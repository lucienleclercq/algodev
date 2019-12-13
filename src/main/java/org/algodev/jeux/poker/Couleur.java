package org.algodev.jeux.poker;

public enum Couleur {
    Coeur(1),
    Pique(2),
    Carreau(3),
    Trefle(4);

    private int couleur;

    Couleur(int couleur) {
        this.couleur = couleur;
    }
}
