package org.algodev.graph.Loto;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Afficheur {
    private int n ;
    private Group g;
    private Text t ;
    private javafx.scene.shape.Rectangle r;
    public Afficheur(int x, int y, int w,int r) {//permet d'afficher la valeur tirer
        g = new Group();
        this.n = 0;
        this.t = new Text();
        t.setY(y+r*10);
        t.setX(x+r*6);
        t.setFont(new Font(60));
        t.setText(Integer.toString(n));
        this.r = new Rectangle(w,w, Color.web("0xC4C4C4", 1));
        this.r.setY(y);
        this.r.setX(x);
        this.r.setArcWidth(r);
        this.r.setArcHeight(r);
        this.r.setStrokeType(StrokeType.INSIDE);
        this.r.setStrokeWidth(4);
        this.r.setStroke(Color.web("0x303030", 0.7));
        DropShadow o = new DropShadow();
        o.setOffsetX(r);
        o.setOffsetY(r);
        o.setRadius(r);
        this.r.setEffect(o);
        g.getChildren().add(this.r);
        g.getChildren().add(t);

    }
    public void setnombre(int n)//change le nombre a afficher
    {
        this.t.setText(Integer.toString(n));
    }
    public Group getG()
    {
        return  g;

    }
}
