package org.algodev.jeux.poker;

public class Carte implements Comparable<Carte> {
    private Hauteur hauteur;            //Hauteur de la carte, valeur de 2 a 14. Plus la valeur est élevée plus la carte est forte.
    private Couleur couleur;            //Couleur de la carte. Valeurs : Coeur, Pique, Trefle, Carreau.

    public Carte(Hauteur hauteur, Couleur couleur) {
        this.hauteur = hauteur;
        this.couleur = couleur;
    }

    public Hauteur getHauteur() {
        return hauteur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Méthode implémentée de l'interface Comparable.
     * @param carte Une carte.
     * @return 0 si les deux cartes ont la même hauteur
     */

    @Override
    public int compareTo(Carte carte) {
        return this.hauteur.compareTo(carte.getHauteur());
    }
}
