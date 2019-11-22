package org.algodev.graph.poker;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class InterfaceCarte {
    public Group g;
    Rectangle carte ;
    private int x;
    private int y;
    private Image image;
    private Image imageVisible;
    private boolean visible;
    public InterfaceCarte(int x, int y , String lien)
    {
        g = new Group();
        visible = false;
        carte =  new Rectangle(100,152);
        image = new Image(String.valueOf(this.getClass().getResource("/cartes-poker/red_back.jpg")));
        imageVisible =new Image(String.valueOf(this.getClass().getResource(lien)));
        carte.setFill(new ImagePattern(image));
        carte.setY(y);
        carte.setX(x);
        g.getChildren().add(carte);
        this.x = x;
        this.y = y ;

    }
    public Group getG()
    {
        return  g;

    }
    public void retourner(boolean v) {
        if(!visible && v){
            carte.setFill(new ImagePattern(imageVisible));
            visible = true;
        }
        else if (visible && !v){
            carte.setFill(new ImagePattern(image));
            visible = false;
        }

    }
}
