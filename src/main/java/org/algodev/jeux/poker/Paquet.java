package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Source du code : https://howtoprogramwithjava.com/enums/

public class Paquet {

    private List<Carte> paquet;                             //Liste de cartes formant le paquet.

    public Paquet() {
        this.paquet = new ArrayList<Carte>();
        for(int i = 0; i < 13; i++) {
            Hauteur hauteur = Hauteur.values()[i];          //Va prendre la valeur d'une Hauteur (enum) a l'indice i.
            for(int j = 0; j < 4; j++) {
                Couleur couleur = Couleur.values()[j];      //Va prendre la valeur d'une couleur a l'indice j.
                Carte carte = new Carte(hauteur, couleur);  //Creer la carte avec la hauteur et la couleur.
                this.paquet.add(carte);                     //Ajoute la carte au paquet.
            }
        }
        Collections.shuffle(paquet);                        //Permet de mélanger le paquet.
    }

    /**
     * Méthode à appeler lors du lancement d'une nouvelle manche.
     */

    public void remplirPaquet() {
        this.paquet.clear();
        for(int i = 0; i < 13; i++) {
            Hauteur hauteur = Hauteur.values()[i];          //Va prendre la valeur d'une Hauteur (enum) a l'indice i.
            for(int j = 0; j < 4; j++) {
                Couleur couleur = Couleur.values()[j];      //Va prendre la valeur d'une couleur a l'indice j.
                Carte carte = new Carte(hauteur, couleur);  //Crée la carte avec la hauteur et la couleur.
                this.paquet.add(carte);                     //Ajoute la carte au paquet.
            }
        }
        Collections.shuffle(paquet);                        //Permet de mélanger le paquet.
    }

    public void retirerCartePaquet(Carte carte) {
        this.paquet.remove(carte);
    }

    public Carte getCartePaquet(int indice) {
        return this.paquet.get(indice);
    }

    public List<Carte> getPaquet() {
        return this.paquet;
    }
}
