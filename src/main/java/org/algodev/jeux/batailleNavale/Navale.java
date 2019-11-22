package org.algodev.jeux.batailleNavale;

import org.algodev.jeux.Case;
import org.algodev.jeux.Grille;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Navale extends Grille {

    protected ArrayList<ArrayList<Case>> ListeBateau;
    protected Grille Grilleadv;


    public Navale(){
        super(10,10,"Bataille Navale");
        ListeBateau = new ArrayList<ArrayList<Case>>();
        Grilleadv = new Grille(10, 10);
    }

    //Permet de modifier la vision de la grille de l'adversaire
    public void modififierGrilleAdv(int x, int y, boolean touch){
        if (touch)
            Grilleadv.getCase(x-1,y-1).setValeur("toucher");
        else
            Grilleadv.getCase(x-1,y-1).setValeur("rater");
    }

    public boolean findePartie() {
        return (ListeBateau.isEmpty());
    }

    //permet de savoir ou est le bateau placer en parametre dans la liste de bateau
    public int bateauPresent(Case bateau){
        int i = 0;
        while (i < ListeBateau.size()){
            if(ListeBateau.get(i).contains(bateau))
                return i;
            i++;
        }
        return -1;
    }

    /*
     * Permet de gérer une attack reçu
     */
    public boolean attack(int x,int y){
        CaseNavale caseNavale = (CaseNavale) this.getCase(x-1,y-1);
        caseNavale.setEtat();
        int i = bateauPresent(this.getCase(x-1, y-1));
        if (i != -1) {
            this.getCase(x - 1, y - 1).setValeur("toucher");

            ListeBateau.get(i).remove(this.getCase(x-1, y-1));
            if(ListeBateau.get(i).isEmpty()) {
                ListeBateau.remove(i);
            }
            return true;
        }
        else
            this.getCase(x - 1, y - 1).setValeur("rater");
        return false;
    }

    /*
     *Methode appéle pour placer un Bateau une fois les coordonnées vérifier
     */
    public void placerBateau(int x, int y, int taille, String oriantation, int num){
        int i = 0;

        ListeBateau.add(new ArrayList<>());
        if(oriantation.equals("Gauche")){
            while (i < taille){
                grille.get(x-1).get(y-1-i).setValeur("Bateau");
                ListeBateau.get(num).add(this.getCase(x-1, y-1-i));
                i++;
            }
        }
        else if(oriantation.equals("Droite")){
            while (i < taille){
                grille.get(x-1).get(y-1+i).setValeur("Bateau");
                ListeBateau.get(num).add(this.getCase(x-1, y-1+i));
                i++;
            }
        }
        else if(oriantation.equals("Haut")){
            while (i < taille){
                grille.get(x-1-i).get(y-1).setValeur("Bateau");
                ListeBateau.get(num).add(this.getCase(x-1-i, y-1));
                i++;
            }
        }
        else if(oriantation.equals("Bas")){
            while (i < taille){
                grille.get(x-1 +i).get(y-1).setValeur("Bateau");
                ListeBateau.get(num).add(this.getCase(x-1+i, y-1));
                i++;
            }
        }
    }

    /*
     * permet de vérifier si les coordonnés entrer sont valables ou non en fonction de l'oriantation
     */
    public boolean verifPlacement(int x, int y, int taille, String oriantation){
        int i = 0;
        if (x < 1 || x > 10|| y < 1 ||y > 10)
            return false;

        if(oriantation.equals("Gauche")){
            if (y-taille < 0)
                return false;
            while (i < taille){
                if (grille.get(x-1).get(y-1-i).toString().equals("Bateau"))
                    return false;
                i++;
            }
        }
        else if(oriantation.equals("Droite")){
            if (y-1+taille > grille.size())
                return false;
            while (i < taille){
                if(grille.get(x-1).get(y-1+i).toString().equals("Bateau"))
                    return false;
                i++;
            }
        }
        else if(oriantation.equals("Haut")){
            if (x-taille < 0)
                return false;
            while (i <taille){
                if(grille.get(x-1-i).get(y-1).toString().equals("Bateau"))
                    return false;
                i++;
            }
        }
        else if(oriantation.equals("Bas")){
            if (x-1+taille > grille.size()){
                return false ;
            }

            while (i < taille){
                if(grille.get(x-1+i).get(y-1).toString().equals("Bateau"))
                    return false;
                i++;
            }
        }
        if (!(oriantation.equals("Bas")) && !(oriantation.equals("Haut")) && !(oriantation.equals("Gauche")) && !(oriantation.equals("Droite")))
            return false;
        return true;
    }

    public boolean entrerUtilisateur(int x, int y){
        if(x < 1 || y < 1 || x > 10 || y > 10)
            return false;
        CaseNavale nav = (CaseNavale) this.getCase(x-1,y-1);
        return nav.isEtat();
    }
    /*
     *Permet de entrer les coordonnes du bateux, de taille plcar en parametre
     * lance automatiquement la vérification des coordonnées
     */
    public boolean entrerBateau(int taille, int num){
        int x, y;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Placement bateau taille :"+ taille);
        System.out.println("Entrer un X :");
        x = scanner.nextInt();
        System.out.println("Entrer un Y :");
        y = scanner.nextInt();
        System.out.println("Entrer une oriantation : Haut Bas Droite Gauche");
        String oriantation = scanner.next();
        if(verifPlacement(y , x ,taille, oriantation)){
            placerBateau(y, x, taille, oriantation, num);
            return true;
        }
        return false;
    }

    public void affichageBataille(){
        int i;
        Grilleadv.affiche();
        for(i=0;i != 20;i++){
            System.out.print("-");
        }
        System.out.println();
        affiche();
    }
    /*
     * Prepare le plateau avec les différents bateau
     * A lancer au début de chaque partie
     * Boucle tant que chaque bateau n'est pas placer correctement
     */
    public void initialisation () {
        while (!(entrerBateau(5,0)));
        affiche();
        while (!(entrerBateau(4,0)));
        affiche();
        while (!(entrerBateau(3,0)));
        affiche();
        while (!(entrerBateau(3,0)));
        affiche();
        while (!(entrerBateau(2,0)));
        affiche();

    }
}