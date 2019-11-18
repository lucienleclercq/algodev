package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Collections;

public class Table {

    protected ArrayList<Carte> tapis;
    protected ArrayList<JoueurPoker> joueurs;
    protected ArrayList<Carte> paquet;
    protected int pot;
    private int misetable; //mise la plus haute de la partie
    protected int joueursactifs; //reference du joueur qui doit jouer

    public Table(){
        pot = 0;
        tapis = new ArrayList<Carte>();
        joueurs = new ArrayList<JoueurPoker>();
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
        joueurs.add(new JoueurPoker(200));
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
    public JoueurPoker compareJoueur (JoueurPoker j1, JoueurPoker j2){
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
    public ArrayList<JoueurPoker> listeGagnant(){
        ArrayList<JoueurPoker> Listejoueur = new ArrayList<>();
        ArrayList<JoueurPoker> Liste = new ArrayList<>();
        JoueurPoker best;
        for (JoueurPoker j : joueurs){
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
        for (JoueurPoker j : Listejoueur){
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
        ArrayList<JoueurPoker> Listegagnant;
        remplirPaquet();
        while (joueurs.size() != nbjoueur){
            ajoutJoueur();
        }
        while (i < nbjoueur){
            JoueurPoker j;
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
        for (JoueurPoker j : joueurs) {
            System.out.println("Main joueur :");
            aff(j.getMain());
            System.out.println("Meilleur compo :");
            aff(j.meilleurJeux(tapis));
        }
        System.out.println("Les compos gagnantes sont :");
        Listegagnant = listeGagnant();
        for (JoueurPoker j : Listegagnant) {
            aff(j.getBestCompo());
        }
    }
    /*liste des joueurs qui se sont coucher*/
    public ArrayList<JoueurPoker> getCoucher()
    {
        ArrayList<JoueurPoker> joueurcoucher = new ArrayList<JoueurPoker>();
        for(JoueurPoker j : joueurs)
        {
            if(j.getCoucher()) joueurcoucher.add(j);
        }
        return joueurcoucher;
    }
    /*systeme de mise*/
    public boolean miser(JoueurPoker joueurs,int valeur)
    {
        if(valeur > 0)
            if(joueurs.getSolde()>=(misetable-joueurs.getMise()))
            {
                joueurs.setSolde(joueurs.getSolde()-(misetable-joueurs.getMise()));
                joueurs.setMise(misetable);
            return true;
            }
        return false;
    }
    /*regarde si les mises de chaques joueurs sont égales*/
    private boolean toutesEgales()
    {
        for(JoueurPoker j : joueurs)
        {
            if(j.getMise() != misetable) return false;
        }
        return true;
    }
    /*gere le déroulement de la partie*/
    private void roulement()
    {
        if (!toutesEgales())
        {
            if(joueursactifs > joueurs.size()) joueursactifs = 0;
            else joueursactifs ++;
        }
        else
        {
            ajoutCarteTapis(1);
            joueursactifs = 0;
        }
    }


}
