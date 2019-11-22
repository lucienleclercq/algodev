package org.algodev.jeux.poker;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.algodev.graph.poker.InterfaceCarte;

import java.util.ArrayList;

public class InterfaceMain {
    private Group g;
    private ArrayList<InterfaceCarte> listecarte;
    private Text t;
    private int numjoueur;
    private int w;
    private int h;
    private boolean coucher;
    private Table traitement;
    public InterfaceMain(int numJoueur,int h, int w,Table traitement)
    {
        coucher = false;
        this.numjoueur = numJoueur;
        this.w = w;
        this.h = h;
        this.traitement = traitement;
        this.numjoueur = numjoueur;
            g = new Group();
            listecarte = new ArrayList<>();
            if(numJoueur == 0)
            {
                listecarte.add(new InterfaceCarte(0,0,"/cartes-poker/"+traitement.getTapis().get(0).getCarte()));
                listecarte.add(new InterfaceCarte(100,0,"/cartes-poker/"+traitement.getTapis().get(1).getCarte()));
            }
            else
            {
                listecarte.add(new InterfaceCarte(0,0,"/cartes-poker/"+traitement.getJoueurs().get(numJoueur-1).getMain().get(0).getCarte()));
                listecarte.add(new InterfaceCarte(100,0,"/cartes-poker/"+traitement.getJoueurs().get(numJoueur-1).getMain().get(1).getCarte()));
            }
            g.getChildren().add(listecarte.get(0).getG());
            g.getChildren().add(listecarte.get(1).getG());
        switch (numJoueur)
        {
            case 0 :
                listecarte.add(new InterfaceCarte(200,0,"/cartes-poker/"+traitement.getTapis().get(2).getCarte()));
                g.getChildren().add(listecarte.get(2).getG());
                g.setTranslateX(w*0.45);
                g.setTranslateY(h*0.4);
                break;
            case 1 :
                g.setTranslateX(w*0.5);
                g.setTranslateY(h*0.8);
                break;
            case 2:
                g.setTranslateX(w*0.5);
                g.setTranslateY(h*0.05);
                break;
            case 3:
                g.setTranslateX(w*0.1);
                g.setTranslateY(h*0.4);
                g.setRotate(90);
                break;
            case 4:
                g.setTranslateX(w*0.85);
                g.setTranslateY(h*0.4);
                g.setRotate(270);
                break;
        }
        if(numJoueur> 0){
            t = new Text("joueur "+numJoueur+"   "+traitement.getJoueurs().get(numJoueur-1).getSolde()+" credit ");
            t.setFont(new Font(20));
            t.setY(170);
            t.setX(30);
            t.setFill(Color.WHITE);
            g.getChildren().add(t);
        }
    }
    public void ajoutCarte(String lien)
    {
        listecarte.add(new InterfaceCarte(listecarte.size()*100,0,lien));
        g.getChildren().add(listecarte.get(listecarte.size()-1).getG());
        g.setTranslateX(g.getTranslateX()-(0.05*w)/2);
    }
    public Group getG()
    {return g;}
    public void retourner(int nbcarte,boolean visible)
    {
        for(int i = 0 ; i < nbcarte ; i ++)listecarte.get(i).retourner(visible);
    }
    public void retournerUn(int idcarte,boolean visible)
    {
        listecarte.get(idcarte).retourner(visible);
    }
    public int nbcarte()
    {
        return listecarte.size();
    }
    public void setCoucher(boolean c)
    {
        coucher = c;
    }
    public boolean getcoucher()
    {
        return coucher;
    }
    public void changesolde()
    {
        t.setText("joueur "+numjoueur+"   "+traitement.getJoueurs().get(numjoueur-1).getSolde()+" credit ");
    }
}
