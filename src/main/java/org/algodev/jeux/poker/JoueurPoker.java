package org.algodev.jeux.poker;


import java.util.ArrayList;
import java.util.Collections;

public class JoueurPoker {
    private ArrayList<Carte> main;
    private ArrayList<Carte> suite;
    private ArrayList<Carte> couleur;
    private ArrayList<Carte> flush;
    private ArrayList<Carte> carre;
    private ArrayList<Carte> brelan;
    private ArrayList<Carte> pair;
    private ArrayList<Carte> meilleur;
    private boolean etat;
    private int solde;
    private int misejoueur; //mise du joueur
    private int niveau;
    private boolean coucher;


    public JoueurPoker(int solde) {
        main = new ArrayList<Carte>();
        suite = new ArrayList<Carte>();
        couleur = new ArrayList<Carte>();
        flush = new ArrayList<Carte>();
        carre = new ArrayList<Carte>();
        brelan = new ArrayList<Carte>();
        pair = new ArrayList<Carte>();
        meilleur = new ArrayList<>();
        this.solde = solde;
        etat = true;
        coucher = false;
    }
    public void setMise(int mise) {
        this.misejoueur = mise;
    }

    public int getMise() {
        return misejoueur;
    }

    public boolean getCoucher()
    {
        return coucher;
    }

    /*
     * Retourne true si le joueur veux rentrer dans la partie
     * non si il ne joue pas
     */
    public boolean isEtat() {
        return etat;
    }

    /*
     * Change l'etat du joueur
     */
    public void changeEtat() {
        etat = !etat;
    }

    /*
     * Retourne la meilleur main obtenue
     */
    public ArrayList<Carte> getBestCompo() {
        return meilleur;
    }

    /*
     * Retourne une valeur entre 1 et 10 selon le jeux que le joueur posséde
     * 1 : pas de jeux
     * 2 : 1 pair
     * 3 : 2 pair
     * 4 : brelan
     * 5 : quinte
     * 6 : couleur
     * 7 : full
     * 8 : carre
     * 9 : quinte flush
     * 10 : quinte royale
     */
    public int getNiveau() {
        return niveau;
    }

    /*
     * recherche si il y a un as dans la liste passer en parametre
     * si il y en a un renvoi la carte
     * sinon renvoi null
     */
    public Carte rechercheAs(ArrayList<Carte> liste) {
        for (Carte carte : liste) {
            if (carte.getValeur() == 1)
                return carte;
        }
        return null;
    }

    /*
     * Etablie le niveau du jeux du joueur
     */
    public void niveaujeux(ArrayList<Carte> jeux) {
        Composition compo = new Composition();
        suite = compo.suite(jeux);
        couleur = compo.couleur(jeux);
        flush = compo.flush(suite, couleur);
        carre = compo.couple(jeux, 4);
        pair = compo.couple(jeux, 2);
        brelan = compo.couple(jeux, 3);
        if (!(flush.isEmpty())) {
            if (flush.get(0).getValeur() == 14) {
                niveau = 10;
            } else
                niveau = 9;
        } else if (!(carre.isEmpty()))
            niveau = 8;
        else if (!(brelan.isEmpty())) {
            if (brelan.size() > 3 || (!(pair.isEmpty())))
                niveau = 7;
            else
                niveau = 4;
        } else if (!(couleur.isEmpty()))
            niveau = 6;
        else if (!(suite.isEmpty()))
            niveau = 5;
        else if (!(pair.isEmpty())) {
            if (pair.size() > 2)
                niveau = 3;
            else
                niveau = 2;
        } else
            niveau = 1;
    }

