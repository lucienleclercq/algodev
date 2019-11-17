package org.algodev.graph;
// cette classe gére toute l'interface

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import org.algodev.graph.Loto.LotoInterface;
import org.algodev.graph.batailleNavale.InterfaceBatailleNaval;
import org.algodev.graph.sudo.InterfaceSudo;

import javafx.scene.control.Button;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Fenetre {
    private Stage s1;
    private Group root;
    private Group loto;
    private Group sudo;
    private Group bataille;
    private Scene scene;
    private BackgroundImage Fond;
    private int tailleh;
    private int taillew;
    private int jeux; // 0 rien 1 loto 2 bataille 3 sudo
    public Fenetre(Stage s1) throws IOException {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();// recuperation de la taille de l'ecran de l'utilisateur
        tailleh = (int)dimension.getHeight();
        taillew  = (int)dimension.getWidth();
        root = new Group();//creation du groupe qui va garder tous les objet a afficher
        scene = new Scene(root,taillew, tailleh,  Color.grayRgb(50)); //instanciation de la fenetre
        loto = new Group(new LotoInterface(tailleh,taillew).getLoto());//groupe qui garder les objet du loto
        sudo = new Group(new InterfaceSudo(tailleh, taillew, scene).getSudo());//groupe qui garder les objet du sudoku
        bataille = new Group(new InterfaceBatailleNaval(tailleh,taillew).getG());
        this.s1 = s1;//on garde le stage dans une variable de la classe
        this.s1.setFullScreen(true);//on met le plein ecran
        this.s1.setScene(scene);
        this.s1.show();//on montre la fenetre
        jeux = 0;//represente le jeut en cour
        creeMenu();
    }
    private void creeMenu() throws FileNotFoundException {

        //FileInputStream inputstream = new FileInputStream(".\\src\\graph\\IMG_0114.jpg");
        //Image image = new Image(String.valueOf(this.getClass().getResource("/IMG_0114.jpg")));//recuperation de l'image
        //ImageView im = new ImageView(image);//enregistrement de l'image
        //im.setX(0);//positionnement de l'image
        //im.setY(0);
        //im.setPreserveRatio(false);
        //im.setFitHeight(tailleh);//definition de la taille de l'image
        //im.setFitWidth(taillew);
        //root.getChildren().add(im);//on affiche l'image en la mettant dans le groupe
        Rectangle barreMenu = new Rectangle(taillew*0.076,tailleh);//on cree un rectangle pour faire le fond de menu a gauche
        DropShadow o = new DropShadow();//creation d'une ombre pour barreMenu
        o.setOffsetX(taillew*0.005);//declaration du decalage de l'ombre
        o.setOffsetY(taillew*0.005);
        barreMenu.setEffect(o);// on associe l'ombre a barreMenu
        barreMenu.setFill(Color.web("#959595", 0.6));//on donne une couleur au rectangle
        root.getChildren().add(barreMenu);// on affiche le rectangle en le metant dans root
        Button bloto = new Button();//creation d'un bouton
        bloto.setLayoutX(10);//positionnement du bouton
        bloto.setLayoutY(10);
        bloto.setPrefWidth(125);//declaration de la taille du bouton
        bloto.setPrefHeight(25);
        bloto.setText("loto");//declaration du texte pour le bouton
        bloto.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");//declaration de la couleur du bouton
        bloto.setOnAction(new EventHandler<ActionEvent>() {// declaration d'un event pour interagir avec le bouton

            public void handle(ActionEvent event) {
                System.out.println("loto");
                if(jeux != 1)
                {
                    if (jeux == 3)
                    {
                        root.getChildren().remove(sudo);
                    }
                    else if(jeux == 2)root.getChildren().remove(bataille);
                    root.getChildren().add(loto);
                    jeux = 1;
                }
            }
        });
        Button bbataile = new Button();
        bbataile.setLayoutX(10);
        bbataile.setLayoutY(40);
        bbataile.setPrefWidth(125);
        bbataile.setPrefHeight(25);
        bbataile.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        bbataile.setText("bataille navale");
        bbataile.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println("bataille navale");
                if(jeux != 2)
                {
                    if (jeux == 1)
                    {
                        root.getChildren().remove(loto);
                    }
                    else if(jeux == 3)root.getChildren().remove(sudo);

                    root.getChildren().add(bataille);
                    jeux = 2;
                }

            }
        });
        Button bsudoku = new Button();
        bsudoku.setLayoutX(10);
        bsudoku.setLayoutY(70);
        bsudoku.setPrefWidth(125);
        bsudoku.setPrefHeight(25);
        bsudoku.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        bsudoku.setText("sudoku");
        bsudoku.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(jeux != 3)
                {
                    if (jeux == 1)
                    {
                        root.getChildren().remove(loto);
                    }
                    else if(jeux == 2)root.getChildren().remove(bataille);

                    root.getChildren().add(sudo);
                    jeux = 3;
                }

            }
        });
        Button bpoker = new Button();
        bpoker.setLayoutX(10);
        bpoker.setLayoutY(100);
        bpoker.setPrefWidth(125);
        bpoker.setPrefHeight(25);
        bpoker.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        bpoker.setText("poker");
        bpoker.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println("poker");
            }
        });
        root.getChildren().add(bpoker);
        root.getChildren().add(bsudoku);
        root.getChildren().add(bbataile);
        root.getChildren().add(bloto);


    }
}
