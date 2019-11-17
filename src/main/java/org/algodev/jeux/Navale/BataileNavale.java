package org.algodev.Navale;

import java.util.Scanner;

public class BataileNavale {
    Navale J1;
    Navale J2;

    /*
     * Initialise les deux joueurs
     */
    public BataileNavale() {
        J1 = new Navale();
        J2 = new Navale();
    }

    /*
     * Permet de lancer une partie en mode console
     */
    public void partie() {
        int tour = 1;
        int x, y;
        System.out.println("Le joueur 1 doit choisir ou placer ses bateaux");
        J1.initialisation();
        for (x = 0; x < 20; x++) {
            System.out.println();
        }
        System.out.println("Le joueur 2 doit choisir ou placer ses bateaux");
        J2.initialisation();
        while (!(J1.findePartie()) && !(J2.findePartie())) {
            Scanner scanner = new Scanner(System.in);
            for (x = 0; x < 20; x++) {
                System.out.println();
            }
            System.out.println("C'est au tour du joueur " + tour);
            if (tour == 1)
                J1.affichageBataille();
            else
                J2.affichageBataille();
            System.out.println("Ou voulez-vous lancer une torpille ?");
            System.out.println("Entrer un X");
            x = scanner.nextInt();
            System.out.println("Entrer un Y :");
            y = scanner.nextInt();
            if (tour == 1) {
                for (x = 0; x < 20; x++) {
                    System.out.println();
                }
                while (!(J2.entrerUtilisateur(x, y))) {
                    System.out.println("Mauvaise coordonee recomencer");
                    System.out.println("Entrer un X");
                    x = scanner.nextInt();
                    System.out.println("Entrer un Y :");
                    y = scanner.nextInt();
                }
                J1.modififierGrilleAdv(x,y,J2.attack(x, y));
                tour = 2;
            } else {
                while (!(J1.entrerUtilisateur(x, y))) {
                    System.out.println("Mauvaise coordonee recomencer");
                    System.out.println("Entrer un X");
                    x = scanner.nextInt();
                    System.out.println("Entrer un Y :");
                    y = scanner.nextInt();
                }
                J2.modififierGrilleAdv(x, y,J1.attack(x, y));
                tour = 1;
            }
        }
        if (J1.findePartie())
            System.out.println("Le joueur 2 a gagner!!");
        else
            System.out.println("Le joueur 1 a gagner!!");
    }
}

