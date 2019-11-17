package org.algodev.graph.batailleNavale;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class InterfaceBatailleNaval {
    protected int h;
    protected int w;
    protected Group g;
    public InterfaceBatailleNaval(int h, int w) throws FileNotFoundException {
        this.h = h;
        this.w = w;
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
                }
                else
                {
                    r.setX(w * 0.58 + w * 0.03 *(j-10));
                    r.setY(h * 0.20 + w * 0.03 *i);
                }

                r.setFill(Color.GRAY);
                r.setStrokeWidth(1);
                r.setStroke(Color.BLACK);
                r.setStrokeType( StrokeType.INSIDE);
                g.getChildren().add(r);
            }
        }
    }
    public Group getG()
    {
        return g;
    }
}
