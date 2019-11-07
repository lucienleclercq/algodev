package org.algodev.graph.Loto;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import org.algodev.graph.Cercle;
import org.algodev.jeux.loto.CaseLoto;

public class interfaceCaseLoto {
    private double x;//position en x
    private double y;//en y
    private double w;//largeur
    private double h;//hauteur
    private double r;//arondisement des coin
    private double e;//ecare entre les case
    private Group g;
    private Cercle c;
    public interfaceCaseLoto(double x, double y, double w, double h,double r,double e,int i , int j,int k, CaseLoto ca ) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.r = r;
        this.e = e;
        String nb = ca.getValeur();
        g = new Group();
        Rectangle c;
        Cercle c1;
        Text t;
        c = new Rectangle(x+e*(j+2)+w*j, y+(i*h)+e*(i+2), w, h);
        c.setFill(Color.web("0xC4C4C4", 1));
        c.setArcHeight(r);
        c.setArcWidth(r);
        c.setStrokeType(StrokeType.OUTSIDE);
        c.setStrokeWidth(4);
        c.setStroke(Color.web("0x505050", 0.7));
        g.getChildren().add(c);
        t = new Text();
        t.setTextAlignment(TextAlignment.CENTER);
        t.setFont(new Font(30));
        t.setX(x+30+e*(j+2)+w*j);
        t.setY(y+40+e*(i+2)+h*i);
        t.setText(nb);
        g.getChildren().add(t);

        c1= new Cercle(x+e*(j+2)+w*j+w/2 , y+(i*h)+e*(i+2)+h/2,(x+e*(j+2)+w*j+w/2)-(x+e*(j+2)+w*j),Color.web("0xFF0000", 0.0));
        c1.setStrokeType(StrokeType.INSIDE);
        c1.setStrokeWidth(2);
        DropShadow o = new DropShadow();
        o.setOffsetX(e);
        o.setOffsetY(e);
        o.setRadius(r);
        c1.setEffect(o);
        this.c = c1;
        c1.setOnMouseClicked(new EventHandler<MouseEvent>() {//affiche le jeton losqu'on clique sur une case
            @Override
            public void handle(MouseEvent event) {
                if(c1.getAfficher())
                {
                    c1.setFill(Color.web("0xFF0000", 0));
                    c1.setStroke(Color.web("0xFF0000", 0));
                    c1.setAfficher();
                    ca.setJetons(false);
                }
                else
                {
                    c1.setFill(Color.web("0xFF2020", 0.5));
                    c1.setStroke(Color.web("0xFF2020", 1));
                    c1.setAfficher();
                    ca.setJetons(true);
                }
            }
        });
        g.getChildren().add(c1);




    }
    public Group getG()
    {
        return g;
    }
    public void restart()
    {
        if(c.getAfficher())
        {
            c.setFill(Color.web("0xFF0000", 0));
            c.setStroke(Color.web("0xFF0000", 0));
        }
    }
}
