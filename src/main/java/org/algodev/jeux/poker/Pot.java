package org.algodev.jeux.poker;

public class Pot {
    private int pot;         //Total des mises de la manche.

    public Pot() {
        this.pot = 0;
    }

    /**
     * Méthode à appeler lors de la fin du tour. Permet d'ajouter au pot la somme des mises du tour.
     * @param pot Somme des mises du tour.
     */

    public void ajoutePot(int pot) {
        this.pot = this.pot + pot;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }
}
