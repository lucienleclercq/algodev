package org.algodev.graph.sudo;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.algodev.graph.Nombre;

import java.util.ArrayList;

public class GrilleSudo {
    private ArrayList<Nombre> nombre;
    private Group g;
    public GrilleSudo(int x, int y,int w ,int r, int e)
    {
        g = new Group();
        int taillex = 0;
        int tailley = 0;
        nombre = new ArrayList<Nombre>();
        Nombre nb;
        Group no = new Group();
        for(int i = 0; i < 9 ; i ++ )//creation de toute les case
        {
            for(int j = 0; j < 9; j ++)
            {

                nb = new Nombre(j+i*10,(x+w*j+e*j)+4*e*((int)(j/3)),(y+w*i+e*i)+4*e*((int)(i/3)),w,r);
                nombre.add(nb);
                no.getChildren().add(nb.getG());
            }
        }
        Rectangle r1 = new Rectangle(w*9 +4*e*4,w*9+4*e*4,Color.web("0x000000", 1));
        r1.setY(y);
        r1.setX(x);
        r1.setArcHeight(r);
        r1.setArcWidth(r);
        DropShadow o = new DropShadow();
        o.setOffsetX(e*10);
        o.setOffsetY(e*10);
        o.setRadius(r);
        r1.setEffect(o);
            /*r1.setStrokeType(StrokeType.OUTSIDE);
            r1.setStrokeWidth(4);
            r1.setStroke(Color.web("0xff0000", 0.7));*/

        g.getChildren().add(r1);
        g.getChildren().add(no);
    }
    public Group getG()
    {
        return g;
    }
}
