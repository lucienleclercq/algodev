package org.algodev.Navale;

import org.algodev.grille.Case;

public class CaseNavale extends Case {
    protected boolean etat;

    public CaseNavale (){
        super();
        etat = true;
    }

    public void setEtat() {
       etat = false;
    }

    public boolean isEtat() {
        return etat;
    }
}
