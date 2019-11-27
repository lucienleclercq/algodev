package main.java.org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartesCommunes {
    private List<Carte> listecartescommunes;

    public CartesCommunes(Carte...cartes) {
        this.listecartescommunes = new ArrayList<>();
        this.listecartescommunes.addAll(Arrays.asList(cartes));
    }

    public List<Carte> getListecartescommunes() {
        return listecartescommunes;
    }
}
