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

    public AffichageCaseSudoku (double x, double y, double w, double e, int i, int j, Nombre nb, CaseSudoku casesudo, GrilleSudo grille) {
        this.selectionnee = false;
        this.nombre = nb;
        this.casesudo = casesudo;
        g = new Group();
        nb.getRectangle().setOnMouseClicked(new EventHandler<MouseEvent>() {                                            //Quand une case est cliquee
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!getSelectionnee() && !grille.caseDejaSelectionnee()) {                                              //Si la case n'est pas selectionnee ET qu'aucune autre case ne l'est
                    setSelectionnee(true);                                                                              //Selectionne passe true
                    g.getChildren().remove(nombre.getRectangle());                                                      //On retire le rectangle pr l'enlever du group (affichage)
                    nombre.setCouleur(Color.web("#505C5E", 1));                                      //Change la couleur de la case
                    g.getChildren().add(nombre.getRectangle());                                                         //Re-affiche la case
                }
                else {
                    setSelectionnee(false);                                                                             //Sinon, passe false
                    g.getChildren().remove(nombre.getRectangle());                                                      //Retire de l'affichage
                    nombre.setCouleur(Color.web("0xC4C4C4", 1));                                     //Remet la couleur d'origine
                    g.getChildren().add(nombre.getRectangle());                                                         //Re-affiche la case
                }
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
