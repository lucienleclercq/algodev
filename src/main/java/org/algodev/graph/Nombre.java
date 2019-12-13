package org.algodev.graph;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Nombre {//cree un nombre dans un rectangle encadré
    private int n ;
    private StackPane stackPane;
    private Text t ;
    private Rectangle r;
    public Nombre(int n,int x, int y, int w,int r,int tailleTexte) {//n etant la valeur afficher dans le cadre
        stackPane = new StackPane();
        this.n = n;
        this.t = new Text();//creation d'un texte pour afficher le nombre
        t.setFont(new Font(tailleTexte));//declaration de la taille du texte
        t.setText(Integer.toString(n));//declaration du texte a afficher
        this.r = new Rectangle(w,w,Color.web("0xC4C4C4", 1));
        this.r.setY(y);
        this.r.setX(x);
        this.stackPane.setTranslateX(x);
        this.stackPane.setTranslateY(y);
        this.r.setArcWidth(r);
        this.r.setArcHeight(r);
        this.r.setStrokeType(StrokeType.INSIDE);
        this.r.setStrokeWidth(4);
        this.r.setStroke(Color.web("0x303030", 0.7));
        stackPane.getChildren().add(this.r);
        stackPane.getChildren().add(t);

    }
    public void setCouleur(Paint g)
    {
        r.setFill(g);
    }
    public int getN()
    {
        return  n ;
    }
    public StackPane getStackPane()
    {
        return stackPane;
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
