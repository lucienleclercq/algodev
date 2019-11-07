package org.algodev.graph.sudo;

import javafx.scene.Group;

public class InterfaceSudo {

    private Group sudo;
    private GrilleSudo i;
    private int h;
    private int w;

    public InterfaceSudo(int h , int w ) {//h et w etant la taille de l'ecran
        sudo = new Group();
        this.h = h;
        this.w = w;
        i = new GrilleSudo(500,100,(int)(w*0.052),(int)(w*0.006) , (int)(w*0.001));//creation de la grille du sudoku
        sudo.getChildren().add(i.getG());
    }
    public Group getSudo()
    {
        return sudo;
    }
}
