package org.algodev.grille;

public class Case {
    protected String valeur;         // Ce qu'il y a dans la case
    protected Boolean modifiable;    // True ou False, défini sur une case peut être modifiée ou non

    /**
     * Constructeur qui initialise toutes les cases à vide et les rend modifiable
     */
    public Case() {
        valeur = "vide";
        this.modifiable = true;
    }

    /**
     * Méthode pour changer l'attribut modifiable et rendre la valeur de la case inchangeable
     */
    public void setModifiable() {
        this.modifiable = false;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Boolean getModifiable() {
        return modifiable;
    }

    @Override
    public String toString() {
        return valeur;
    }
}