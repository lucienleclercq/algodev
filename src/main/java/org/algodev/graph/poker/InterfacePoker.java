package org.algodev.graph.poker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.algodev.jeux.poker.InterfaceMain;
import org.algodev.jeux.poker.Table;

import java.util.ArrayList;

public class InterfacePoker {
    private int numJoueur;
    private Group g;
    private ArrayList<InterfaceMain> listemain;
    private int w;
    private int h;
    private int tour;
    private int joueurActif;
    private Text t;
    private Text t2;
    private Table traitementPoker;
    public InterfacePoker(int w, int h)
    {
        tour = 0;
        joueurActif = 1;
        traitementPoker = new Table();
        this.w = w;
        this.h = h;
        listemain = new ArrayList<>();
        numJoueur = 1;
        g = new Group();
        creeInterface();
    }
    private void creeInterface()
    {
        this.w = w;
        this.h = h;
        Text te = new Text("choisissez le nombre  \n de joueur");
        te.setFill(Color.WHITE);
        te.setFont(new Font(20));
        te.setX(10);
        te.setY(160);
        g.getChildren().add(te);
        ObservableList<Integer> choixnbjoueurs = FXCollections.observableArrayList(2, 3, 4);
        ChoiceBox<Integer> nbjoueursChoiceBox = new ChoiceBox<>(choixnbjoueurs);
        nbjoueursChoiceBox.setLayoutX(20);
        nbjoueursChoiceBox.setLayoutY(200);
        nbjoueursChoiceBox.setPrefWidth(40);
        nbjoueursChoiceBox.setPrefHeight(25);
        nbjoueursChoiceBox.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        nbjoueursChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                traitementPoker.lancerPartie(t1.intValue()+2);
                lancerpartie(t1.intValue()+2);
                g.getChildren().remove(nbjoueursChoiceBox);
                g.getChildren().remove(te);
            }
        });
        g.getChildren().add(nbjoueursChoiceBox);
    }

    public Group getG() {
        return g;
    }
    private void suivant(int nbjoueur)
    {
            if(!listemain.get(joueurActif).getcoucher())listemain.get(joueurActif).retourner(listemain.get(joueurActif).nbcarte(), false);
            joueurActif++;
            if (joueurActif > nbjoueur) joueurActif = 1;
            listemain.get(joueurActif).retourner(listemain.get(joueurActif).nbcarte(), true);
            t.setText("c'est au joueur " + joueurActif + " de jouer ");




    }
    private void lancerpartie(int nbjoueur)
    {
        t = new Text("c'est au joueur "+numJoueur+" de jouer ");
        t.setFont(new Font(30));
        t.setY(h*0.35);
        t.setX(w*0.45);
        t.setFill(Color.WHITE);
        g.getChildren().add(t);
        t2 = new Text("valeur de la mise :"+traitementPoker.getMisetable());
        t2.setFont(new Font(30));
        t2.setY(h*0.6);
        t2.setX(w*0.45);
        t2.setFill(Color.WHITE);
        g.getChildren().add(t2);
        listemain.add(new InterfaceMain(0,h,w,traitementPoker));
        g.getChildren().add(listemain.get(0).getG());

        for(int i = 1  ; i <= nbjoueur ; i ++)
        {
            listemain.add(new InterfaceMain(i,h,w,traitementPoker));
            g.getChildren().add(listemain.get(i).getG());
        }
        listemain.get(1).retourner(listemain.get(1).nbcarte(),true);
        Button coucher= new Button();
        coucher.setLayoutX(10);
        coucher.setLayoutY(200);
        coucher.setPrefWidth(125);
        coucher.setPrefHeight(25);
        coucher.setText("se coucher");
        coucher.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        coucher.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                listemain.get(joueurActif).setCoucher(true);
                suivant(nbjoueur);
            }
        });
        Button suivre = new Button();
        suivre.setLayoutX(10);
        suivre.setLayoutY(230);
        suivre.setPrefWidth(125);
        suivre.setPrefHeight(25);
        suivre.setText("suivre");
        suivre.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        suivre.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(tour <= 3&& traitementPoker.getJoueurs().get(joueurActif-1).getSolde() >= traitementPoker.getMisetable())
                {
                    traitementPoker.miser(traitementPoker.getJoueurs().get(joueurActif-1),traitementPoker.getMisetable());
                    listemain.get(joueurActif).changesolde();
                    if(traitementPoker.toutesEgales())
                    {
                        traitementPoker.tourSuivant();
                        switch (tour)
                        {
                            case 0:
                                listemain.get(0).retourner(3,true);
                                tour ++;
                                break;
                            case 1 :
                                listemain.get(0).ajoutCarte("/cartes-poker/"+traitementPoker.getTapis().get(3).getCarte());
                                listemain.get(0).retournerUn(3,true);
                                tour++;
                                break;
                            case 2:
                                listemain.get(0).ajoutCarte("/cartes-poker/"+traitementPoker.getTapis().get(4).getCarte());
                                listemain.get(0).retournerUn(4,true);
                                tour++;
                                break;
                            case 3:
                                for(InterfaceMain j : listemain)
                                {
                                    j.retourner(j.nbcarte(),true);
                                }
                                tour++;
                                break;
                        }
                    }
                    if(tour <= 3)suivant(nbjoueur);
                }
                }

        });
        TextField val = new TextField();
        val.setLayoutX(10);
        val.setLayoutY(260);
        val.setPrefWidth(125);
        val.setPrefHeight(25);
        g.getChildren().add(val);
        Button relance = new Button();
        relance.setLayoutX(10);
        relance.setLayoutY(290);
        relance.setPrefWidth(125);
        relance.setPrefHeight(25);
        relance.setText("relancer");
        relance.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        relance.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(tour<= 3 && Double.valueOf(val.getText()).intValue() >= traitementPoker.getMisetable()&&traitementPoker.getJoueurs().get(joueurActif-1).getSolde()>=Double.valueOf(val.getText()).intValue()){

                    traitementPoker.setMisetable((Double.valueOf(val.getText()).intValue()));
                    traitementPoker.miser(traitementPoker.getJoueurs().get(joueurActif-1),traitementPoker.getMisetable());
                    listemain.get(joueurActif).changesolde();
                    t2.setText("valeur de la mise :"+traitementPoker.getMisetable());
                    if(traitementPoker.toutesEgales())
                    {
                        traitementPoker.tourSuivant();
                        switch (tour)
                        {
                            case 0:
                                listemain.get(0).retourner(3,true);
                                tour ++;
                                break;
                            case 1 :
                                listemain.get(0).ajoutCarte("/cartes-poker/"+traitementPoker.getTapis().get(3).getCarte());
                                listemain.get(0).retournerUn(3,true);
                                tour++;
                                break;
                            case 2:
                                listemain.get(0).ajoutCarte("/cartes-poker/"+traitementPoker.getTapis().get(4).getCarte());
                                listemain.get(0).retournerUn(4,true);
                                tour++;
                                break;
                            case 3:
                                for(InterfaceMain j : listemain)
                                {
                                    j.retourner(j.nbcarte(),true);
                                }
                                tour++;
                                break;
                        }
                    }
                    if(tour <= 3)suivant(nbjoueur);

                }
                }
        });
        Button manche= new Button();
        manche.setLayoutX(10);
        manche.setLayoutY(320);
        manche.setPrefWidth(125);
        manche.setPrefHeight(25);
        manche.setText("manche suivante");
        manche.setStyle("-fx-font: 14 arial; -fx-base: #C4C4C4;");
        manche.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                for(InterfaceMain j : listemain)
                {
                    g.getChildren().remove(j.getG());
                }
                listemain.clear();
                tour = 0;
                joueurActif = 1;
                traitementPoker = new Table();
                traitementPoker.lancerPartie(nbjoueur);
                numJoueur = 1;
                System.out.println(nbjoueur);
                lancerpartie(nbjoueur);
            }
        });
        g.getChildren().add(manche);
        g.getChildren().add(relance);
        g.getChildren().add(coucher);
        g.getChildren().add(suivre);
    }
}
