package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainDeJoueur {

    private List<Carte> main;       //Liste de cartes dans la main, deux cartes.
    private long puissancedelamain;  //Une fois la main finale du joueur (7 cartes) attribuee, cette valeur definira la puissance de cette main.

    public MainDeJoueur() {
        this.main = new ArrayList<>();
        this.puissancedelamain = 0;
    }

    /**
     * Va créer la main d'un joueur.
     * @param cartes Passer seulement DEUX cartes.
     */

    public MainDeJoueur(Carte...cartes) {
        this.main = new ArrayList<>();
        this.main.addAll(Arrays.asList(cartes));        //Prend les cartes (2 cartes) en argument et les ajoute dans la main.
        this.puissancedelamain = 0;
    }

    public MainDeJoueur(List<Carte> cartes) {
        this.main = new ArrayList<>();
        this.main.addAll(cartes);
        this.puissancedelamain = 0;
    }

    public void ajouterCarteMain(Carte carte) {
        this.main.add(carte);
    }

    public void viderMain() {
        this.main.clear();
    }

    public long getPuissancedelamain() {
        return puissancedelamain;
    }

    public void setPuissancedelamain(long puissancedelamain) {
        this.puissancedelamain = puissancedelamain;
    }

    public List<Carte> getMain() {
        return main;
    }

    public void setMain(List<Carte> main) {
        this.main = main;
    }

    public Carte getCarte(int indice) {
        return main.get(indice);
    }

    /**
     * Méhode qui va vérifier si deux mains sont les mêmes ou non.
     * @param mainDeJoueur Main du joueur que l'on veut comparer.
     * @return True si les deux mains sont les memes.
     */

    public boolean equals(MainDeJoueur mainDeJoueur) {
        int i = 0;
        for(Carte carte : mainDeJoueur.getMain()) {
            if(this.getCarte(i).getHauteur().compareTo(carte.getHauteur()) != 0) {
                return false;
            }
            i++;
        }
        return true;
    }
}
