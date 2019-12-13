package org.algodev.graph;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Credits {

    private Stage menu;
    private StackPane credit;
    private Stage fenetreCredit;
    private List<Group> developpeurs;

    public Credits(Stage menu) {
        this.developpeurs = new ArrayList<>();
        this.menu = menu;
        fenetreCredit = new Stage();
        credit = new StackPane();
        credit.setStyle("-fx-background-color: black");
        Scene scene = new Scene(credit, 600, 600);
        fenetreCredit.setScene(scene);
        fenetreCredit.show();
        retourMenu();
        afficherTexte();
    }

    public void afficherTexte() {
        Text developpeurs = new Text();
        developpeurs.setFill(Color.WHITE);
        developpeurs.setText("Developpeurs : ");
        developpeurs.setStyle("-fx-font-size: 24");
        developpeurs.setTranslateY(-200);
        credit.getChildren().add(developpeurs);
        creerTexteDeveloppeurs();
    }

    public void creerTexteDeveloppeurs() {
        for(int i = 0; i < 5; i++) {
            Group group = new Group();
            this.developpeurs.add(group);
            Text text = new Text();
            group.setTranslateY(-100 + (40 *i));
            text.setStyle("-fx-font-size: 20");
            text.setFill(Color.WHITE);
            switch (i) {
                case 0 : text.setText("Lucile Camus"); break;
                case 1 : text.setText("Lucien Leclercq"); break;
                case 2 : text.setText("Dorianne Jouin"); break;
                case 3 : text.setText("Yanis Hamour"); break;
                case 4 : text.setText("Aymeric Felisiak"); break;
            }
            group.getChildren().add(text);
        }
        credit.getChildren().addAll(developpeurs);
    }

    public void retourMenu() {
        Button retour = new Button();
        retour.setOnMouseClicked(mouseEvent -> {
            this.menu.show();
            this.fenetreCredit.close();
        });
        retour.setText("Retour");
        retour.setTranslateX(-225);
        retour.setTranslateY(250);
        retour.prefHeight(100);
        credit.getChildren().add(retour);
    }
}
