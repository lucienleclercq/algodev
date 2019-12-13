package org.algodev.graph.Loto;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.algodev.jeux.loto.Loto;

public class interfacejoueur {
    private Scene s;
    private Stage s1;
    private Group carton;
    private interfaceCartonLoto c;
    private int tailleh = 1080;
    private int taillew = 1920;
    private Loto traitementLoto;
    private Text valider;
    public interfacejoueur(int nb)
    {
        carton = new Group();
        traitementLoto = new Loto();
        s = new Scene(carton,1920,1080,  Color.grayRgb(50));
        s1 = new Stage();
        s1.setTitle("joueur "+nb);
        Image image = new Image(String.valueOf(this.getClass().getResource("/loto.jpg")));//recuperation de l'image
        ImageView im = new ImageView(image);//enregistrement de l'image
        im.setX(0);//positionnement de l'image
        im.setY(0);
        c = new interfaceCartonLoto(traitementLoto);
        im.setPreserveRatio(false);
        im.setFitHeight(tailleh+20);//definition de la taille de l'image
        im.setFitWidth(taillew+40);
        im.setTranslateX(-40);
        im.setTranslateY(-20);
        carton.getChildren().add(im);
        this.s1.setWidth(1000);
        this.s1.setHeight(900);
        this.s1.setScene(s);
        this.s1.show();
        valider = new Text();
        valider.setFill(Color.RED);
        carton.getChildren().add(valider);
        Button verifier = new Button();
        Button ajoutgrille = new Button();
        ajoutgrille.setLayoutX(50);
        ajoutgrille.setLayoutY(50);
        ajoutgrille.setPrefWidth(125);
        ajoutgrille.setPrefHeight(25);
        ajoutgrille.setText("ajout grille");
        ajoutgrille.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        ajoutgrille.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                traitementLoto.CreerCarton();
                c.creeCarton();
                carton.getChildren().remove(ajoutgrille);
                carton.getChildren().add(ajoutgrille);
                carton.getChildren().remove(verifier);
                carton.getChildren().add(verifier);
            }
        });

        verifier.setLayoutX(180);
        verifier.setLayoutY(50);
        verifier.setPrefWidth(125);
        verifier.setPrefHeight(25);
        verifier.setText("verifier");
        verifier.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        verifier.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                    if(traitementLoto.VerifLigne(traitementLoto.getEtatpartie()))
                    {
                        valider.setFont(new Font(100));
                        valider.setX(300);
                        valider.setY(100);
                        valider.setText("!!GAGNER!!");
                        traitementLoto.ajoutEtatpartie();
                    }
                    else {
                        valider.setX(300);
                        valider.setY(150);
                        valider.setFont(new Font(200));
                        valider.setText("X");
                    }


            }
        });
        carton.getChildren().add(verifier);
        carton.getChildren().addAll(c.getCarton());
        carton.getChildren().add(ajoutgrille);
    }
    public void fermer()
    {
        s1.close();
    }
    public void vide()
    {
        valider.setText("");
    }

}
