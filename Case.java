package org.algodev.grille;

public class Case {
    protected String valeur;         // Ce qu'il y a dans la case

    /**
     * Constructeur qui initialise toutes les cases à vide et les rend non-modifiable
     */
    public Case() {
        valeur = "vide";
    }
    public Case(String valeur) {
        this.valeur = valeur;
    }
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    public String getValeur()
    {
        return valeur;
    }

    @Override
    public String toString() {
        return valeur;
    }
}
