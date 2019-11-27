package main.java.org.algodev.jeux.poker;

public class Carte {
    private Hauteur hauteur;
    private Couleur couleur;

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
