package org.algodev.jeux.poker;

//https://github.com/dpaukov/combinatoricslib3

import org.paukov.combinatorics3.Generator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe prenant toutes les combinaisons de cartes possibles d'un joueur.
 */

public class MainsPossiblesJoueur {
    private List<MainDeJoueur> listemainspossiblesjoueur;
    private long typeDeMain;    //La puissance de la main la plus forte. Si 1000, le joueur a une double paire.

    public MainsPossiblesJoueur() {
        this.listemainspossiblesjoueur = new ArrayList<>();
    }

    /**
     * Méthode créant toutes les combinaisons possibles de 7 cartes.
     * @param listecartes Liste de cartes, contient 7 cartes.
     */

    public void assembleMainsPossibles(List<Carte> listecartes) {
        /*Appelle une librairie pour créer tous les ensembles de cartes possibles.
          simple(int) va definir combien d'éléments on veut dans l'ensemble, ici on veut des ensembles de 5.
          .stream() permet de récupérer cette liste dans le type Stream.
          .collect(Collectors.toList()) va convertir ce stream en liste, pour ainsi récupérer le stream dans la liste de cette classe.
        */
        List<List<Carte>> test = Generator.combination(listecartes).simple(5).stream().collect(Collectors.toList());
        for(List<Carte> main : test) {
            Collections.sort(main);
            this.listemainspossiblesjoueur.add(new MainDeJoueur(main));
        }
    }

    public List<MainDeJoueur> getListeMainsPossiblesJoueur() {
        return listemainspossiblesjoueur;
    }

    public void setTypeDeMain(long typeDeMain) {
        this.typeDeMain = typeDeMain;
    }

    public long getTypeDeMain() {
        return typeDeMain;
    }

    public void videListeMainsPossible() {
        this.listemainspossiblesjoueur.clear();
    }
}
