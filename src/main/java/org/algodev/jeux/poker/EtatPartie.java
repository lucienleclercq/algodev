package org.algodev.jeux.poker;

public class EtatPartie {
    private static int etatpartie;      //Marqueur pour savoir dans quelle manche on est. 0 : etat initial, 1 : le flop, 2 : la river, 3 : le turn; 4 : vainqueur et fin de manche.
    private static int dealerpartie;    //Indice du dealer dans le tableau. Celui qui parle en debut de partie, change qu'au debut de chaque partie.
    private static int dealermanche;    //Indice du dealer de la manche. Bouge quand le dealer se couche/a mis tapis.

    public EtatPartie() {
        etatpartie = 0;
        dealerpartie = 0;
        dealermanche = 0;
    }

    /**
     * Appeler cette methode a chaque fin de tour.
     * Etat partie : 0, 1, 2, 3, 4.
     */

    public void avancerPartie() {
        etatpartie++;
    }

    /**
     * Methode qui incremente dealerpartie. A appeler lors du debut de partie.
     * Reset le dealermanche egalement.
     * @param nbjoueursrestants Entier, nombre de joueurs en jeu.
     */

    public void changementDealerPartie(int nbjoueursrestants) {
        if(nbjoueursrestants <= dealerpartie+1) {
            dealerpartie = 0;
        }
        else dealerpartie++;
        dealermanche = dealerpartie;
    }

    /**
     * Methode qui increment dealermanche. Si le dealer de la partie se couche, le joueur suivant est le dealer.
     * @param nbjoueursrestants Entier, nombre de joueurs en jeu.
     */

    public void changementDealerManche(int nbjoueursrestants) {
        if(nbjoueursrestants <= dealermanche) {
            dealermanche = 0;
        }
        else dealermanche++;
    }

    public int getEtatpartie() {
        return etatpartie;
    }

    public int getDealerPartie() {
        return dealerpartie;
    }

    public int getDealerManche() {
        return dealermanche;
    }

    public void setEtatpartie(int etat) {
        etatpartie = etat;
    }
}
