package main.java.org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Source du code : https://howtoprogramwithjava.com/enums/

public class Paquet {

    private List<Carte> paquet;

    public Paquet() {
        this.paquet = new ArrayList<Carte>();
        for(int i = 0; i < 13; i++) {
            Hauteur hauteur = Hauteur.values()[i];
            for(int j = 0; j < 4; j++) {
                Couleur couleur = Couleur.values()[j];
                Carte carte = new Carte(hauteur, couleur);
                this.paquet.add(carte);
            }
        }
        Collections.shuffle(paquet);
    }

    public List<Carte> getPaquet() {
        return this.paquet;
    }
}
