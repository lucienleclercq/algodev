package main.java.org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.List;

public class PartiePoker {
    private Paquet paquet;
    private CartesCommunes cartescommunes;
    private List<JoueurPoker> listejoueurs;
    private Pot pot;

    public PartiePoker() {
        this.paquet = new Paquet();
        this.cartescommunes = new CartesCommunes();
        this.listejoueurs = new ArrayList<>();
        this.pot = new Pot();
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
}
