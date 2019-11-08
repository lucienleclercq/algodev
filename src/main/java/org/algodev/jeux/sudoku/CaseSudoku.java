package org.algodev.jeux.sudoku;

import org.algodev.jeux.Case;

public class CaseSudoku extends Case {
	protected Boolean modifiable;    // True ou False, défini sur une case peut être modifiée ou non, utile uniquement pour Sudoku
	
	public CaseSudoku() {
		super();
		this.modifiable = true;
	}
	
	/**
     * Methode pour changer l'attribut modifiable et rendre la valeur de la case changeable
     */
    public void modifiableToFalse() {
        this.modifiable = false;
    }
    
    public Boolean getModifiable() {
        return modifiable;
    }
}
