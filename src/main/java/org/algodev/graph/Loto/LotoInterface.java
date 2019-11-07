package org.algodev.graph.Loto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import org.algodev.jeux.loto.Loto;

import java.util.ArrayList;

public class LotoInterface {//cree l'interface du loto , menu /bouton/carton...
    private Loto traitementLoto;
    private interfaceCartonLoto c;
    private Group loto;
    private Compteur compteur;
    private EtatPartie etatPartie;
    public LotoInterface(int tailleh, int taillew)
    {
        traitementLoto = new Loto();
        loto = new Group();
        compteur = new Compteur(1300,300,50,10 , 2,traitementLoto);
        loto.getChildren().addAll(compteur.getG());
        etatPartie = new EtatPartie(tailleh,taillew);
        loto.getChildren().add(etatPartie.getG());
        c = new interfaceCartonLoto(traitementLoto);
        creemenu();

    }
    private void creemenu()//cree les bouton pour le loto
    {

        Button ajoutgrille = new Button();
        ajoutgrille.setLayoutX(10);
        ajoutgrille.setLayoutY(200);
        ajoutgrille.setPrefWidth(125);
        ajoutgrille.setPrefHeight(25);
        ajoutgrille.setText("ajout grille");
        ajoutgrille.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        ajoutgrille.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                traitementLoto.CreerCarton();
                c.creeCarton();

            }
        });
        Button verifier = new Button();
        verifier.setLayoutX(10);
        verifier.setLayoutY(230);
        verifier.setPrefWidth(125);
        verifier.setPrefHeight(25);
        verifier.setText("verifier");
        verifier.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        verifier.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                if(compteur.gagner(etatPartie.getEtatpartie()))etatPartie.suivant();
            }
        });
        Button suivant = new Button();
        suivant.setLayoutX(10);
        suivant.setLayoutY(260);
        suivant.setPrefWidth(125);
        suivant.setPrefHeight(25);
        suivant.setText("suivant");
        suivant.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        suivant.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                compteur.maj(traitementLoto.aleatoire());
                compteur.vide();
            }
        });
        Button restart = new Button();
        restart.setLayoutX(10);
        restart.setLayoutY(290);
        restart.setPrefWidth(125);
        restart.setPrefHeight(25);
        restart.setText("recommencer");
        restart.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        restart.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                compteur.restart();
                traitementLoto.restart();
                c.restart();
                etatPartie.restart();
            }
        });

        loto.getChildren().add(ajoutgrille);
        loto.getChildren().add(verifier);
        loto.getChildren().add(suivant);
        loto.getChildren().add(restart);
        loto.getChildren().addAll(c.getCarton());
    }
    public Group getLoto()
    {
        return loto;
    }
}
