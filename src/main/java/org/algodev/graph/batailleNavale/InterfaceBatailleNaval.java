package org.algodev.graph.batailleNavale;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class InterfaceBatailleNaval {
    protected int h;
    protected int w;
    protected Group g;
    private boolean etatplacement;
    public InterfaceBatailleNaval(int h, int w) throws FileNotFoundException {
        this.h = h;
        this.w = w;
        etatplacement = false;
        g= new Group();
        creeInterface();
    }
    private void creeInterface() throws FileNotFoundException {
        Image image = new Image(String.valueOf(this.getClass().getResource("/eaux.jpg")));//recuperation de l'image
        Rectangle r = new Rectangle( w*0.78,h*0.62);
        r.setFill(new ImagePattern(image));
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setX(w * 0.14);//positionnement de l'image
        r.setY(h * 0.15);
        g.getChildren().add(r);
        r = new Rectangle(w*0.361, h*0.599);
        r.setLayoutX(w * 0.15);
        r.setLayoutY(h * 0.16);
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(Color.web("0x9693FF", 0.6));
        g.getChildren().add(r);
        r = new Rectangle(w*0.361, h*0.599);
        r.setLayoutX(w * 0.55);
        r.setLayoutY(h * 0.16);
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(Color.web("0x9693FF", 0.6));
        g.getChildren().add(r);

        for (int i = 0 ; i < 10; i ++)
        {
            for(int j = 0 ; j < 20 ; j ++)
            {
                r = new Rectangle(w * 0.03,w*0.03);
                if(j < 10)
                {
                    r.setX(w * 0.18 + w * 0.03 *j);
                    r.setY(h * 0.20 + w * 0.03 *i);
                    Rectangle finalR = r;
                    r.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(!etatplacement)finalR.setFill(Color.RED);
                        }
                    });
                    r.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(!etatplacement)finalR.setFill(Color.GRAY);
                        }
                    });
                }
                else
                {
                    r.setX(w * 0.58 + w * 0.03 *(j-10));
                    r.setY(h * 0.20 + w * 0.03 *i);
                    Rectangle finalR = r;
                    r.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(etatplacement)finalR.setFill(Color.RED);
                        }
                    });
                    r.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(etatplacement)finalR.setFill(Color.GRAY);
                        }
                    });
                }

                r.setFill(Color.GRAY);
                r.setStrokeWidth(1);
                r.setStroke(Color.BLACK);
                r.setStrokeType( StrokeType.INSIDE);
                g.getChildren().add(r);
            }
        }
        for(int i = 0; i < 20 ; i ++)
        {
            Text t = new Text();
            Text t2 = new Text();
            t.setFont(new Font(17));
            t.setFill(Color.BLACK);
            t.setY(h * 0.13 + w * 0.03);
            t2.setFont(new Font(17));
            t2.setFill(Color.BLACK);

            if(i < 10)
            {
                t.setText(String.valueOf(i+1));
                t.setX(w * 0.19 + w * 0.03 *i);
                t2.setText(String.valueOf((char)(i+65)));
                t2.setY(h * 0.22 + w * 0.03 * i);
                t2.setX(w * 0.13 + w * 0.03);
            }
            else
            {
                t.setText(String.valueOf(i-9));
                t.setX(w * 0.59 + w * 0.03 *(i-10));
                t2.setText(String.valueOf((char)(i+55)));
                t2.setX(w * 0.53 + w * 0.03);
                t2.setY(h * 0.22 + w * 0.03 * (i-10));
            }

            g.getChildren().add(t);
            g.getChildren().add(t2);

        }
    }
    public Group getG()
    {
        return g;
    }
}
