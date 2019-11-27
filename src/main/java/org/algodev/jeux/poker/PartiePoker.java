package main.java.org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.List;

public class PartiePoker {
    private Paquet paquet;                      //Paquet contient la liste des cartes du paquet.
    private CartesCommunes cartescommunes;      //Contient la liste des cartes communes a tous les joueurs.
    private List<JoueurPoker> listejoueurs;     //La liste de joueurs dans la partie.
    private Pot pot;                            //Contient la valeur du pot de la manche.
    private EtatPartie etatpartie;              //Va definir l'etat de la partie, quelle manche, qui deal.
    private Mises mise;                         //Permet de recuperer toutes les mises des joueurs lors d'un tour.

    public PartiePoker() {
        this.paquet = new Paquet();
        this.cartescommunes = new CartesCommunes();
        this.listejoueurs = new ArrayList<>();
        this.pot = new Pot();
        this.etatpartie = new EtatPartie();
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
     * Methode a appeler lors d'un event. Va gerer le deroulement des tours et de la partie.
     * @param mise Double, la mise d'un joueur s'il y en a.
     */

    public void deroulementPartie(double mise) {
        if(tousCouche()) {                      //Si tous les joueurs sont couches.
            relancePartie();                   //Methode qui va passer a la prochaine partie.
        }
        else {
            if(comparerLesMises() && etatpartie.getEtatpartie() == 3) {         //Si tous les joueurs ont suivi et que c'est la fin de partie.
                finPartie();                   //Methode qui gere la fin de partie.
            }
            else {
                if(comparerLesMises()) {       //Si tous les joueurs ont suivi.
                    finTour();                 //Methode qui gere la fin d'un tour.
                }
                else {                         //Si tous les joueurs n'ont pas joue/mise.
                    ajouterMise(mise);
                    joueurSuivant();
                }
            }
        }
    }

    /**
     * Methode qui va relancer une partie.
     */

    public void relancePartie() {
        int nbjoueurs = listejoueurs.size();            //Reprend le nombre de joueurs, au cas ou des joueurs auraient perdu.
        etatpartie.setEtatpartie(0);                    //L'etat de partie 0 est le debut de partie.
        etatpartie.changementDealerPartie(nbjoueurs);   //Designe un nouveau dealer.
        paquet.remplirPaquet();                         //Recreer le paquet.
        initialisePartie(nbjoueurs);                    //Appelle la methode d'initilisation d'une partie (cf au dessus).
    }

    /**
     * Methode permettant de definir le prochain joueur.
     */

    public void joueurSuivant() {
        JoueurPoker joueur = getJoueurActuel();         //Recupere le joueur actuel.
        int indice = listejoueurs.indexOf(joueur)+1;    //Recupere l'indice+1 du joueur actuel.
        while(!getJoueur(indice).peutJouer()) {         //Boucle temps que le joueur a l'indice ne peut pas jouer. peutJouer, verifie si un joueur a fait tapis ou s'est couche.
            if(indice > getNbJoueurs()) {               //Si l'indice est superieur au nombre de joueurs, on revient au premier de la liste (indice 0).
                indice = 0;
            }
            else indice++;
        }
        joueur.changeJoue();                            //changeJoue passe le champ joue en son inverse. Donc le joueur actuel passe en false.
        getJoueur(indice).changeJoue();                 //Le joueur a l'indice passe true.
    }

    /**
     * Methode de fin de tour. Appelee quand tout le monde a joue.
     */

    public void finTour() {
        double pot = 0;
        pot = mise.sommeMises();                                            //Methode de la classe mise, permet d'additionner toutes les mises du tour.
        this.pot.ajoutePot(pot);                                            //Ajoute la valeur precedemment calculee au pot total.
        JoueurPoker joueurdealer = getJoueurDealer();                       //Va chercher le dealer actuel (de la manche).
        etatpartie.avancerPartie();                                         //avancerPartie incremente la champ etatpartie.
        while(joueurdealer.isCouche() || joueurdealer.isTapis()) {          //TQ le deal est couche ou est tapis, on cherche un nouveau dealer pr cette partie.
            etatpartie.changementDealerManche(getNbJoueurs());              //Va incrementer le champ dealermanche de la classe EtatPartie.
            joueurdealer = listejoueurs.get(etatpartie.getDealerManche());  //Avance dans la liste de joueurs.
        }
        mise.viderListeMises();
        joueurdealer.changeJoue();                                          //Set joue a true pr le dealer.
    }

    /**
     * Methode de fin de partie, quand tout le monde a jouer et que toutes les cartes communes sont retournees.
     * Appelle la comparaison de mains.
     */

    public void finPartie() {
        double pot = 0;
        pot = mise.sommeMises();
        this.pot.ajoutePot(pot);
        etatpartie.avancerPartie();
        /*
          Ajouter la comparaison des mains
        */
        mise.viderListeMises();
        retireJoueurSansSolde();
    }

    /**
     * Compare si tous les joueurs en jeu ont suivi la mise maximale du tour.
     * @return true si tous les joueurs ont bien suivi.
     */

    public boolean comparerLesMises() {
        if(mise.getListeMisesSize() == listejoueurs.size()) {               //S'il y a autant de mises que de joueurs dans la partie (penser a ajouter 0 quand un joueur se couche).
            double misemax = mise.getPlusGrandeMise();                      //Va chercher la plus grande valeur parmi les mises.
            for(JoueurPoker joueur : listejoueurs) {
                if(joueur.getMise() != misemax && !joueur.peutJouer()) {    //Si un joueur n'a pas mis la mise max et s'il peut jouer (pas tapis ni couche), alors return false.
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Methode qui va ajouter la mise du joueur dans la liste des mises du tour.
     * @param mise Double, mise du joueur actuel.
     */

    public void ajouterMise(double mise) {
        JoueurPoker joueur = getJoueurActuel();
        joueur.setMise(mise);
        this.mise.ajouterMise(mise);
    }

    /**
     * Methode permettant de recuperer le joueur en train de jouer.
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
     * Methode retournant le dealer de la manche.
     * @return JoueurPoker etant le dealer.
     */

    public JoueurPoker getJoueurDealer() {
        return listejoueurs.get(etatpartie.getDealerManche());
    }

    public JoueurPoker getJoueur(int indice) {
        return listejoueurs.get(indice);
    }

    /**
     * Methode verifiant si tous les joueurs sont couches.
     * @return True s'ils sont tous couches.
     */

    public boolean tousCouche() {
        for(JoueurPoker joueur : listejoueurs) {
            if(!joueur.isCouche()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Remplit la liste des joueurs.
     * @param nbjoueurs Nombre de joueurs, recupere avant la partie.
     */

    public void remplirListeJoueur(int nbjoueurs) {
        for(int i = 0; i < nbjoueurs; i++) {
            this.listejoueurs.add(new JoueurPoker());
        }
    }

    /**
     * Remplit les mains des joueurs precedemment instancies.
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
        for(JoueurPoker joueur : listejoueurs) {
            if(joueur.getSolde() == 0) {
                listejoueurs.remove(joueur);
            }
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
}
