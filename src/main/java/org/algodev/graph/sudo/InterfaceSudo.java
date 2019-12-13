package org.algodev.graph.sudo;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
        this.h = h;
        this.w = w;
        choixDifficulte(scene);
    }

    /**
     * Créer une choice box pour choisir la difficulté de la grille.
     * @param scene La fenêtre.
     */

    public void choixDifficulte(Scene scene) {

        ChoiceBox<String> difficulte = new ChoiceBox<>(FXCollections.observableArrayList("Facile", "Moyen", "Difficile"));  //Crée la choicebox avec 3 choix de difficulté.
        difficulte.setLayoutX(10);
        difficulte.setLayoutY(200);
        difficulte.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {                   //Event quand il y a sélection d'un champ dans la choicebox.
            switch ((Integer) t1) {
                case 0 : instanciationSudoku("faciles");
                    i = new GrilleSudo(500,100,(int)(w*0.052),(int)(w*0.006) , (int)(w*0.001), traitementsudoku, scene); //Création de la grille de Sudoku.
                    break;
                case 1 : instanciationSudoku("intermediaires");
                    i = new GrilleSudo(500,100,(int)(w*0.052),(int)(w*0.006) , (int)(w*0.001), traitementsudoku, scene); //Création de la grille de Sudoku.
                    break;
                case 2 : instanciationSudoku("expertes");
                    i = new GrilleSudo(500,100,(int)(w*0.052),(int)(w*0.006) , (int)(w*0.001), traitementsudoku, scene); //Création de la grille de Sudoku.
                    break;
            }
            sudo.getChildren().add(i.getG());
            creerMenu(scene);
            difficulte.setVisible(false);
        });
        this.sudo.getChildren().add(difficulte);
    }

    /**
     * Crée le sudoku.
     * @param difficulte Difficulté choisie par l'utilisateur.
     */

    public void instanciationSudoku(String difficulte) {
        try {
            this.traitementsudoku = new Sudoku(difficulte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Créer le menu du Sudoku : bouton Vérifier et Nouvelle Grille
     * @param scene la scene du jeu provenant de Fenetre.java
     */

    public void creerMenu(Scene scene) {
        Button verifier = creerButton(10, 200, 125, 25, "Verifier");
        verifier.setOnAction(actionEvent -> {                                                           //Si action sur bouton Verifier
            i.verifier(traitementsudoku, 1300, 300, 50, 2);                               //Appelle methode verifier de GrilleSudoku.java
        });
        Button nouvellegrille = creerButton(10, 230, 125, 25, "Nouvelle Grille");

        //Si action sur bouton Nouvelle Grille
        nouvellegrille.setOnAction(actionEvent -> {
                sudo.getChildren().remove(i.getG());                                                   //Supprime la grille actuelle
                choixDifficulte(scene);
                desafficherBoutons(verifier, nouvellegrille);
        });
        this.sudo.getChildren().addAll(nouvellegrille, verifier);
    }

    /**
     * Crée un bouton.
     * @param x Position x.
     * @param y Position y.
     * @param w Largeur.
     * @param h Hauteur.
     * @param text Texte du bouton.
     * @return Bouton créé.
     */

    public Button creerButton(int x, int y, int w, int h, String text) {
        Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefHeight(h);
        button.setPrefWidth(w);
        button.setText(text);
        button.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        return button;
    }

    /**
     * Désaffiche le bouton nouvelle grille et vérifier.
     * Appelée quand on clique sur nouvelle grille.
     * @param verifier Bouton vérifier.
     * @param nouvellegrille Bouton nouvelle grille.
     */

    public void desafficherBoutons(Button verifier, Button nouvellegrille) {
        verifier.setVisible(false);
        nouvellegrille.setVisible(false);
    }

    public Group getSudo()
    {
        return sudo;
    }
}
