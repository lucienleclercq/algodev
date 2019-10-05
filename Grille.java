package grille;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Grille {
    protected ArrayList<ArrayList<Case>> grille;

    public Grille getGrille() {
        return this;
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
        int i = 1, j = 1;
        for (ArrayList<Case> cases : grille) {
            for (Case valeur : cases) {
                System.out.print(valeur.toString() + " ");
                if(i == 3) {
                    i = 0;
                    //System.out.print(" ");
                }
                i++;
            }
            System.out.println();
            if(j == 3) {
                j = 0;
                //System.out.println();
            }
            j++;
        }
    }

    public void remplirGrille(String valeurs) {
        StringTokenizer line = new StringTokenizer(valeurs);
        for(ArrayList<Case> cases : grille)  {
            for(Case valeur : cases) {
                valeur.setValeur(line.nextToken());
            }
        }
    }

    public void remplirGrilleSudoku(String valeurs) {
        StringTokenizer line = new StringTokenizer(valeurs);
        String c;
        for (ArrayList<Case> cases : grille) {
            for (Case valeur : cases) {
                c = line.nextToken();
                if (Integer.parseInt(c) < 10 && Integer.parseInt(c) > 0)
                    valeur.setModifiable();
                valeur.setValeur(c);
            }
        }
    }
}
