package org.algodev.graph.batailleNavale;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.algodev.jeux.batailleNavale.BataileNavale;
import org.algodev.jeux.batailleNavale.Navale;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class InterfaceBatailleNaval {
    protected int h;
    protected int w;
    private Rectangle rb;
    private static int k ;
    protected Group g;
    private Group glob;
    private boolean etatplacement;
    private String lien[];
    private int taillebateaux[] = {5, 4, 3, 3, 2};
    private static boolean afficher = false;
    private  int  direction;
    private double x;
    private double y ;
    private boolean findepartie;
    private Navale traitementj1;
    private Navale traitementj2;
    private boolean tirer[][];

    public InterfaceBatailleNaval(int h, int w) throws FileNotFoundException {
        this.h = h;
        this.w = w;
        glob = new Group();
        direction = 0;
        tirer = new boolean[10][10];
        findepartie = false;
        k = 0;
        etatplacement = false;
        g= new Group();
        creeInterface();
        this.traitementj1 = new Navale();
        initIa();
        lien = new String[10];
        lien[1]= "/BatailleNavale/cutter.png";
        lien[0]= "/BatailleNavale/orca.png";
        lien[2]= "/BatailleNavale/cliper.png";
        lien[3]= "/BatailleNavale/cliper.png";
        lien[4]= "/BatailleNavale/dolfin.png";
        lien[5]= "/BatailleNavale/corvette.png";
        lien[6]= "/BatailleNavale/corvette.png";
        lien[7]= "/BatailleNavale/corvette.png";
        lien[8]= "/BatailleNavale/corvette.png";
        lien[9]= "/BatailleNavale/corvette.png";
    }
    private String orientation(int direction)
    {
        switch (direction)
        {
            case 0:
                return "Bas";
            case 90 :
                return "Droite";
            case 180 :
                return "Haut";
            case 270 :
                return "Gauche";
        }
        return "";
    }
    private void initIa()
    {
        traitementj2 = new Navale();
        for(int i = 0; i < 5 ; i++)
        {
            boolean trouver = false;
            while (!trouver)
            {

                int x = (int)(Math.random()*(10-1));
                int y = (int)(Math.random()*(10-1));
                int o = (int)(Math.random()*(4-1)) * 90;
                if(traitementj2.verifPlacement(x+1,y+1,taillebateaux[i],orientation(o))){
                    trouver = true;
                    traitementj2.placerBateau(x+1,y+1,taillebateaux[i],orientation(o),i);
                }
            }
        }
    }
    private void restart()
    {
        glob.getChildren().remove(g);
        traitementj1 = new Navale();
        findepartie = false;
        tirer = new boolean[10][10];
        etatplacement = false;
        direction = 0;
        g = new Group();
        k = 0;
        initIa();
        gestion();
    }
    private void creeInterface() throws FileNotFoundException {
        Button recommencer = new Button();
        recommencer.setLayoutX(10);
        recommencer.setLayoutY(200);
        recommencer.setPrefWidth(125);
        recommencer.setPrefHeight(25);
        recommencer.setText("recommencer");
        recommencer.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        recommencer.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                restart();
            }
        });
        glob.getChildren().add(recommencer);

        Image image = new Image(String.valueOf(this.getClass().getResource("/eaux.jpg")));//recuperation de l'image
        Rectangle r = new Rectangle(w * 0.78, h * 0.62);
        r.setFill(new ImagePattern(image));
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setX(w * 0.14);//positionnement de l'image
        r.setY(h * 0.15);
        glob.getChildren().add(r);
        r = new Rectangle(w * 0.361, h * 0.599);
        r.setLayoutX(w * 0.15);
        r.setLayoutY(h * 0.16);
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(Color.web("0x9693FF", 0.6));
        glob.getChildren().add(r);
        r = new Rectangle(w * 0.361, h * 0.599);
        r.setLayoutX(w * 0.55);
        r.setLayoutY(h * 0.16);
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(Color.web("0x9693FF", 0.6));
        glob.getChildren().add(r);
        gestion();
    }
    private void gestion()
    {
        Rectangle r;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                int finalJ = j;
                int finalI = i;
                r = new Rectangle(w * 0.03, w * 0.03);
                if (j < 10) {
                    r.setX(w * 0.18 + w * 0.03 * j);
                    r.setY(h * 0.20 + w * 0.03 * i);
                    Rectangle finalR = r;


                    final boolean[] valider = {false};
                    r.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (!etatplacement && !afficher) {
                                afficher = true;
                                finalR.setFill(Color.web("#FF2C2C", 0.5));
                                Image image = new Image(String.valueOf(this.getClass().getResource(lien[k])));
                                direction = 0;
                                rb = new Rectangle(w * 0.032 + w * 0.002, w * 0.03 * taillebateaux[k] + w * 0.002);
                                rb.setFill(new ImagePattern(image));
                                rb.setX(w * 0.178 + w * 0.03 * finalJ);//positionnement de l'image
                                rb.setY(h * 0.198 + w * 0.03 * finalI);
                                y = rb.getY();
                                x = rb.getX();
                                rb.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        if (traitementj1.verifPlacement(finalI + 1, finalJ + 1, taillebateaux[k], orientation(direction))) {
                                            traitementj1.placerBateau(finalI + 1, finalJ + 1, taillebateaux[k], orientation(direction), k);
                                            valider[0] = true;
                                            k++;
                                            if (k >= 5) etatplacement = true;
                                        }
                                    }
                                });
                                rb.setOnScroll(new EventHandler<ScrollEvent>() {
                                    @Override
                                    public void handle(ScrollEvent scrollEvent) {
                                        direction += 90;
                                        rb.setRotate(direction);

                                        if (direction >= 360) direction = 0;
                                        switch (direction) {
                                            case 0:
                                                rb.setY(y);
                                                rb.setX(x);
                                                break;
                                            case 90:

                                                rb.setY(y - (rb.getHeight() / 2) + rb.getWidth() / 2);
                                                rb.setX(x + rb.getHeight() / 2 - rb.getWidth() / 2);

                                                break;
                                            case 180:
                                                rb.setY(y - (rb.getHeight()) + rb.getWidth());
                                                rb.setX(x);
                                                break;
                                            case 270:
                                                rb.setY(y - (rb.getHeight() / 2) + rb.getWidth() / 2);
                                                rb.setX(x - rb.getHeight() / 2 + rb.getWidth() / 2);
                                                break;
                                        }
                                    }

                                });
                                rb.setOnMouseExited(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        if (!valider[0]) {
                                            g.getChildren().remove(rb);
                                        }
                                        afficher = false;
                                        finalR.setFill(Color.web("#8D8D8D", 0.5));
                                    }
                                });
                                g.getChildren().add(rb);

                            }

                        }
                    });


                } else {
                    r.setX(w * 0.58 + w * 0.03 * (j - 10));
                    r.setY(h * 0.20 + w * 0.03 * i);
                    Rectangle finalR = r;
                    r.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (etatplacement) finalR.setFill(Color.web("#FF2C2C", 0.7));
                            finalR.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if (!findepartie) {
                                        afficherAttaque(finalI, finalJ, true);

                                        if (traitementj2.findePartie()) {
                                            Image image = new Image(String.valueOf(this.getClass().getResource("/BatailleNavale/gagner.png")));
                                            Rectangle fin = new Rectangle(w * 0.1, w * 0.1);
                                            fin.setFill(new ImagePattern(image));
                                            fin.setX((int) (w * 0.15));
                                            fin.setY((int) (w * 0.1));
                                            g.getChildren().add(fin);
                                            findepartie = true;
                                        }

                                    }
                                    if (!findepartie) {
                                        int tirerx = (int) (Math.random() * (10));
                                        int tirery = (int) (Math.random() * (10));
                                        ;
                                        while (tirer[tirerx][tirery]) {
                                            tirerx = (int) (Math.random() * (10));
                                            tirery = (int) (Math.random() * (10));
                                        }
                                        tirer[tirerx][tirery] = true;
                                        afficherAttaque(tirery, tirerx, false);
                                        if (traitementj1.findePartie()) {
                                            Image image = new Image(String.valueOf(this.getClass().getResource("/BatailleNavale/gagner.png")));
                                            Rectangle fin = new Rectangle(w * 0.1, w * 0.1);
                                            fin.setFill(new ImagePattern(image));
                                            fin.setX((int) (w * 0.55));
                                            fin.setY((int) (w * 0.1));
                                            g.getChildren().add(fin);
                                            findepartie = true;
                                        }
                                    }

                                }
                            });
                        }
                    });
                    r.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (etatplacement) finalR.setFill(Color.web("#8D8D8D", 0.5));
                        }
                    });
                }

                r.setFill(Color.web("#8D8D8D", 0.5));
                r.setStrokeWidth(1);
                r.setStroke(Color.BLACK);
                r.setStrokeType(StrokeType.INSIDE);
                g.getChildren().add(r);
            }
        }
        for (int i = 0; i < 20; i++) {
            Text t = new Text();
            Text t2 = new Text();
            t.setFont(new Font(17));
            t.setFill(Color.BLACK);
            t.setY(h * 0.13 + w * 0.03);
            t2.setFont(new Font(17));
            t2.setFill(Color.BLACK);

            if (i < 10) {
                t.setText(String.valueOf(i + 1));
                t.setX(w * 0.19 + w * 0.03 * i);
                t2.setText(String.valueOf((char) (i + 65)));
                t2.setY(h * 0.22 + w * 0.03 * i);
                t2.setX(w * 0.13 + w * 0.03);
            } else {
                t.setText(String.valueOf(i - 9));
                t.setX(w * 0.59 + w * 0.03 * (i - 10));
                t2.setText(String.valueOf((char) (i + 55)));
                t2.setX(w * 0.53 + w * 0.03);
                t2.setY(h * 0.22 + w * 0.03 * (i - 10));
            }

            g.getChildren().add(t);
            g.getChildren().add(t2);

        }

        glob.getChildren().add(g);
    }
    private void afficherAttaque(int x, int y,boolean p2)
    {
        Navale traitement;

        Rectangle re =  new Rectangle( w * 0.03,w*0.03);
        Image image;
        int y2 = y;
        if(p2){
            y2-=10;
            re.setX(w * 0.58 + w * 0.03 * (y-10));
            traitement = traitementj2;
        }
        else
        {
            re.setX(w * 0.18 + w * 0.03 *y);
            traitement = traitementj1;
        }

        re.setY(h * 0.20 + w * 0.03 * x);

        if(traitement.attack(x+1,y2+1))
        {
            image= new Image(String.valueOf("/BatailleNavale/explosion.png"));
        }
        else
        {
            image = new Image(String.valueOf("/BatailleNavale/explosion2.png"));
        }
        re.setFill(new ImagePattern(image));
        g.getChildren().add(re);
    }
    public Group getG()
    {
        return glob;
    }
}
