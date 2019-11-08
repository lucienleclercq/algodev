package org.algodev.jeux.sudoku;

import org.algodev.jeux.Grille;
import org.algodev.jeux.Case;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Sudoku extends Grille {
    private int random;

    /**
     * Constructeur, lit la grille dans un fichier texte et appelle methode dans la classe mere pour remplir la grille
     * @throws FileNotFoundException
     */

    public Sudoku () throws IOException {
    	super(9,9, "Sudoku");
    	this.random = (int) (Math.random() * 300) +1;
    	System.out.println(random);
    	String url = "/grilles-et-solutions/Grilles-faciles.txt";
        InputStream input = this.getClass().getResourceAsStream(url);                       // Indispensable, permet d'aller chercher le fichier dans le jar, besoin d'ajouter l'arbo dans le classpath
        BufferedReader br = new BufferedReader(new InputStreamReader(input));               // Initialise un buffer et un InputStreamReader
        for(int i = 1; i < random; i++) {
            br.readLine();                                                                  // Lit la ligne
        }
        String valeurs = br.readLine();
        super.remplirGrille(valeurs);
        input.close();
    }
    
    /**
     * Demande un entier a entrer dans la grille a l'utilisateur
     * @return la valeur entree
     */
    
    public Integer entrerValeur() {
    	Scanner scanner = new Scanner(System.in);
    	int valeur;
        do {
        	System.out.println("ENTRER UN ENTIER ENTRE 1 ET 9 : ");
        	valeur = scanner.nextInt();
        } while(valeur < 0 && valeur > 10);
        return valeur;
    }
    
    public Boolean verifModifiable(Case cases) {
    	CaseSudoku casesudoku = (CaseSudoku) cases;
    	return casesudoku.getModifiable();
    }

    // TODO: 05/10/2019
    public void modifierValeur() {
    	ArrayList<Integer> listecoordonnees = new ArrayList<Integer>();
    	String valeur;
        do {
        	listecoordonnees = coordonneesUtilisateur();
        } while (!(coordonneesCorrectes(listecoordonnees)) || !(verifModifiable(this.getCase(listecoordonnees.get(1), listecoordonnees.get(0)))));
        valeur = entrerValeur().toString();
        this.getCase(listecoordonnees.get(1), listecoordonnees.get(0)).setValeur(valeur);
    }

    /**
     * Methode a� appeler pour verifier si tous les champs de la grille sont remplis
     * @return true si remplie, false si un champ est toujours a� 0
     */
    public Boolean grilleRemplie() {
        for(ArrayList<Case> liste : this.grille) {
            for(Case cases : liste) {
                if (cases.toString().equals("0")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Methode pour charger la solution de la grille
     * @return String, la grille
     * @throws IOException
     */

    public String chargerSolution() throws IOException {
        String url = "/grilles-et-solutions/Solutions-faciles.txt";
        InputStream input = this.getClass().getResourceAsStream(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        for(int i = 1; i < this.random; i++) {
            br.readLine();
        }
        String valeurs = br.readLine();
        return valeurs;
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
     * Methode pour afficher la grille du Sudoku sur la console
     */

    public void affichageGrille() {
        int i = 0, j = 0;
        for (ArrayList<Case> cases : grille) {
            System.out.print("| ");
            for (Case valeur : cases) {
                System.out.print(valeur.toString() + " | ");
                j++;
                if(j%3 == 0 && j < 9) {
                    System.out.print(" | ");
                }
            }
            j = 0;
            i++;
            System.out.println();
            if (i%3 == 0 && i < 9) {
                System.out.println();
            }
        }
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

    /**
     * Methode a appeler pour lancer une partie de Sudoku en mode console
     * @throws FileNotFoundException
     */

    public void partie() throws IOException {
        affichageGrille();
        String solution = chargerSolution();
        do {
            modifierValeur();
            if(grilleRemplie()) {
                if(finDePartie(solution)) {
                    break;
                }
            }
            affichageGrille();
        } while(true);
        System.out.println("Bravo");
    }
}
