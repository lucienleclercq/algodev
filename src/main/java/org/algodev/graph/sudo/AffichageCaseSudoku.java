package org.algodev.graph.sudo;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.algodev.graph.Nombre;
import org.algodev.jeux.sudoku.CaseSudoku;

/**
 * Classe qui gere l'evenement sur les cases du Sudoku
 */

public class AffichageCaseSudoku {
    private Group g;
    private Boolean selectionnee;
    private Nombre nombre;
    private CaseSudoku casesudo;

    public AffichageCaseSudoku (Nombre nb, CaseSudoku casesudo, GrilleSudo grille) {
        this.selectionnee = false;
        this.nombre = nb;
        this.casesudo = casesudo;
        g = new Group();
        //Quand une case est cliquée
        nb.getRectangle().setOnMouseClicked(mouseEvent -> {
            if(!getSelectionnee() && !grille.caseDejaSelectionnee()) {                                              //Si la case n'est pas sélectionnée ET qu'aucune autre case ne l'est pas
                setSelectionnee(true);                                                                              //Sélectionnée passe true
                nombre.setCouleur(Color.web("#505C5E", 1));                                      //Change la couleur de la case
            }
            else {
                setSelectionnee(false);                                                                             //Sinon, passe false
                nombre.setCouleur(Color.web("0xC4C4C4", 1));                                     //Remet la couleur d'origine
            }
        });
    }

    /**
     * Permet de changer la valeur de l'attribut selectionnee de l'objet
     * @param valeur true ou false
     */

    public void setSelectionnee(Boolean valeur) {
        this.selectionnee = valeur;
    }

    public Boolean getSelectionnee() {
        return this.selectionnee;
    }

    public Nombre getNombre() {
        return this.nombre;
    }

    public Group getG() {
        return g;
    }

    public CaseSudoku getCaseSudoku() {
        return this.casesudo;
    }
}
