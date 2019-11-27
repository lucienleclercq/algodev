package main.java.org.algodev.jeux.poker;

public class Pot {
    private double pot;

    public Pot() {
        this.pot = 0;
    }

    public double getPot() {
        return pot;
    }

    public void setPot(double pot) {
        this.pot = pot;
    }

    public void ajoutPot(double...mises) {
        for(double mise : mises) {
            this.pot = pot + mise;
        }
    }
}
