package org.algodev.graph.sudo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.algodev.jeux.sudoku.Sudoku;

import java.io.IOException;

/**
 * Classe initialisant tout ce dont il est necessaire pour afficher le jeu du Sudoku
 */

public class InterfaceSudo {

    private Group sudo;
    private Sudoku traitementsudoku;
    private GrilleSudo i;
    private int h;
    private int w;

    public InterfaceSudo(int h , int w, Scene scene) throws IOException {//h et w etant la taille de l'ecran
        this.sudo = new Group();
        this.traitementsudoku = new Sudoku();
        this.h = h;
        this.w = w;
        i = new GrilleSudo(500,100,(int)(w*0.052),(int)(w*0.006) , (int)(w*0.001), traitementsudoku, scene);//creation de la grille du sudoku
        sudo.getChildren().add(i.getG());
        creerMenu(scene);
    }

    /**
     * Creer le menu du Sudoku : bouton Verifier et Nouvelle Grille
     * @param scene la scene du jeu provenant de Fenetre.java
     */

    public void creerMenu(Scene scene) {
        Button verifier = new Button();
        verifier.setLayoutX(10);
        verifier.setLayoutY(200);
        verifier.setPrefWidth(125);
        verifier.setPrefHeight(25);
        verifier.setText("Verifier");
        verifier.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        verifier.setOnAction(new EventHandler<ActionEvent>() {                      //Si action sur bouton Verifier
            @Override
            public void handle(ActionEvent actionEvent) {
                i.verifier(traitementsudoku, 1300, 300, 50, 2);       //Appelle methode verifier de GrilleSudoku.java
            }
        });
        Button nouvellegrille = new Button();
        nouvellegrille.setLayoutX(10);
        nouvellegrille.setLayoutY(230);
        nouvellegrille.setPrefWidth(125);
        nouvellegrille.setPrefHeight(25);
        nouvellegrille.setText("Nouvelle Grille");
        nouvellegrille.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        nouvellegrille.setOnAction(new EventHandler<ActionEvent>() {                //Si action sur bouton Nouvelle Grille
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    sudo.getChildren().remove(i.getG());                                                                            //Supprime la grille actuelle
                    traitementsudoku = new Sudoku();                                                                                //Creation d'un nouvel objet Sudoku (nouvelle grille)
                    i = new GrilleSudo(500,100,(int)(w*0.052),(int)(w*0.006) , (int)(w*0.001), traitementsudoku, scene);      //Creation d'un nouvel affichage base sur la nouvelle grille
                    sudo.getChildren().add(i.getG());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.sudo.getChildren().add(nouvellegrille);
        this.sudo.getChildren().add(verifier);
    }

    public Group getSudo()
    {
        return sudo;
    }
}
