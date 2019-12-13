package org.algodev.jeux.sudoku;

import org.algodev.jeux.Grille;
import org.algodev.jeux.Case;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Sudoku extends Grille {
    private int random;
    private String difficulte;

    /**
     * Constructeur, lit la grille dans un fichier texte et appelle methode dans la classe mere pour remplir la grille
     * @throws FileNotFoundException
     */

    public Sudoku (String difficulte) throws IOException {
    	super(9,9, "Sudoku");
    	this.random = (int) (Math.random() * 300) +1;
    	this.difficulte = difficulte;
    	String url = "/grilles-et-solutions/Grilles-";
    	url = url + difficulte + ".txt";                                                    // Reconstruit le string pour aller chercher le fichier correspondant à la difficulté choisie.
        InputStream input = this.getClass().getResourceAsStream(url);                       // Indispensable, permet d'aller chercher le fichier dans le jar, besoin d'ajouter l'arbo dans le classpath
        BufferedReader br = new BufferedReader(new InputStreamReader(input));               // Initialise un buffer et un InputStreamReader
        for(int i = 1; i < random; i++) {                                                   // Boucle jusque la valeur random.
            br.readLine();                                                                  // Lit la ligne
        }
        String valeurs = br.readLine();
        super.remplirGrille(valeurs);
        input.close();
    }

    /**
     * Methode pour charger la solution de la grille
     * @return String, la grille
     * @throws IOException
     */

    public String chargerSolution() throws IOException {
        String url = "/grilles-et-solutions/Solutions-";
        url = url + difficulte + ".txt";
        InputStream input = this.getClass().getResourceAsStream(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        for(int i = 1; i < this.random; i++) {
            br.readLine();
        }
        return br.readLine();
    }

    /**
     * Methode a appeler dans le cas ou la grille est remplie
     * @return true si la grille est correcte, false si elle ne l'est pas
     * @throws FileNotFoundException
     */

    public Boolean finDePartie(String solution) throws IOException {
        StringBuilder build = new StringBuilder();
        for(ArrayList<Case> liste : this.grille) {
            for(Case cases : liste) {
                build.append(cases.toString() + " ");
            }
        }
        String grillecomplete = build.toString();
        if(grillecomplete.equals(solution)) {
            return true;
        }
        else return false;
    }

    /**
     * Verifie s'il y a un doublon dans la ligne/colonne ou dans le carre
     * @param casesudo
     * @return true s'il y a un doublon, false s'il n'y en a pas
     */

    public Boolean verificationDoublons(Case casesudo) {
        int x = 0, y = 0;
        for(int i = 0; i < 9; i++) {
            if(super.getGrille().get(i).indexOf(casesudo) != -1) {
                x = i;
                y = super.getGrille().get(i).indexOf(casesudo);
            }
        }
        if(verificationDoublonLigne(casesudo, x, y) || verificationDoublonColonne(casesudo, x, y) || verificationDoublonCarre(casesudo, x, y)) {
            return true;
        }
        return false;
    }

    /**
     * Methode pour verifier s'il existe un doublon sur la ligne
     * @param casesudo case que l'on veut verifier
     * @param x coordonnee x de casesudo
     * @param y coordonee y de casesudo
     * @return true s'il y a un doublon, sinon false
     */

    public Boolean verificationDoublonLigne(Case casesudo, int x, int y) {
        for(int i = 0; i < 9; i++) {
            if(i != y) {
                if(super.getCase(x, i).getValeur().equals(casesudo.getValeur())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Methode pour verifier s'il existe un doublon sur la colonne
     * @param casesudo case que l'on veut verifier
     * @param x coordonnee x de casesudo
     * @param y coordonee y de casesudo
     * @return true s'il y a un doublon, sinon false
     */

    public Boolean verificationDoublonColonne(Case casesudo, int x, int y) {
        for(int i = 0; i < 9; i++) {
            if(i != x) {
                if(super.getCase(i, y).getValeur().equals(casesudo.getValeur())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Permet de verifier s'il y a un doublon dans un carre de la grille
     * @param caseudo
     * @param x
     * @param y
     * @return true s'il y a un doublon, false s'il n'y en a pas
     */

    public Boolean verificationDoublonCarre(Case caseudo, int x, int y) {
        if(x <= 2) {
            if(y <= 2) {
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        if((i != x) || (j != y)) {
                            if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                return true;
                            }
                        }
                    }
                }
            }
            else {
                if (y >= 6) {
                    for(int i = 0; i < 3; i++) {
                        for(int j = 6; j < 9; j++) {
                            if((i != x) || (j != y)) {
                                if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                    return true;
                                }
                            }

                        }
                    }
                }
                else {
                    for(int i = 0; i < 3; i++) {
                        for(int j = 3; j < 6; j++) {
                            if((i != x) || (j != y)) {
                                if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                    return true;
                                }
                            }

                        }
                    }
                }
            }
        }
        else {
            if (x >= 6) {
                if(y <= 2) {
                    for(int i = 6; i < 9; i++) {
                        for(int j = 0; j < 3; j++) {
                            if((i != x) || (j != y)) {
                                if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                else {
                    if (y >= 6) {
                        for(int i = 6; i < 9; i++) {
                            for(int j = 6; j < 9; j++) {
                                if((i != x) || (j != y)) {
                                    if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        for(int i = 6; i < 9; i++) {
                            for(int j = 3; j < 6; j++) {
                                if((i != x) || (j != y)) {
                                    if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                if(y <= 2) {
                    for(int i = 3; i < 6; i++) {
                        for(int j = 0; j < 3; j++) {
                            if((i != x) || (j != y)) {
                                if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                else {
                    if (y >= 6) {
                        for(int i = 3; i < 6; i++) {
                            for(int j = 6; j < 9; j++) {
                                if((i != x) || (j != y)) {
                                    if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                        return true;
                                    }
                                }

                            }
                        }
                    }
                    else {
                        for(int i = 3; i < 6; i++) {
                            for(int j = 3; j < 6; j++) {
                                if((i != x) || (j != y)) {
                                    if(super.getCase(i, j).getValeur().equals(caseudo.getValeur())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
