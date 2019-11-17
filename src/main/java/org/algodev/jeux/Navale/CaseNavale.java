package org.algodev.Navale;

import org.algodev.grille.Case;

public class CaseNavale extends Case {
    protected boolean etat;

    //Initialise l'état de la case qui correspond a n'a pas été toucher
    public CaseNavale (){
        super();
        etat = true;
    }

    //change l'état en a été toucher
    public void setEtat() {
       etat = false;
    }

    public boolean isEtat() {
        return etat;
    }
}
