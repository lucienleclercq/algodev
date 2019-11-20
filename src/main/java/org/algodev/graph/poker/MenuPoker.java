package org.algodev.graph.poker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import org.algodev.jeux.poker.Table;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuPoker {
    private Group gpoker;
    private ObservableList<Integer> choixnbjoueurs = FXCollections.observableArrayList(2, 3, 4);
    private int nbjoueurs;

    public MenuPoker(int h, int w, Scene scene) throws IOException {
        Table poker = new Table();
        this.gpoker = new Group();
        ChoiceBox<Integer> nbjoueursChoiceBox = new ChoiceBox<>(choixnbjoueurs);
        nbjoueursChoiceBox.setLayoutX(60);
        nbjoueursChoiceBox.setLayoutY(200);
        nbjoueursChoiceBox.setPrefWidth(40);
        nbjoueursChoiceBox.setPrefHeight(25);
        nbjoueursChoiceBox.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        nbjoueursChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                nbjoueurs = t1.intValue() + 2;
                AffichagePartiePoker partiepoker = null;
                partiepoker = new AffichagePartiePoker(h, w , scene, nbjoueurs , poker);
                gpoker.getChildren().add(partiepoker.getStackpanepoker());
                gpoker.getChildren().remove(nbjoueursChoiceBox);
            }
        });
        gpoker.getChildren().add(nbjoueursChoiceBox);
    }

    public Group getGpoker()  {
        return gpoker;
    }
}
