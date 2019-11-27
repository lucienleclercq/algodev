package main.java.org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainDeJoueur {

    private List<Carte> main;       //Liste de cartes dans la main, deux cartes.

    /**
     * Va creer la main d'un joueur.
     * @param cartes Passer seulement DEUX cartes.
     */

    public MainDeJoueur(Carte...cartes) {
        this.main = new ArrayList<>();
        this.main.addAll(Arrays.asList(cartes));        //Prend les cartes (2 cartes) en argument et les ajoute dans la main.
    }

    public void ajouterCarteMain(Carte carte) {
        this.main.add(carte);
    }

    public List<Carte> getMain() {
        return main;
    }
}
