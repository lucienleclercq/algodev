package org.algodev.graph.poker;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.algodev.jeux.poker.Carte;
import org.algodev.jeux.poker.Table;

import java.util.ArrayList;

public class AffichagePartiePoker {
    private StackPane stackpanepoker;
    private ArrayList<Rectangle> cartestable;
    private ArrayList<Group> cartesjoueurs;

    public AffichagePartiePoker(int h, double w, Scene scene, int nbjoueurs, Table table) {
        stackpanepoker = new StackPane();
        Group centre = new Group();
        cartestable = new ArrayList<>();
        cartesjoueurs = new ArrayList<>();
        stackpanepoker.setPrefSize(w - w*0.076, h);
        stackpanepoker.setLayoutX(w * 0.076);
        stackpanepoker.setLayoutY(0);
        StackPane.setAlignment(centre, Pos.CENTER);
        table.lancerPartie(nbjoueurs);
        for(Carte cartes: table.getTapis()) {
            Rectangle carte = new Rectangle(100,152);
            String url = "/cartes-poker/";
            carte.setX(105 * table.getTapis().indexOf(cartes));
            StringBuffer strb = new StringBuffer(url + cartes.getCarte());
            System.out.println(strb);
            Image image = new Image(String.valueOf(this.getClass().getResource(strb.toString())));
            carte.setFill(new ImagePattern(image));
            carte.setVisible(false);
            cartestable.add(carte);
            centre.getChildren().add(carte);
        }
        for (int i = 0; i < 4; i++) {
            cartesjoueurs.add(new Group());
            switch (i) {
                case 0 : StackPane.setAlignment(cartesjoueurs.get(i), Pos.BOTTOM_CENTER);
                         cartesjoueurs.get(i).setTranslateY(-20);
                         break;
                case 1 : StackPane.setAlignment(cartesjoueurs.get(i), Pos.CENTER_LEFT);
                         cartesjoueurs.get(i).setTranslateX(20);
                         break;
                case 2 : StackPane.setAlignment(cartesjoueurs.get(i), Pos.TOP_CENTER);
                         cartesjoueurs.get(i).setTranslateY(20);
                         break;
                case 3 : StackPane.setAlignment(cartesjoueurs.get(i), Pos.CENTER_RIGHT);
                         cartesjoueurs.get(i).setTranslateX(-20);
                         break;
            }
            for(int j = 0; j < 2; j++) {
                Rectangle carte = new Rectangle(100, 152, Color.web("0x000000", 1));
                carte.setX(105 * j);
                carte.setFill(new ImagePattern(new Image ("/cartes-poker/red_back.jpg")));
                cartesjoueurs.get(i).getChildren().add(carte);
            }
        }
        stackpanepoker.getChildren().addAll(cartesjoueurs);
        stackpanepoker.getChildren().add(centre);
        Partie(table);
    }

    public void Partie(Table poker) {
        Button suivre = new Button();
        suivre.setLayoutX(10);
        suivre.setLayoutY(230);
        suivre.setPrefWidth(125);
        suivre.setPrefHeight(25);
        suivre.setText("Suivre");
        suivre.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        suivre.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int etat = Table.getEtatpartie();
                if(etat == 4) {
                    int nbjoueur = poker.getNbJoueurs();
                    for(Rectangle cartes : cartestable) {
                        cartes.setVisible(false);
                    }
                    poker.lancerPartie(nbjoueur);
                }
                else {
                    switch(etat)  {
                        case 0 : cacherCartesTable(); break;
                        case 1 : afficherFlop(); break;
                        case 2 : cartestable.get(3).setVisible(true); break;
                        case 3 : cartestable.get(4).setVisible(true); break;
                    }
                }
            }
        });
        StackPane.setAlignment(suivre, Pos.BOTTOM_RIGHT);
        stackpanepoker.getChildren().add(suivre);
    }

    public void cacherCartesTable() {
        for(Rectangle carte : cartestable) {
            carte.setVisible(false);
        }
    }

    public void afficherFlop() {
        for(int i = 0; i < 3; i++) {
            cartestable.get(i).setVisible(true);
        }
    }

    public StackPane getStackpanepoker() {
        return stackpanepoker;
    }
}
