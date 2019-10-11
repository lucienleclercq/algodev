package org.algodev.grille;

public class Case {
    protected String valeur;         // Ce qu'il y a dans la case

    /**
     * Constructeur qui initialise toutes les cases à vide et les rend non-modifiable
     */
    public Case() {
        valeur = "vide";
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return valeur;
    }
}