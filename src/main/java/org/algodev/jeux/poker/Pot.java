package main.java.org.algodev.jeux.poker;

public class Pot {
    private double pot;         //Total des mises de la manche.

    public Pot() {
        this.pot = 0;
    }

    /**
     * Methode a appeler lors de la fin du tour. Permet d'ajouter au pot la somme des mises du tour.
     * @param pot Somme des mises du tour.
     */

    public void ajoutePot(double pot) {
        this.pot = this.pot + pot;
    }

    public double getPot() {
        return pot;
    }
}
