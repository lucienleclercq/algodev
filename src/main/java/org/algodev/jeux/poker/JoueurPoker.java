package main.java.org.algodev.jeux.poker;

public class JoueurPoker {

    private MainDeJoueur main;                  //Main du joueur, deux cartes.
    private double solde;                       //L'argent total qu'il possede.
    private double mise;                        //Sa derniere mise.
    private boolean couche;                     //Si true, le joueur est couche.
    private boolean joue;                       //Si true, c'est au tour du joueur.
    private boolean tapis;                      //Si true, le solde du joueur est a 0.

    public JoueurPoker() {
        this.main = new MainDeJoueur();
        this.solde = 5000;
        this.mise = 0;
        this.couche = false;
        this.joue = false;
    }

    public void ajouterCarteMain(Carte carte) {
        this.main.ajouterCarteMain(carte);
    }

    /**
     * Verifie si un joueur peut miser.
     * @param mise Double, la mise du joueur.
     * @return la mise si inferieure ou egale a son solde.
     */

    private boolean peutMiser(double mise) {
        return mise <= this.solde;
    }

    /**
     * Methode retirant du solde du joueur un montant donne.
     * @param mise Double, la mise du joueur.
     */

    public void retraitSolde(double mise) {
        if(peutMiser(mise)) {               //Si son solde est superieur ou egal a la mise.
            this.solde = solde - mise;
            if(solde == 0) {                //Si son solde, apres le retrait est egal a 0. Le joueur fait donc tapis.
                this.tapis = true;
            }
        }
    }

    /**
     * Methode permettant de savoir si un joueur peut jouer.
     * @return True s'il n'est pas couche et n'a pas fait tapis.
     */

    public boolean peutJouer() {
        return !couche && !tapis;
    }


    public MainDeJoueur getMain() {
        return main;
    }

    public double getSolde() {
        return solde;
    }

    public double getMise() {
        return mise;
    }

    public void setMise(double mise) {
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
}
