package main.java.org.algodev.jeux.poker;

public class JoueurPoker {
    private MainDePoker main;
    private double solde;
    private double mise;

    public JoueurPoker(Carte...cartes) {
        this.main = new MainDePoker(cartes);
        this.solde = 5000;
        this.mise = 0;
    }

    public MainDePoker getMain() {
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

    public void retraitSolde(double mise) {
        if(peutMiser(mise)) {
            this.solde = solde - mise;
        }
    }

    private boolean peutMiser(double mise) {
        return mise <= this.solde;
    }
}
