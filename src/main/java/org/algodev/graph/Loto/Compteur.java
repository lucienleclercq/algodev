package org.algodev.graph.Loto;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.algodev.graph.Nombre;
import org.algodev.jeux.loto.Loto;

import java.util.ArrayList;

public class Compteur {//afficher un tableaux de toute les valeur possible et afficher les valeur tirer
    private Group g;
    private ArrayList<Nombre> nombre;
    private Afficheur a;
    private Text valider;
    private int x;
    private int y;
    private int w;
    private int r;
    private int e;
    private Loto traitement;
    public Compteur(int x, int y, int w , int r, int e , Loto traitement) {//x et y la position w et r la taille e l'ecare entre les case
        g = new Group();
        valider = new Text();
        g.getChildren().add(valider);

        this.traitement = traitement;
        nombre = new ArrayList<Nombre>();
        this.x = x;
        this.y = y;
        this.w = w;
        this.r = r;
        this.e = e;

        valider.setFill(Color.RED);
        creecompteur();
    }
    private void creecompteur()//cree le tablaux de valeur
    {   Nombre nb;
        Rectangle r1 = new Rectangle(10*w+9*e,9*w+9*e,  Color.grayRgb(50));
        r1.setY(y);
        r1.setX(x+w);
        DropShadow o = new DropShadow();
        o.setOffsetX(e*2);
        o.setOffsetY(e*2);
        o.setRadius(r);
        r1.setEffect(o);
        g.getChildren().add(r1);
        a = new Afficheur(x+w*4+w/2+e*2,y-(w*3+w/2+e*2),w*3+e*2,r);

        g.getChildren().add(a.getG());
        for(int i = 0; i < 9 ; i ++ )
        {
            for(int j = 1; j < 11; j ++)
            {
                nb = new Nombre(j+i*10,(x+w*j+e*j),(y+w*i+e*i),w,r);
                nombre.add(nb);
                g.getChildren().add(nb.getG());
            }
        }

    }
    public void restart()
    {
        for(Nombre n : nombre)
        {
            n.setCouleur(Color.web("0xC4C4C4", 1));
        }
        vide();
    }
    public void maj(int nb)//permet de changer l'état d'un nombre
    {
        if(nb>0)nombre.get(nb-1).setCouleur(Color.web("0xFF2020", 1));
        a.setnombre(nb);
    }
    public boolean gagner(int nb)//affiche gagner
    {
        if(traitement.VerifLigne(nb))
        {
            valider.setFont(new Font(100));
            valider.setX(x+70);
            valider.setY(y+9*w+9*e+100);
            valider.setText("!!GAGNER!!");
            return true;
        }
        else {
            valider.setX(x+225);
            valider.setY(y+9*w+9*e+250);
            valider.setFont(new Font(300));
            valider.setText("X");
            return false;
        }

    }
    public void vide()
    {
        valider.setText("");
    }
    public Group getG()
    {
        return g;
    }
}
