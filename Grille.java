package org.algodev.grille;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.algodev.sudoku.CaseSudoku;

public class Grille {
    protected ArrayList<ArrayList<Case>> grille;
    
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
                    CaseSudoku casesudoku = (CaseSudoku) this.getCase(i, j);
                    if (!(casesudoku.toString().equals("0"))) {
                    	casesudoku.modifiableToFalse();
                    }
                }
            }
    	}
    	else {
    		if (jeu.equals("Loto")) {
    			for(int i = 0; i < ligne; i++) {
                    this.grille.add(new ArrayList<Case>());
                    for (int j = 0; j < col; j++) {
                        //this.grille.get(i).add(new CaseLoto());
                    }
                }
    		}
    	}
    }
    
    public Grille getGrille() {
        return this;
    }

    public Case getCase(int i, int j){
        return grille.get(i).get(j);
    }
    
    /**
     * Met directement une case � des coordonn�es donn�es
     * @param i Coordonn�e de la ligne
     * @param j Coordonn�e de la colonne
     * @param c	Case � ajouter aux coordonn�es
     */
    
    public void setCase(int i, int j, Case c) {
    	grille.get(i).set(j, c);
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