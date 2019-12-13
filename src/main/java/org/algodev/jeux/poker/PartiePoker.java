package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.List;

public class PartiePoker {
    private Paquet paquet;                      //Paquet contient la liste des cartes du paquet.
    private CartesCommunes cartescommunes;      //Contient la liste des cartes communes a tous les joueurs.
    private List<JoueurPoker> listejoueurs;     //La liste de joueurs dans la partie.
    private Pot pot;                            //Contient la valeur du pot de la manche.
    private EtatPartie etatpartie;              //Va définir l'état de la partie, quelle manche, qui deal.
    private Mises mise;                         //Permet de récupérer toutes les mises des joueurs lors d'un tour.
    private CompareMains compareMains;

    public PartiePoker() {
        this.mise = new Mises();
        this.paquet = new Paquet();
        this.cartescommunes = new CartesCommunes();
        this.listejoueurs = new ArrayList<>();
        this.pot = new Pot();
        this.etatpartie = new EtatPartie();
        this.compareMains = new CompareMains();
    }

    /**
     * Initialise une partie. Ajoute les joueurs, remplit les mains, les cartes communes et set le joueur dealer a true (peut jouer).
     * @param nbjoueurs Entier, le nombre de joueurs a instancier.
     */

    public void initialisePartie(int nbjoueurs) {
        int dealer = etatpartie.getDealerPartie();

        remplirListeJoueur(nbjoueurs);
        remplirMainsJoueurs();
        remplirCartesCommunes();
        listejoueurs.get(dealer).changeJoue();
    }

    /**
     * Methode appelee lorsque un joueur se couche.
     */

    public void seCouche() {
        JoueurPoker joueurActuel = getJoueurActuel();                                   //Va chercher le joueur.
        joueurActuel.seCouche();                                                        //Change sa valeur "couche" à true.
        if(nbDeJoueursRestants() > 1) {                                                 //S'il y a plus d'un joueur encore en jeu.
            if(listejoueurs.get(etatpartie.getDealerManche()).equals(joueurActuel)) {   //Si le joueur qui se couche était le dealer.
                etatpartie.changementDealerManche(nbDeJoueursRestants());               //Le dealer passer au joueur suivant.
            }
            deroulementPartie(0);
        }
        else  {
            etatpartie.setEtatpartie(3);
            finPartie(false);
        }
    }

    /**
     * Méthode à appeler lors d'un event. Va gérer le déroulement des tours et de la partie.
     * @param mise Double, la mise d'un joueur.
     */

    public void deroulementPartie(int mise) {
        switch(nbDeJoueursRestants()) {
            case 0 : relancePartie(); break;
            case 1 : finPartie(false); break;
            default : deroulementPartie2(mise); break;
        }
    }

    /**
     * Méthode appelée dans deroulementPartie dans le cas par default du switch.
     * Gere la partie en fonction de son etat.
     * @param mise Double, la mise d'un joueur.
     */

    public void deroulementPartie2(int mise) {
        JoueurPoker joueurActuel = getJoueurActuel();
        int miseMax = this.mise.getPlusGrandeMise();
        if(!tousTapis(nbDeJoueursRestants())) {
            if((comparerLesMises() || tousCheck()) && etatpartie.getEtatpartie() == 4) {   //Si tous les joueurs ont suivi et que c'est la fin de partie.
                ajouterMise(mise);
                finPartie(true);                                //Méthode qui gère la fin de partie. On passe true parce que l'on veut comparer les mains.
            }
            else {
                if(joueurActuel.peutMiser(mise, miseMax) || joueurActuel.isCouche()) {
                    ajouterMise(mise);
                    if(mise != 0) {
                        retireCheck();
                    }
                    else {
                        joueurActuel.setaCheck(true);
                    }
                    if(tousCheck()) {                           //Si tous les joueurs ont check, fin du tour.
                        finTour();
                    }
                    else {
                        if (comparerLesMises() && mise != 0) {               //Si tous les joueurs ont suivi.
                            finTour();                          //Méthode qui gère la fin d'un tour.
                        }
                        else {
                            joueurSuivant();                    //Si tous les joueurs n'ont pas joué/misé.
                        }
                    }
                }
            }
        }
        else {
            ajouterMise(mise);
            etatpartie.setEtatpartie(3);
            finPartie(true);
        }
    }

    /**
     * Méthode qui va relancer une partie.
     */

    public void relancePartie() {
        int nbjoueurs = listejoueurs.size();            //Reprend le nombre de joueurs, au cas ou des joueurs auraient perdu.
        etatpartie.changementDealerPartie(nbjoueurs);   //Désigne un nouveau dealer.
        int dealer = etatpartie.getDealerPartie();
        etatpartie.setEtatpartie(0);                    //L'état de partie 0 est le début de partie.
        paquet.remplirPaquet();                         //Recréer le paquet.
        cartescommunes = new CartesCommunes();
        reinitialierMainsJoueurs();
        remplirMainsJoueurs();
        remplirCartesCommunes();
        listejoueurs.forEach(joueurPoker -> joueurPoker.setCouche(false));
        listejoueurs.forEach(joueurPoker -> joueurPoker.setJoue(false));
        listejoueurs.get(dealer).changeJoue();
    }

