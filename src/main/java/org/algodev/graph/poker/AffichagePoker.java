package org.algodev.graph.poker;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.algodev.jeux.poker.PartiePoker;

/**
 * Classe qui créer la fenêtre dans lequel sera le poker.
 */

public class AffichagePoker {

    private Stage fenetrePoker;
    private StackPane root;
    private PartiePoker partie;
    private AffichagePartie affichagePartie;
    private BoutonsPoker boutonsPoker;
    private Stage menu;

    /**
     * Constructeur de l'affichage de la fenêtre.
     * @param menu Menu principal contenant les autres jeux.
     */

    public AffichagePoker(Stage menu) {
        this.menu = menu;
        fenetrePoker = new Stage();
        root = new StackPane();
        root.setStyle("-fx-background-color: green");
        Scene scene = new Scene(root, 1600, 900);
        fenetrePoker.setScene(scene);
        fenetrePoker.setResizable(false);
        fenetrePoker.show();
        partie = new PartiePoker();
        choixNbJoueurs();
        retourMenu();
    }

    /**
     * Affiche une choicebox pour l'utilisateur.
     * L'utilisateur choisit le nombre de joueurs.
     */

    public void choixNbJoueurs() {
        Text choix = new Text();
        choix.setTranslateY(-50);
        choix.setStyle("-fx-font-size: 20");
        choix.setFill(Color.WHITE);
        choix.setText("Choisissez un nombre de joueurs : ");
        ChoiceBox<Integer> nbJoueurs = new ChoiceBox<>(FXCollections.observableArrayList(2, 3, 4));
        nbJoueurs.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch ((Integer) t1) {
                case 0 : partie.initialisePartie(2); break;
                case 1 : partie.initialisePartie(3); break;
                case 2 : partie.initialisePartie(4); break;
            }
            this.affichagePartie = new AffichagePartie(partie, root);
            this.boutonsPoker = new BoutonsPoker(partie, affichagePartie, root);
            nbJoueurs.setVisible(false);
            choix.setVisible(false);
        });
        root.getChildren().addAll(nbJoueurs, choix);
    }

    /**
     * Affiche un bouton pour retourner au menu où se trouvent les autres jeux.
     */

    public void retourMenu() {
        Button retour = new Button();
        retour.setOnMouseClicked(mouseEvent -> {
            this.menu.show();
            this.fenetrePoker.close();
        });
        retour.setText("Retour");
        retour.setTranslateX(-700);
        retour.setTranslateY(375);
        retour.prefHeight(100);
        root.getChildren().add(retour);
    }
}
