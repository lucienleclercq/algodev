package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.List;

public class JoueurPoker {

    private MainDeJoueur main;                  //Main du joueur, deux cartes.
    private int solde;                       //L'argent total qu'il possède.
    private int mise;                        //Sa dernière mise.
    private boolean couche;                     //Si true, le joueur est couche.
    private boolean joue;                       //Si true, c'est au tour du joueur.
    private boolean tapis;                      //Si true, le solde du joueur est a 0.
    private MainsPossiblesJoueur mainspossibles; //Contient toutes les mains possibles du joueurs en fin de partie.
    private boolean aCheck;                     //Si le joueur a checké pendant ce tour.
    private MainDeJoueur mainFinale;
    private String nom;

    public JoueurPoker(int num) {
        this.main = new MainDeJoueur();
        this.solde = 5000;
        this.mise = 0;
        this.couche = false;
        this.joue = false;
        this.mainspossibles = new MainsPossiblesJoueur();
        this.tapis = false;
        this.aCheck = false;
        this.nom = "Joueur " + num;
    }

    public void ajouterCarteMain(Carte carte) {
        this.main.ajouterCarteMain(carte);
    }

    /**
     * Vérifie si un joueur peut miser.
     * @param mise Double, la mise du joueur.
     * @return la mise si inférieure ou égale à son solde.
     */

    public boolean peutMiser(int mise, int miseMax) {
        if(miseMax >= this.solde && mise >= this.solde) {
            this.mise = mise;
            return true;
        }
        return mise >= miseMax && mise <= this.solde;
    }

    /**
     * Méthode retirant du solde du joueur selon un montant donné.
     * Gère également la relance.
     * @param mise Double, la mise du joueur.
     */

    public void retraitSolde(int mise) {
        if(mise >= this.solde) {
            this.solde = 0;
            this.tapis = true;
        } else {
            if(this.mise != 0) {
                this.solde = this.solde + this.mise;
                this.mise = mise;
                this.solde = this.solde - mise;
            }
            else {
                this.mise = mise;
                this.solde = this.solde - mise;
            }
        }
    }

    /**
     * Méthode permettant de savoir si un joueur peut jouer.
     * @return True s'il n'est pas couché et n'a pas fait tapis.
     */

    public boolean peutJouer() {
        return !couche && !tapis;
    }

    /**
     * Méthode appelée en fin de partie quand il reste plus d'un joueur et que l'on va comparer les mains afin de definir un vainqueur.
     * La liste possibles des mains d'un joueur sera créée. La meilleure sera sélectionnée.
     * @param cartesCommunes Les cartes communes à tous les joueurs.
     * @param compareMains La classe de comparaison des mains. Provient de la classe PartiePoker
     */

    public void gestionMainsPossibles(CartesCommunes cartesCommunes, CompareMains compareMains) {
        remplitMainsPossibles(cartesCommunes);
        this.setMainFinale(compareMains.compareMainsPossibles(this.mainspossibles));
        this.mainspossibles.videListeMainsPossible();
    }

    /**
     * Va appeler les méthodes nécessaires a la création des mains possibles.
     * @param cartescommunes Les cartes communes a tous les joueurs.
     */

    public void remplitMainsPossibles(CartesCommunes cartescommunes) {
        List<Carte> toutescartes = new ArrayList<>(this.main.getMain());
        toutescartes.addAll(cartescommunes.getListecartescommunes());
        mainspossibles.assembleMainsPossibles(toutescartes);
        DefinitPuissanceMain definitPuissanceMain = new DefinitPuissanceMain(this.mainspossibles);
    }

    public void viderMain() {
        this.main = new MainDeJoueur();
    }

    public MainDeJoueur getMain() {
        return main;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public void ajoutSolde(int solde) {
        this.solde = this.solde + solde;
    }

    public int getMise() {
        return mise;
    }

    public void setMise(int mise) {
        this.mise = mise;
    }

    public void seCouche() {
        this.couche = true;
    }

    public void changeJoue() {
        this.joue = !joue;
    }

    public boolean isCouche() {
        return couche;
    }

    public boolean isJoue() {
        return joue;
    }

    public boolean isTapis() {
        return tapis;
    }

    public void setMain(MainDeJoueur main) {
        this.main = main;
    }

    public void setaCheck(boolean aCheck) {
        this.aCheck = aCheck;
    }

    public boolean isaCheck() {
        return aCheck;
    }

    public void setMainFinale(MainDeJoueur mainFinale) {
        this.mainFinale = mainFinale;
    }

    public void setCouche(boolean couche) {
        this.couche = couche;
    }

    public void setJoue(boolean joue) {
        this.joue = joue;
    }

    public String getNom() {
        return nom;
    }
}
