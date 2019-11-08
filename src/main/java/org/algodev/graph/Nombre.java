package org.algodev.graph;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Nombre {//cree un nombre dans un rectangle encadré
    private int n ;
    private Group g;
    private Text t ;
    private Rectangle r;
    public Nombre(int n,int x, int y, int w,int r) {//n etant la valeur afficher dans le cadre
        g = new Group();
        this.n = n;
        this.t = new Text();//creation d'un texte pour afficher le nombre
        t.setY(y+r*2);//positionnement du texte
        t.setX(x+r);
        t.setFont(new Font(20));//declaration de la taille du texte
        t.setText(new Integer(n).toString());//declaration du texte a afficher
        this.r = new Rectangle(w,w,Color.web("0xC4C4C4", 1));
        this.r.setY(y);
        this.r.setX(x);
        this.r.setArcWidth(r);
        this.r.setArcHeight(r);
        this.r.setStrokeType(StrokeType.INSIDE);
        this.r.setStrokeWidth(4);
        this.r.setStroke(Color.web("0x303030", 0.7));
        g.getChildren().add(this.r);
        g.getChildren().add(t);

    }
    public void setCouleur(Paint g)
    {
        r.setFill(g);
    }
    public int getN()
    {
        return  n ;
    }
    public Group getG()
    {
        return g;
    }

    public Text getText() {
        return t;
    }

    public Rectangle getRectangle() {
        return r;
    }

    public void setText(String nombre) {
        if(nombre.equals("0")) {
            t.setText("");
        }
        else t.setText(nombre);
    }
}
