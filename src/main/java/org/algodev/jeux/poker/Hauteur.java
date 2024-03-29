package org.algodev.jeux.poker;

/**
 * Enum d'une hauteur, chaque hauteur a une valeur de 14 a 2. Plus cette valeur est élevée, plus la carte est forte.
 */

public enum Hauteur {
    As(14),
    Roi(13),
    Dame(12),
    Valet(11),
    Dix(10),
    Neuf(9),
    Huit(8),
    Sept(7),
    Six(6),
    Cinq(5),
    Quatre(4),
    Trois(3),
    Deux(2);

    private int valeur;

    Hauteur(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }
}
