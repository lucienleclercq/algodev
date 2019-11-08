package org.algodev.jeux;

import org.algodev.jeux.loto.CaseLoto;
import org.algodev.jeux.sudoku.CaseSudoku;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Grille {
    protected ArrayList<ArrayList<Case>> grille;

    public Grille() {
        this.grille = new ArrayList<ArrayList<Case>>();
    }

    /**
     * CrÃ©er la grille en fonction des valeurs passÃ©s en paramÃ¨tre
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
     * Appeler ce constructeur pour les jeux Sudoku ou Loto
     * @param ligne Nombre de ligne
     * @param col Nombre de colonnes
     * @param jeu Sudoku ou Loto
     */

    public Grille(int ligne, int col, String jeu) {
        this.grille = new ArrayList<ArrayList<Case>>();
        if(jeu.equals("Sudoku")) {
            for(int i = 0; i < ligne; i++) {
                this.grille.add(new ArrayList<Case>());
                for (int j = 0; j < col; j++) {
                    this.grille.get(i).add(new CaseSudoku());
                }
            }
        }
        else {
            if (jeu.equals("Loto")) {
                for(int i = 0; i < ligne; i++) {
                    this.grille.add(new ArrayList<Case>());
                    for (int j = 0; j < col; j++) {
                        this.grille.get(i).add(new CaseLoto());
                    }
                }
            }
        }
    }
    public void agrandir(int ligne, int col)
    {
        int fin = grille.size();
        for(int i = grille.size(); i < ligne+fin;i++)
        {
            this.grille.add(new ArrayList<Case>());
            for (int j = 0; j < col; j++) {
                this.grille.get(i).add(new CaseLoto());
            }
        }
    }

    public ArrayList<ArrayList<Case>> getGrille() {
        return this.grille;
    }

    public Case getCase(int i, int j){
        return grille.get(i).get(j);
    }

    /**
     * Met directement une case à des coordonnées données
     * @param i Coordonnée de la ligne
     * @param j Coordonnée de la colonne
     * @param c	Case à ajouter aux coordonnées
     */

    public void setCase(int i, int j, Case c) {
        grille.get(i).set(j, c);
    }

    /**
     * Affiche la grille, c'est une mÃ©thode de test, ne pas utiliser dans le projet final
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
     * Permet de dÃ©composer un string. Chaque caractÃ¨re doit Ãªtre sÃ©parer d'un esapace pour fonctionner
     * @param valeurs Une chaine de caractÃ¨res
     */

    public void remplirGrille(String valeurs) {
        StringTokenizer tokens = new StringTokenizer(valeurs);
        for(ArrayList<Case> cases : grille)  {
            for(Case valeur : cases) {
                String nombre = tokens.nextToken();
                if(!nombre.equals("0")) {
                    CaseSudoku casesudo = (CaseSudoku) valeur;
                    casesudo.modifiableToFalse();
                }
                valeur.setValeur(nombre);
            }
        }
    }

    public ArrayList<Integer> coordonneesUtilisateur() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> listecoordonnees = new ArrayList<Integer>();
        System.out.println("ENTRER COORDONNEE X : ");
        listecoordonnees.add(scanner.nextInt()-1);
        System.out.println("ENTRER COORDONNEE Y : ");
        listecoordonnees.add(scanner.nextInt()-1);
        scanner.close();
        return listecoordonnees;
    }

    public Boolean coordonneesCorrectes(ArrayList<Integer> listecoordonnees) {
        int x = listecoordonnees.get(0);
        int y = listecoordonnees.get(1);
        if(x <= this.grille.size() && y <= this.grille.get(0).size() && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }
}