    public void reinitialierMainsJoueurs() {
        for(JoueurPoker joueurPoker : listejoueurs) {
            joueurPoker.viderMain();
        }
    }

    /**
     * Methode permettant de definir le prochain joueur.
     */

    public void joueurSuivant() {
        JoueurPoker joueur = getJoueurActuel();         //Récupère le joueur actuel.
        int indice = listejoueurs.indexOf(joueur)+1;    //Récupère l'indice+1 du joueur actuel.
        if(indice >= getNbJoueurs()) {                  //Si l'indice est supérieur au nombre de joueurs, on revient au premier de la liste (indice 0).
            indice = 0;
        } else {
            while(!getJoueur(indice).peutJouer()) {         //Boucle temps que le joueur à l'indice ne peut pas jouer. peutJouer, vérifie si un joueur a fait tapis ou s'est couche.
                if(indice >= getNbJoueurs()) {               //Si l'indice est supérieur au nombre de joueurs, on revient au premier de la liste (indice 0).
                    indice = 0;
                }
                else indice++;
            }
        }
        joueur.changeJoue();                            //changeJoue passe le champ "joue" en son inverse. Donc le joueur actuel passe en false.
        getJoueur(indice).changeJoue();                 //Le joueur à l'indice passe à true.
    }

    /**
     * Méthode de fin de tour. Appelée quand tout le monde a joué.
     */

    public void finTour() {
        int pot = mise.sommeMises();                                     //Méthode de la classe mise, permet d'additionner toutes les mises du tour.
        this.pot.ajoutePot(pot);                                            //Ajoute la valeur précédemment calculée au pot total.
        getJoueurActuel().changeJoue();
        JoueurPoker joueurdealer = getJoueurDealer();                       //Va chercher le dealer actuel (de la manche).
        etatpartie.avancerPartie();                                         //avancerPartie incrémente la champ "etatpartie".
        while(joueurdealer.isCouche() || joueurdealer.isTapis()) {          //TQ le deal est couché ou est tapis, on cherche un nouveau dealer pour cette partie.
            etatpartie.changementDealerManche(getNbJoueurs());              //Va incrémenter le champ dealermanche de la classe EtatPartie.
            joueurdealer = listejoueurs.get(etatpartie.getDealerManche());  //Avance dans la liste de joueurs.
        }
        mise.viderListeMises();
        reinitialiseToutesLesMises();
        retireCheck();
        joueurdealer.changeJoue();                                          //Set joué à true pr le dealer.
    }

    /**
     * Methode de fin de partie, quand tout le monde a joué et que toutes les cartes communes sont retournées.
     * Appelle la comparaison de mains.
     * @param comparer Prend true si jamais on veut comparer les mains. Mettre false si jamais il n'y a plus qu'un seul joueur en jeu.
     */

    public void finPartie(boolean comparer) {
        int pot = mise.sommeMises();
        List<JoueurPoker> gagnants;
        this.pot.ajoutePot(pot);
        int potTotal = this.pot.getPot();
        etatpartie.avancerPartie();
        if(comparer) {
            List<JoueurPoker> joueurEncoreEnJeu = getJoueursEnJeu();
            for(JoueurPoker joueur : joueurEncoreEnJeu) {
                joueur.gestionMainsPossibles(cartescommunes, this.compareMains);
            }
            gagnants = this.compareMains.definitGagnant(joueurEncoreEnJeu);
            if(gagnants.size() > 1) {
                for(JoueurPoker joueurPoker : gagnants) {
                    joueurPoker.ajoutSolde(potTotal/gagnants.size());
                }
            }
            else {
                gagnants.get(0).ajoutSolde(potTotal);
            }
        }
        else {
            getJoueurActuel().ajoutSolde(potTotal);
        }
        this.pot.setPot(0);
        reinitialiseToutesLesMises();
        mise.viderListeMises();
        retireJoueurSansSolde();
        retireCheck();
    }

    /**
     * Compare si tous les joueurs en jeu ont suivi la mise maximale du tour.
     * @return True si tous les joueurs ont bien suivi.
     */

    public boolean comparerLesMises() {
        int misemax = this.mise.getPlusGrandeMise();                                                          //Va chercher la plus grande valeur parmi les mises.
        for(JoueurPoker joueurPoker : listejoueurs) {
            if(!joueurPoker.isCouche() && !joueurPoker.isTapis() && joueurPoker.getMise() != misemax) {     //Si un joueur qui n'est pas couché et n'a pas mis tapis n'a pas la mise max.
                return false;                                                                               //Retourne false.
            }
        }
        return true;                                                                                        //Retourne true si tous les joueurs qui pouvaient jouer ont bien misé.
    }