    /*
     * Retourne et stock la meilleur combinaison de 5 carte en fonction du tapis et du niveau de jeux
     */
    public ArrayList<Carte> meilleurJeux(ArrayList<Carte> tapis){
        int i;
        ArrayList<Carte> jeux = new ArrayList<>();
        Carte as;

        jeux.addAll(tapis);
        jeux.addAll(main);
        Collections.sort(jeux);
        niveaujeux(jeux);
        if (niveau == 10 || niveau == 9) {
            for (i = 0; i < 5; i++) {
                meilleur.add(flush.get(i));
            }
        }
        if (niveau == 8){
            meilleur = carre;
            for (Carte carte: meilleur){
                jeux.remove(carte);
            }
            as = rechercheAs(jeux);
            if (as != null)
                meilleur.add(as);
            else
                meilleur.add(jeux.get(0));
        }
        if (niveau == 7){
            for (i = 0; i < 3; i++) {
                as = rechercheAs(brelan);
                if (as != null){
                    meilleur.add(as);
                    jeux.remove(as);
                    brelan.remove(as);
                }
                else {
                    meilleur.add(brelan.get(0));
                    jeux.remove(brelan.get(0));
                    brelan.remove(0);
                }
            }
            if (pair.isEmpty() && !(brelan.isEmpty())){
                for (i = 0; i < 2; i++) {
                    as = rechercheAs(brelan);
                    if (as != null) {
                        meilleur.add(as);
                        jeux.remove(as);
                        brelan.remove(as);
                    }
                    else {
                        meilleur.add(brelan.get(0));
                        jeux.remove(brelan.get(0));
                        brelan.remove(0);
                    }
                }
            }
            else if (!(pair.isEmpty()) && brelan.isEmpty()){
                for (i = 0; i < 2; i++) {
                    as = rechercheAs(pair);
                    if (as != null) {
                        meilleur.add(as);
                        jeux.remove(as);
                        pair.remove(as);
                    }
                    else {
                        meilleur.add(pair.get(0));
                        jeux.remove(pair.get(0));
                        pair.remove(0);
                    }
                }
            }
            else {
                if(pair.get(0).getValeur() > brelan.get(0).getValeur()) {
                    for (i = 0; i < 2; i++) {
                        as = rechercheAs(pair);
                        if (as != null) {
                            meilleur.add(as);
                            jeux.remove(as);
                            pair.remove(as);
                        }
                        else{
                            meilleur.add(pair.get(0));
                            jeux.remove(pair.get(0));
                            pair.remove(0);
                        }
                    }
                }
                else {
                    for (i = 0; i < 2; i++) {
                        as = rechercheAs(brelan);
                        if (as != null) {
                            meilleur.add(as);
                            brelan.remove(as);
                            jeux.remove(as);
                        }
                        else {
                            meilleur.add(brelan.get(0));
                            jeux.remove(brelan.get(0));
                            brelan.remove(0);
                        }
                    }
                }
            }
        }
        if(niveau == 6){
            for (i = 0; i < 5; i++) {
                as = rechercheAs(couleur);
                if (as != null) {
                    meilleur.add(as);
                    jeux.remove(as);
                    couleur.remove(as);
                }
                else {
                    meilleur.add(couleur.get(0));
                    jeux.remove(couleur.get(0));
                    couleur.remove(0);
                }
            }
        }
        if(niveau == 5){
            for (i = 0; i < 5; i++) {
                meilleur.add(suite.get(0));
                jeux.remove(suite.get(0));
                suite.remove(0);
            }
        }
        if(niveau == 4){
            for (i = 0; i < 3; i++) {
                as = rechercheAs(brelan);
                if (as != null) {
                    meilleur.add(as);
                    brelan.remove(as);
                    jeux.remove(as);
                }
                else {
                    meilleur.add(brelan.get(0));
                    jeux.remove(brelan.get(0));
                    brelan.remove(0);
                }
            }
            for (i = 0; i < 2; i++) {
                as = rechercheAs(jeux);
                if (as != null) {
                    meilleur.add(as);
                    jeux.remove(as);
                }
                else {
                    meilleur.add(jeux.get(0));
                    jeux.remove(0);
                }
            }
        }
        if (niveau == 3){
            for (i = 0; i < 4; i++) {
                as = rechercheAs(pair);
                if (as != null) {
                    meilleur.add(as);
                    jeux.remove(as);
                    pair.remove(as);
                } else {
                    meilleur.add(pair.get(0));
                    jeux.remove(pair.get(0));
                    pair.remove(0);
                }
            }
            as = rechercheAs(jeux);
            if (as != null)
                meilleur.add(as);
            else
                meilleur.add(jeux.get(0));
        }
        if (niveau == 2) {
            for (i = 0; i < 2; i++) {
                as = rechercheAs(pair);
                if (as != null){
                    meilleur.add(as);
                    jeux.remove(as);
                    pair.remove(as);
                }
                else {
                    meilleur.add(pair.get(0));
                    jeux.remove(pair.get(0));
                    pair.remove(0);
                }
            }
            for (i = 0; i < 3; i++) {
                as = rechercheAs(jeux);
                if (as != null) {
                    meilleur.add(as);
                    jeux.remove(as);
                }
                else {
                    meilleur.add(jeux.get(0));
                    jeux.remove(0);
                }
            }
        }
        if (niveau == 1) {
            for (i = 0; i < 5; i++) {
                as = rechercheAs(jeux);
                if (as != null) {
                    meilleur.add(as);
                    jeux.remove(as);
                }
                else{
                    meilleur.add(jeux.get(0));
                    jeux.remove(0);
                }
            }
        }
        return meilleur;
    }

    /*
     * Retourne la liste de carte du joueur
     */
    public ArrayList<Carte> getMain() {
        return main;
    }

    /*
     * Ajout des deux carte placer en parametre a la main du joueur
     */
    public void setMain(Carte carte1, Carte carte2) {
        this.main.add(carte1);
        this.main.add(carte2);
    }

    /*
     * Supprime le contenue de la liste main
     */
    public void clearMain(){
        main.clear();
    }

    /*
     * retourne le solde du joueur
     */
    public int getSolde() {
        return solde;
    }

    /*
     * remplace le solde actuel par la valeur passer en parametre
     */
    public void setSolde(int solde) {
        this.solde = solde;
    }
}
