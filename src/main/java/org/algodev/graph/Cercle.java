package org.algodev.graph;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Cercle extends Circle {//redefinition de Circle pour ajouter une variable pour savoir le le jeton du loto est afficher ou non
    protected boolean afficher;

    public Cercle(double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
        this.afficher = false;
    }
    public void setAfficher()
    {
        if(afficher)afficher = false;
        else afficher = true;
    }
    public boolean getAfficher()
    {
        return afficher;
    }
}
