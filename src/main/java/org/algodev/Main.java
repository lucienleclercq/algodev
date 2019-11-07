package org.algodev;

import org.algodev.graph.Fenetre;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {
    Fenetre f1;
    public static void main(String[] args) {
        launch( args);
        /*Loto l = new Loto();
        l.partie();*/
    }

    @Override
    public void start(Stage s1) throws FileNotFoundException {
        s1.setTitle("jeux");
        f1 = new Fenetre(s1);
    }


}
