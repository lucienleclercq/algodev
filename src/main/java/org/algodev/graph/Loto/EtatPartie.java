package org.algodev.graph.Loto;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class EtatPartie {//pas fini permet d'afficher combien de ligne il faut pour gagner
    protected int etatpartie;
    protected Group g;
    protected Text t;
    public EtatPartie(int tailleh, int taillew)
    {
        g = new Group();
        etatpartie = 1;
        creeInterfaceEtatPartie(tailleh,taillew);
    }
    public void ajoutetatpartie()
    {
       if(etatpartie <= 3) etatpartie++;
    }
    public Group getG()
    {
        return g;
    }
    public void recommencer ()
    {
        etatpartie = 1;
    }
    public void suivant()
    {
        if(etatpartie <3)etatpartie++;
        sett();
    }
    private void creeInterfaceEtatPartie(int tailleh, int taillew)
    {
        t = new Text();
        t.setFont(new Font(30));
        t.setLayoutY(tailleh *  0.045);
        t.setLayoutX(taillew * 0.42);
        t.setFill(Color.WHITE);
        t.setText("vous jouer pour "+ new Integer(etatpartie).toString() + " ligne");

        Rectangle r = new Rectangle((int)(taillew * 0.366),(int)(tailleh * 0.0694));
        r.setLayoutX((int) (taillew * 0.309));
        r.setLayoutY((int)(tailleh * 0.002));
        r.setFill(Color.web("0x959595", 0.6));
        r.setArcHeight(10);
        r.setArcWidth(10);
        g.getChildren().add(r);
        g.getChildren().add(t);
    }
    public void fin()
    {
        t.setText("la partie est terminer");
    }
    public void setEtatpartie(int nb)
    {
        etatpartie = nb;
        sett();
    }
    private void sett()
    {
        t.setText("vous jouer pour "+ new Integer(etatpartie).toString() + " ligne");
    }
    public  int getEtatpartie()
    {
        return etatpartie;
    }
    public void restart()
    {
        etatpartie = 1;
        sett();
    }

}