    /**
     * Méthode qui va ajouter la mise du joueur dans la liste des mises du tour.
     * @param mise Double, mise du joueur actuel.
     */

    public void ajouterMise(int mise) {
        JoueurPoker joueur = getJoueurActuel();
        joueur.retraitSolde(mise);
        this.mise.ajouterMise(mise);
    }

    /**
     * Méthode permettant de récupérer le joueur en train de jouer.
     * @return JoueurPoker qui a son champ "joue" a true.
     */

    public JoueurPoker getJoueurActuel() {
        for(JoueurPoker joueur : listejoueurs) {    //Parcourt la liste des joueurs.
            if(joueur.isJoue()) {
                return joueur;
            }
        }
        return null;
    }

    /**
     * Méthode retournant le dealer de la manche.
     * @return JoueurPoker e4étant le dealer.
     */

    public JoueurPoker getJoueurDealer() {
        return listejoueurs.get(etatpartie.getDealerManche());
    }

    public JoueurPoker getJoueur(int indice) {
        return listejoueurs.get(indice);
    }

    /**
     * Méthode vérifiant si tous les joueurs ont mis tapis.
     * @return True s'ils un seul joueur ou moins n'a pas mis tapis.
     */

    public boolean tousTapis(int nbJoueursRestants) {
        int cpt = 0;
        for(JoueurPoker joueur : listejoueurs) {
            if(!joueur.isTapis()) {
                cpt++;
            }
        }
        return cpt <= 1;
    }

    /**
     * Remplit la liste des joueurs.
     * @param nbjoueurs Nombre de joueurs, récupéré avant la partie.
     */

    public void remplirListeJoueur(int nbjoueurs) {
        for(int i = 0; i < nbjoueurs; i++) {
            this.listejoueurs.add(new JoueurPoker(i+1));
        }
    }

    /**
     * Remplit les mains des joueurs précédemment instanciées.
     */

    public void remplirMainsJoueurs() {
        for(JoueurPoker joueur : listejoueurs) {
            for(int i = 0; i < 2; i++) {
                Carte carte = this.paquet.getCartePaquet(i);
                joueur.ajouterCarteMain(carte);
                this.paquet.retirerCartePaquet(carte);
            }
        }
    }

    /**
     * Remplit le tableau des cartes communes.
     */

    public void remplirCartesCommunes() {
        for(int i = 0; i < 5; i++) {
            Carte carte = this.paquet.getCartePaquet(i);
            this.cartescommunes.ajouterCarteCommune(carte);
            this.paquet.retirerCartePaquet(carte);
        }
    }

    /**
     * Retire un joueur en fin de partie si son solde atteint 0.
     */

    public void retireJoueurSansSolde() {
        listejoueurs.removeIf(joueur -> joueur.getSolde() == 0);
    }

    /**
     * Méthode à appeler en fin de partie, créer une liste avec les joueurs non couches.
     * @return La liste des joueurs non couchés.
     */

    public List<JoueurPoker> getJoueursEnJeu() {
        List<JoueurPoker> joueurs = new ArrayList<>();
        for(JoueurPoker joueurPoker : listejoueurs) {
            if(!joueurPoker.isCouche()) {
                joueurs.add(joueurPoker);
            }
        }
        return joueurs;
    }

    /**
     * Vérifie si tous les joueurs encore en jeu ou pouvant jouer ont check.
     * @return True si tous les joueurs ont check.
     */

    public boolean tousCheck() {
        for(JoueurPoker joueurPoker : listejoueurs) {
            if(joueurPoker.peutJouer()) {
                if(!joueurPoker.isaCheck()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Passe la valeur aCheck a false pour tous les joueurs.
     */

    public void retireCheck() {
        for(JoueurPoker joueurPoker : listejoueurs) {
            joueurPoker.setaCheck(false);
        }
    }

    /**
     * Calcule le nombre de joueurs encore en jeu.
     * @return Entier, nombre de joueurs encore en jeu.
     */

    public int nbDeJoueursRestants() {
        int cpt = 0;
        for(JoueurPoker joueur : listejoueurs) {
            if(!joueur.isCouche()) {
                cpt++;
            }
        }
        return cpt;
    }

    /**
     * Reinitilise toutes les mises des joueurs. Fin de tour et fin de partie.
     */

    public void reinitialiseToutesLesMises() {
        for(JoueurPoker joueurPoker : listejoueurs) {
            joueurPoker.setMise(0);
        }
    }

    public Paquet getPaquet() {
        return paquet;
    }

    public CartesCommunes getCartescommunes() {
        return cartescommunes;
    }

    public List<JoueurPoker> getListejoueurs() {
        return listejoueurs;
    }

    public Pot getPot() {
        return pot;
    }

    public int getNbJoueurs() {
        return listejoueurs.size();
    }

    public EtatPartie getEtatpartie() {
        return etatpartie;
    }

    public Mises getMise() {
        return mise;
    }
}
