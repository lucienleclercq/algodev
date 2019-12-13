package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.List;

public class CartesCommunes {
    private List<Carte> listecartescommunes;        //Liste des cartes communes a tous les joueurs.

    public CartesCommunes() {
        this.listecartescommunes = new ArrayList<>();
    }

    public void ajouterCarteCommune(Carte carte) {
        this.listecartescommunes.add(carte);
    }

    public List<Carte> getListecartescommunes() {
        return listecartescommunes;
    }
}
