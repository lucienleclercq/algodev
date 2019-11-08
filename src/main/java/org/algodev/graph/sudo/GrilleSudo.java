package org.algodev.graph.sudo;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.algodev.graph.Nombre;
import org.algodev.jeux.sudoku.CaseSudoku;
import org.algodev.jeux.sudoku.Sudoku;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Cette classe creer l'affichage de la grille du Sudoku et gere l'evenement onKeyPressed
 */

public class GrilleSudo {
    private ArrayList<Nombre> nombre;
    private Group g;
    private ArrayList<ArrayList<AffichageCaseSudoku>> grille;
    public GrilleSudo(int x, int y,int w ,int r, int e, Sudoku traitementsudoku, Scene scene)
    {
        g = new Group();
        int taillex = 0;
        int tailley = 0;
        nombre = new ArrayList<Nombre>();
        grille = new ArrayList<ArrayList<AffichageCaseSudoku>>();
        Nombre nb;
        Group no = new Group();
        for(int i = 0; i < 9 ; i ++ )//creation de toute les case
        {
            this.grille.add(new ArrayList<AffichageCaseSudoku>());
            for(int j = 0; j < 9; j++)
            {
                int n = Integer.parseInt(traitementsudoku.getCase(i, j).getValeur());
                nb = new Nombre(n ,(x+w*j+e*j)+4*e* (j/3),(y+w*i+e*i)+4*e* (i/3),w,r);                              //Remplit le tableau avec les valeurs de la grille
                nb.setText(Integer.toString(n));
                nb.getText().setY(((y+w*i+e*i)+4*e* (i/3))+ (r*6));                                                       //positionnement du texte
                nb.getText().setX(((x+w*j+e*j)+4*e*(j/3))+(r*3));
                nb.getText().setFont(new Font(50));                                                                  //declaration de la taille du texte
                CaseSudoku casesudo = (CaseSudoku) traitementsudoku.getCase(i, j);
                if(casesudo.getModifiable()) {                                                                            //Si la case est modifiable, on creer un AffichageCaseSudoku qui la rendra cliquable et modifiable
                    AffichageCaseSudoku c = new AffichageCaseSudoku((x+w*j+e*j)+4*e* (j/3), (y+w*i+e*i)+4*e*(i/3), w, e, i, j, nb, casesudo, this); //Créer un objet AffichageCaseSudoku
                    grille.get(i).add(c);                                                                                                                        //Créer un tableau d'AffichageCaseSudoku, utile pour aller voir si la case est sélectionnée ou non
                    no.getChildren().addAll(c.getG());
                }
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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {                                                            //Evenement de touche clavier
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().isDigitKey()) {                                                                   //Si la touche appuyée est un chiffre, il entre dans les boucles
                    for(ArrayList<AffichageCaseSudoku> liste : grille) {
                        for(AffichageCaseSudoku casesudo : liste) {
                            if(casesudo.getSelectionnee()) {                                                            //Si la valeur selectionnee dans l'objet AffichageCaseSudoku est true, la valeur de la case sera changee par la touche entree
                                casesudo.setSelectionnee(false);                                                        //Repasse la selection de la case a false
                                String input = keyEvent.getCode().toString().replaceAll("[^\\d.]", "");//Ligne qui permet de recuperer la valeur de l'input, le code regex permet de ne prendre que la valeur numerique et non les characteres, ex : NUMPAD1 devient 1
                                casesudo.getNombre().setCouleur(Color.web("0xC4C4C4", 1));           //Remet la case dans sa couleur d'origine
                                casesudo.getNombre().setText(input);                                                    //Change la valeur affichee de la case
                                casesudo.getCaseSudoku().setValeur(input);                                              //Change la valeur dans la grille du Sudoku (backend)
                                if(traitementsudoku.verificationDoublons(casesudo.getCaseSudoku())) {                   //Verifie s'il existe des doublons en ligne, colonne ou dans le même carre. Si true
                                    casesudo.getNombre().getText().setFill(Color.RED);                                  //Alors le chiffre est affiche en rouge
                                }
                                else casesudo.getNombre().getText().setFill(Color.DARKBLUE);                            //Sinon, en bleu
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Methode appelee lorsque l'on clique sur Verifier, cette methode verifie si la grille est bonne et affiche un texte si oui
     * @param traitementsudoku le jeu du Sudoku
     * @param x Position x dans la fenetre
     * @param y Position y dans la fenetre
     * @param w Largeur du bouton
     * @param e Espacement
     */

    public void verifier(Sudoku traitementsudoku, int x, int y, int w, int e) {
        Text gagne = new Text();
        gagne.setFont(new Font(100));
        gagne.setFill(Color.RED);
        gagne.setX(x+150);
        gagne.setY(y+9*w+9*e+100);
        try {
            if(traitementsudoku.finDePartie(traitementsudoku.chargerSolution())) {                    //Appelle la methode finDePartie dans la classe Sudoku.java, si true
                gagne.setText("Gagne");                                                               //Alors affiche texte Gagne
            }
            else gagne.setText("");                                                                   //Sinon, rien
        } catch (IOException err) {
            err.printStackTrace();
        }
        g.getChildren().add(gagne);
    }

    /**
     * Methode permettant de verifier dans le tableau d'AffichageCaseSudoku si une des cases dans ce tableau est deja selectionnee
     * @return return true si une case est deja selectionnee, sinon false
     */

    public Boolean caseDejaSelectionnee() {
        for(ArrayList<AffichageCaseSudoku> liste : grille) {
            for(AffichageCaseSudoku casesudoku : liste) {
                if(casesudoku.getSelectionnee()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Group getG()
    {
        return g;
    }

    public ArrayList<ArrayList<AffichageCaseSudoku>> getGrille() {
        return this.grille;
    }
}
