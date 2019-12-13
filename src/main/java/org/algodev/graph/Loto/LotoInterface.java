package org.algodev.graph.Loto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.algodev.jeux.loto.Loto;

import java.util.ArrayList;

public class LotoInterface {//cree l'interface du loto , menu /bouton/carton...
    private Loto traitementLoto;
    private Group loto;
    private Compteur compteur;
    private EtatPartie etatPartie;
    private ArrayList<interfacejoueur> joueur;
    public LotoInterface(int tailleh, int taillew)
    {
        joueur = new ArrayList<interfacejoueur>();
        traitementLoto = new Loto();
        loto = new Group();
        compteur = new Compteur(1300,300,50,10 , 2,traitementLoto);
        loto.getChildren().addAll(compteur.getG());
        etatPartie = new EtatPartie(tailleh,taillew);
        loto.getChildren().add(etatPartie.getG());
        creemenu();
    }
    private void creemenu()//cree les bouton pour le loto
    {


        Button ajout = new Button();
        ajout.setLayoutX(10);
        ajout.setLayoutY(230);
        ajout.setPrefWidth(125);
        ajout.setPrefHeight(25);
        ajout.setText("ajouter un joueur");
        ajout.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        ajout.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                joueur.add(new interfacejoueur(joueur.size()+1));
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
                if(!traitementLoto.getfin())
                {
                    etatPartie.setEtatpartie(traitementLoto.getEtatpartie());
                    compteur.maj(traitementLoto.aleatoire());
                    for(interfacejoueur j : joueur)
                    {
                        j.vide();
                    }
                }
                else etatPartie.fin();
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
                etatPartie.restart();
                for(interfacejoueur j : joueur)
                {
                    j.fermer();
                }
                joueur.clear();
            }
        });


        loto.getChildren().add(ajout);
        loto.getChildren().add(suivant);
        loto.getChildren().add(restart);

    }
    public Group getLoto()
    {
        return loto;
    }
}
