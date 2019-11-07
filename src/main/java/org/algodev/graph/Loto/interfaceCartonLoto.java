package org.algodev.graph.Loto;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.algodev.jeux.loto.CaseLoto;
import org.algodev.jeux.loto.Loto;

import java.util.ArrayList;

public class interfaceCartonLoto {// cree un carton
    private double h = 75;
    private double w = 75;
    private double e = 10;
    private double x = 550;
    private double y = 200;
    private double r = 30;
    private int k;
    private Loto traitement;
    private Rectangle fondglob;
    ArrayList<ArrayList<interfaceCaseLoto>> grille;
    Group carton;
    public interfaceCartonLoto(Loto t) {
        this.traitement = t;
        this.h =75;//taille du carton en hauteur
        this.w = 75;//en largeur
        this.e = 10;//ecart entre les case
        this.x = 400;//position en x;
        this.y = 200;//position en y
        this.r = 30;//arondisement des coin
        this.k = 0;
        grille = new ArrayList<ArrayList<interfaceCaseLoto>>();
        carton = new Group();
        fondglob = new Rectangle(x-(e*2),y-(e*2)+((h * 3+ e * 6)*k)+e,w*9+e*12,h*3+e*6);
        fondglob.setFill(Color.web("0x676767", 0.0));
        carton.getChildren().add(fondglob);
        carton.setOnScroll(new EventHandler<ScrollEvent>() {//permet de scroller quand les carton depasse l'écran
            @Override
            public void handle(ScrollEvent event) {
                if(k> 3)
                {
                    if(carton.getTranslateY() + event.getDeltaY() > 0)carton.setTranslateY(0);
                    else if((carton.getTranslateY() + event.getDeltaY()) < -((h*3+e*6)*(k-3)+e*k)) carton.setTranslateY(-((h*3+e*6)*(k-3)+e*k));
                    else carton.setTranslateY(carton.getTranslateY() + event.getDeltaY());

                }
            }
        });

        creeCarton();
    }
    public void creeCarton()
    {
        interfaceCaseLoto c;
        Rectangle fond;
        fondglob.setHeight(fondglob.getHeight()+h*3+e*8);

        fond = new Rectangle(x-(e*2),y-(e*2)+((h * 3+ e * 6)*k)+e*k,w*9+e*12,h*3+e*6);
        fond.setFill(Color.web("0x959595", 0.6));
        fond.setArcHeight(r);
        fond.setArcWidth(r);
        DropShadow o = new DropShadow();
        o.setOffsetX(e);
        o.setOffsetY(e);
        o.setRadius(r);
        fond.setEffect(o);
        carton.getChildren().add(fond);

        for(int i = 0; i < 3 ; i ++) {
            grille.add(new ArrayList<interfaceCaseLoto>());

            for (int z = 0; z < 9; z++)
            {
                if(!traitement.getGrille().get(i+(k*3)).get(z).getValeur().equals("vide"))
                {
                    c =new interfaceCaseLoto(x-(e*2),y-(e*2)+((h * 3+ e * 6)*k)+e*k,w,h,r,e,i,z,k,(CaseLoto)traitement.getGrille().get(i+(k*3)).get(z));
                    grille.get(i).add(c);
                    carton.getChildren().addAll(c.getG());
                }
            }


        }
        this.k++;
    }
    public void restart()
    {
        for(ArrayList<interfaceCaseLoto> liste : grille )
        {
            for(interfaceCaseLoto i : liste)
            {
                i.restart();
            }
        }
    }
    public Group getCarton()
    {
        return carton;
    }
}
