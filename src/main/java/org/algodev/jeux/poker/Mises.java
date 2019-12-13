package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.List;

public class Mises {
    private List<Integer> listemises;

    public Mises() {
        this.listemises = new ArrayList<>();
    }

    /**
     * Ajoute une mise dans la liste des mises.
     * @param mise Double, mise d'un joueur.
     */

    public void ajouterMise(int mise) {
        this.listemises.add(mise);
    }

    /**
     * Méthode pour récupérer la taille de la liste des mises.
     * @return Entier, la taille de la lise des mises.
     */

    public int getListeMisesSize() {
        return this.listemises.size();
    }

    /**
     * Méthode a appeler en fin de tour et de partie. Permet de vider la liste des mises.
     */

    public void viderListeMises() {
        this.listemises.clear();
    }

    /**
     * Méthode permettant de récupérer la plus grande mise parmi la liste des mises.
     * @return Double, la plus grande mise.
     */

    public int getPlusGrandeMise() {
        int max = 0;
        for(int mise : listemises) {
            if(mise > max) {
                max = mise;
            }
        }
        return max;
    }

    /**
     * Méthode qui calcule la somme de toutes les mises du tour.
     * @return Double, somme des mises.
     */

    public int sommeMises() {
        int pot = 0;
        for(int mise : listemises) {
            pot = mise + pot;
        }
        return pot;
    }
}
