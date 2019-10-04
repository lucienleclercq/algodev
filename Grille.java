package grille;

import java.util.ArrayList;

public class Grille {
    protected ArrayList<ArrayList<Case>> grille;

    public ArrayList<ArrayList<Case>> getGrille() {
        return grille;
    }

    public Case getCase(int i, int j){
        return grille.get(i).get(j);
    }

    public Grille() {
        this.grille = new ArrayList<ArrayList<Case>>();
    }

    public Grille(int ligne, int col) {
        this.grille = new ArrayList<ArrayList<Case>>();
        for(int i = 0; i < ligne; i++) {
            this.grille.add(new ArrayList<Case>());
            for (int j = 0; j < col; j++) {
                this.grille.get(i).add(new Case());
            }
        }
    }

    public void affiche() {
        for (ArrayList<Case> cases : grille) {
            for (Case valeur : cases) {
                System.out.print(valeur.toString() + " ");
            }
            System.out.println();
        }
    }
}
