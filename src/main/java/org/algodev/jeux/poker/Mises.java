package main.java.org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mises {
    private List<Double> listemises;

    public Mises(List<Double> listemises) {
        this.listemises = new ArrayList<>();
    }

    /**
     * Ajoute une mise dans la liste des mises.
     * @param mise Double, mise d'un joueur.
     */

    public void ajouterMise(double mise) {
        this.listemises.add(mise);
    }

    /**
     * Methode pour recuperer la taille de la liste des mises.
     * @return Entier, la taille de la lise des mises.
     */

    public int getListeMisesSize() {
        return this.listemises.size();
    }

    /**
     * Methode a appeler en fin de tour et de partie. Permet de vider la liste des mises.
     */

    public void viderListeMises() {
        this.listemises.clear();
    }

    /**
     * Methode permettant de recuperer la plus grande mise parmi la liste des mises.
     * @return Double, la plus grande mise.
     */

    public double getPlusGrandeMise() {
        double max = 0;
        for(double mise : listemises) {
            if(mise > max) {
                max = mise;
            }
        }
        return max;
    }

    /**
     * Methode qui calcule la somme des toutes les mises du tour.
     * @return Double, somme des mises.
     */

    public double sommeMises() {
        double pot = 0;
        for(double mise : listemises) {
            pot = mise + pot;
        }
        return pot;
    }
}
