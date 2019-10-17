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
     * Creer la grille en fonction des valeurs passes en parametres
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
    
    public Grille getGrille() {
        return this;
    }

    public Case getCase(int i, int j){
        return grille.get(i).get(j);
    }
    
    /**
     * Met directement une case a des coordonnees donnees
     * @param i Coordonnee de la ligne
     * @param j Coordonnee de la colonne
     * @param c	Case a ajouter aux coordonnees
     */
    
    public void setCase(int i, int j, Case c) {
    	grille.get(i).set(j, c);
    }

    /**
     * Affiche la grille, c'est une methode de test, ne pas utiliser dans le projet final
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
     * Permet de decomposer un string. Chaque caractere doit etre separer d'un esapace pour fonctionner
     * @param valeurs Une chaine de caractÃ¨res
     */

    public void remplirGrille(String valeurs) {
        StringTokenizer tokens = new StringTokenizer(valeurs);
        for(ArrayList<Case> cases : grille)  {
            for(Case valeur : cases) {
                valeur.setValeur(tokens.nextToken());
                if(valeur instanceof CaseSudoku) {
                    CaseSudoku casesudo = (CaseSudoku) valeur;
                    if(!casesudo.valeur.equals("0")) {
                        casesudo.modifiableToFalse();
                    }
                    System.out.println(casesudo.getModifiable());
                }
            }
        }
    }

    /**
     * Demande a l'utilisateur de rentrer des coordonees
     * @return ArrayList, indice 0 = x, indice 1 = y
     */
    
    public ArrayList<Integer> coordonneesUtilisateur() {
    	Scanner scanner = new Scanner(System.in);
    	ArrayList<Integer> listecoordonnees = new ArrayList<Integer>();
    	System.out.println("ENTRER COORDONNEE X : ");
    	listecoordonnees.add(scanner.nextInt()-1);
    	System.out.println("ENTRER COORDONNEE Y : ");
    	listecoordonnees.add(scanner.nextInt()-1);
    	return listecoordonnees;
    }

    /**
     * Verifie si l'utilisateur n'a pas entre des coordonnees innaccessibles (hors liste)
     * @param listecoordonnees ArrayList des coordonees x et y
     * @return true si les coordonnees sont correctes
     */
    
    public Boolean coordonneesCorrectes(ArrayList<Integer> listecoordonnees) {
    	int x = listecoordonnees.get(1);
    	int y = listecoordonnees.get(0);
    	if(y <= this.grille.size() && x <= this.grille.get(0).size() && x >= 0 && y >= 0) {
    		return true;
    	}
    	return false;
    }
}
