package org.algodev.jeux.poker;

public class Carte implements Comparable<Carte> {
    private int  valeur;
    private String couleur;


    public Carte (int valeur , String couleur){
        this.couleur = couleur;
        this.valeur = valeur;
    }

    /*
     * Affichage pour les test
     */
    protected void aff(){
        System.out.print(valeur +" "+ couleur+"   ");
    }

    /*
     * retourne la valeur de la carte comprit entre 1 et 13
     * 11 : valet
     * 12 : dame
     * 13 : roi
     */
    public int getValeur() {
        return valeur;
    }

    /*
     *retourne la couleur de la carte
     */
    public String getCouleur() {
        return couleur;
    }

    /*
     *  Compare la valeur de la carte avec celle d'une carte placer en paramétre
     *  Utiliser pour trier les cartes par valeurs avec un sort
     */
    public int compareTo(Carte other) {
        if (other.getValeur() >= valeur)
            return 0;
        if (other.getValeur() < valeur)
            return -1;
        return 0;
    }

    public String getCarte() {
        return String.valueOf(valeur)+" "+couleur+".jpg";
    }
}

