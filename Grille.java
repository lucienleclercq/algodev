package org.algodev.grille;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Grille {
    protected ArrayList<ArrayList<Case>> grille;

    public Grille getGrille() {
        return this;
    }

    public Case getCase(int i, int j){
        return grille.get(i).get(j);
    }

    public Grille() {
        this.grille = new ArrayList<ArrayList<Case>>();
    }

    /**
     * Créer la grille en fonction des valeurs passés en paramètre
     * @param ligne nombre de lignes
     * @param col nombre de colonnes
     */

    public Grille(int ligne, int col) {
        this.grille = new ArrayList<ArrayList<Case>>();
        for(int i = 0; i < ligne; i++) {
            this.grille.add(new ArrayList<Case>());
            for (int j = 0; j < col; j++) {
                this.grille.get(i).add(new Case());
            }
        }
    }

    /**
     * Affiche la grille, c'est une méthode de test, ne pas utiliser dans le projet final
     */

    public void affiche() {
        for (ArrayList<Case> cases : grille) {
            for (Case valeur : cases) {
                System.out.print(valeur.toString() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Permet de décomposer un string. Chaque caractère doit être séparer d'un esapace pour fonctionner
     * @param valeurs Une chaine de caractères
     */

    public void remplirGrille(String valeurs) {
        StringTokenizer tokens = new StringTokenizer(valeurs);
        for(ArrayList<Case> cases : grille)  {
            for(Case valeur : cases) {
                valeur.setValeur(tokens.nextToken());
            }
        }
    }
}