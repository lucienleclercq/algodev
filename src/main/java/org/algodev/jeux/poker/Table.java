package poker;

import poker.Carte;
import poker.Joueur;

import java.util.ArrayList;
import java.util.Collections;

public class Table {

    protected ArrayList<Carte> tapis;
    protected ArrayList<Joueur> joueurs;
    protected ArrayList<Carte> paquet;
    int pot;

    public Table(){
        pot = 0;
        tapis = new ArrayList<Carte>();
        joueurs = new ArrayList<Joueur>();
        paquet = new ArrayList<Carte>();
    }

    /*
     * Remplie le paquet avec les 52 cartes
     * A appelé a chaque debut de partie
     */
    public void remplirPaquet(){
        int i = 1;
        Carte carte;
        paquet.clear();
        while (i <= 13) {
            //11: vallet, 12: dame, 13: roi;
            carte = new Carte(i, "coeur");
            paquet.add(carte);
            carte = new Carte(i, "carreaux");
            paquet.add(carte);
            carte = new Carte(i, "trefle");
            paquet.add(carte);
            carte = new Carte(i, "pique");
            paquet.add(carte);
            i++;
        }
        Collections.shuffle(paquet);
    }

    /*
     * Affiche la liste des carte passer en parametre
     * Utilisation uniquement pour les tests
     */
    public void aff(ArrayList<Carte> liste){
        for (Carte carte:liste) {
            carte.aff();
        }
        System.out.println();
    }

    /*
     * Ajout d'un nouveau joueur a la liste
     * Modifier pour ajouter un joueur déja existant a la table
     */
    public void ajoutJoueur(){
        joueurs.add(new Joueur(200));
    }

    /*
     *  Ajoute nb carte au tapis
     *  n'utilisé cette fonction que 3 fois
     *  1er : nb = 3
     *  2em et 3em : nb = 1
     */
    public void ajoutCarteTapis(int nb){
        int i = 1;
        while (i <= nb){
            tapis.add(paquet.get(0));
            paquet.remove(0);
            i++;
        }
    }

    /*
     * Compare le jeux de 2 joueur renvoi le joueur avec le meilleur jeux ou null si leur jeux sont égaux
     * Est utilisé dans la fonction listeGagnant
     */
    public Joueur compareJoueur (Joueur j1, Joueur j2){
        int i = 0;
        ArrayList<Carte> comp1;
        ArrayList<Carte> comp2;
        if(j1.getNiveau() > j2.getNiveau())
            return j1;
        if(j1.getNiveau() < j2.getNiveau())
            return j2;
        comp1 = j1.getBestCompo();
        comp2 = j2.getBestCompo();
        for(i = 0; i < comp1.size(); i++){
            if (comp1.get(i).getValeur() != 1 &&  comp2.get(i).getValeur() == 1)
                return j2;
            if (comp1.get(i).getValeur() == 1 &&  comp2.get(i).getValeur() != 1)
                return j1;
            if(comp1.get(i).getValeur() < comp2.get(i).getValeur())
                return j1;
            if(comp1.get(i).getValeur() > comp2.get(i).getValeur())
                return j2;
        }
        return null;
    }

    /*
     * Permet d'obtenir une liste contenant le ou les joueurs gagnants.
     */
    public ArrayList<Joueur> listeGagnant(){
        ArrayList<Joueur> Listejoueur = new ArrayList<>();
        ArrayList<Joueur> Liste = new ArrayList<>();
        Joueur best;
        for (Joueur j : joueurs){
            if (j.isEtat())
                Listejoueur.add(j);
        }
        best = compareJoueur(joueurs.get(0), joueurs.get(1));
        if (best == null){
            Liste.add(Listejoueur.get(0));
            Listejoueur.remove(0);
            Liste.add(Listejoueur.get(0));
            Listejoueur.remove(0);
        }
        if (best.equals(joueurs.get(0))){
            Liste.add(Listejoueur.get(0));
            Listejoueur.remove(0);
            Listejoueur.remove(0);
        }
        if (best.equals(joueurs.get(1))){
            Liste.add(Listejoueur.get(1));
            Listejoueur.remove(0);
            Listejoueur.remove(0);
        }
        for (Joueur j : Listejoueur){
            best = compareJoueur(j, Liste.get(0));
            if (best == null){
                Liste.add(best);
            }
            else if (best.equals(j)){
                Liste.clear();
                Liste.add(j);
            }
        }
        return Liste;
    }

    /*
    * Permet de Lancer la partie
    * A modifier pour un déroulement de partie complet, avec ajout de joueur en cour de partie
    * Mettre en parametre le nb de joueur, minimum 2 pour lancer la partie
    */
    public void lancerPartie(int nbjoueur){
        int i = 0;
        ArrayList<Joueur> Listegagnant;
        remplirPaquet();
        while (joueurs.size() != nbjoueur){
            ajoutJoueur();
        }
        while (i < nbjoueur){
            Joueur j;
            j = joueurs.get(i);
            j.clearMain();
            j.setMain(paquet.get(0), paquet.get(1));
            paquet.remove(0);
            paquet.remove(0);
            i++;
        }
        ajoutCarteTapis(5);
        System.out.println("Le tapis :");
        aff(tapis);
        for (Joueur j : joueurs) {
            System.out.println("Main joueur :");
            aff(j.getMain());
            System.out.println("Meilleur compo :");
            aff(j.meilleurJeux(tapis));
        }
        System.out.println("Les compos gagnantes sont :");
        Listegagnant = listeGagnant();
        for (Joueur j : Listegagnant) {
            aff(j.getBestCompo());
        }
    }
}
