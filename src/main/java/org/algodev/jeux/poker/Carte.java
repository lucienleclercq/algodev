package main.java.org.algodev.jeux.poker;

public class Carte {
    private Hauteur hauteur;            //Hauteur de la carte, valeur de 2 a 14. Plus la valeur est elevee plus la carte est forte.
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
}
