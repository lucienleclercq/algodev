package org.algodev.graph.poker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import org.algodev.jeux.poker.JoueurPoker;
import org.algodev.jeux.poker.PartiePoker;

/**
 * Classe créant et affichant tous les boutons pour les joueurs.
 * Prend des évènements sur ces boutons.
 */

public class BoutonsPoker {

    private PartiePoker partiePoker;
    private AffichagePartie affichagePartie;
    private Group boutons;
    private StackPane root;

    /**
     * Constructeur de la classe BoutonPoker.
     * @param partiePoker La partie (backend).
     * @param affichagePartie Affichage de la partie, nécessaire pour réafficher les mises, cartes... A chaque action.
     * @param root La fenêtre du poker.
     */

    public BoutonsPoker(PartiePoker partiePoker, AffichagePartie affichagePartie, StackPane root) {
        this.partiePoker = partiePoker;
        this.affichagePartie = affichagePartie;
        this.root = root;
        boutons = new Group();
        boutons.setTranslateX(500);
        boutons.setTranslateY(300);
        boutons.prefWidth(200);
        boutons.prefHeight(200);
        affichageBoutons();
        root.getChildren().add(boutons);
    }

    /**
     * Créer le visuel des boutons.
     * Appelle gestion bouton à la fin pour ajouter les évènements aux boutons.
     */

    public void affichageBoutons() {
        if(!boutons.getChildren().isEmpty()) {
            boutons.getChildren().clear();
        }
        Button miser = creerBoutons(0,0,50,50, "Miser");
        TextArea champMise = new TextArea();
        champMise.setLayoutX(51);
        champMise.setPrefWidth(100);
        champMise.setPrefHeight(50);
        champMise.setMaxWidth(100);
        champMise.setStyle("-fx-font-size: 16");
        champMise.setText("0");
        champMise.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")) {
                    champMise.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        Button coucher = creerBoutons(51,51, 100, 50, "Se Coucher");
        Button suivre = creerBoutons(0, 51, 50, 50, "Suivre");
        changerSuivre(suivre);
        Button nouvellePartie = creerBoutons(0, 0, 100, 100, "Nouvelle Partie");
        nouvellePartie.setVisible(false);
        boutons.getChildren().addAll(miser, coucher, champMise, suivre, nouvellePartie);
        gestionBoutons(miser, coucher, suivre, nouvellePartie, champMise);
    }

    /**
     * Change le texte du bouton suivre en fonction de la mise maximale de la table.
     * @param suivre Bouton suivre.
     */

    public void changerSuivre(Button suivre) {
        int plusGrandeMise = partiePoker.getMise().getPlusGrandeMise();
        if(plusGrandeMise == 0) {                                       //Si la plus grande mise est 0.
            suivre.setText("Check");                                    //Le bouton suivre prend le texte "Check".
        }
        else {                                                          //Si misemax > 0.
            suivre.setText("Suivre");                                   //Le bouton suivre prend le texte "Suivre".
        }
    }

    /**
     * Ajoute tous les évènements à chaque bouton.
     * @param miser Bouton miser.
     * @param coucher Bouton coucher.
     * @param suivre Bouton suivre.
     * @param nouvellePartie Bouton nouvellePartie.
     * @param textArea TextArea, dans lequel on écrit la mise.
     */

    public void gestionBoutons(Button miser, Button coucher, Button suivre, Button nouvellePartie, TextArea textArea) {
        int plusGrandeMise = partiePoker.getMise().getPlusGrandeMise();                         //Get la mise la plus grande.
        miser.setOnMouseClicked(mouseEvent -> {                                                 //Event clic sur miser.
            JoueurPoker joueurActuel = partiePoker.getJoueurActuel();                           //Get joueur en train de jouer.
            int montant = Integer.parseInt(textArea.getText());                                 //Prend le montant écrit dans la zone de texte.
            int indice = partiePoker.getListejoueurs().indexOf(joueurActuel);                   //Prend l'indice du joueur dans la liste de joueurs.
            partiePoker.deroulementPartie(montant);                                             //Appelle la méthode dans partie de poker en charge du déroulement de la partie.
            affichagePartie.repaint(indice, joueurActuel);                                      //Appelle la méthode en charge de l'affichage des valeurs etc...
            cacherBoutons();                                                                    //Méthode qui cache les boutons dans le cas où la partie est finie.
        });
        suivre.setOnMouseClicked(mouseEvent -> {
            JoueurPoker joueurActuel = partiePoker.getJoueurActuel();                           //Get joueur en train de jouer.
            int indice = partiePoker.getListejoueurs().indexOf(joueurActuel);                   //Prend l'indice du joueur dans la liste de joueurs.
            partiePoker.deroulementPartie(plusGrandeMise);                                      //Bouton suivre donc le joueur joue la plus grande mise.
            affichagePartie.repaint(indice, joueurActuel);                                      //Appelle la méthode en charge de l'affichage des valeurs etc...
            cacherBoutons();                                                                    //Méthode qui cache les boutons dans le cas où la partie est finie.
        });
        coucher.setOnMouseClicked(mouseEvent -> {
            JoueurPoker joueurPoker = partiePoker.getJoueurActuel();                           //Get joueur en train de jouer.
            int indice = partiePoker.getListejoueurs().indexOf(joueurPoker);                   //Prend l'indice du joueur dans la liste de joueurs.
            affichagePartie.afficherVerso(affichagePartie.getCartesJoueurs().get(indice));     //Affiche le dos de la carte de ce joueur.
            partiePoker.seCouche();                                                            //Appelle la méthode se couche dans la gestion de partie. Va faire coucher le joueur et sélectionner le suivant si le tour n'est pas terminé.
            affichagePartie.repaint(indice, joueurPoker);                                      //Appelle la méthode en charge de l'affichage des valeurs etc...
            cacherBoutons();                                                                   //Méthode qui cache les boutons dans le cas où la partie est finie.
        });
        nouvellePartie.setOnMouseClicked(mouseEvent -> {                                       //Créer évènement sur le bouton de nouvelle partie.
            affichagePartie.relancerPartie();                                                  //Relance la partie.
            cacherBoutons();                                                                   //Cache le bouton nouvelle partie et réaffiche les autres.
        });
    }

    /**
     * Créer les boutons.
     * @param x Position x du bouton.
     * @param y Position y du bouton.
     * @param w Largeur du bouton.
     * @param h Hauteur du bouton.
     * @param text Texte à mettre dans le bouton.
     * @return Bouton créé.
     */

    public Button creerBoutons(int x, int y, int w, int h, String text) {
        Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(w);
        button.setPrefHeight(h);
        button.setText(text);
        return button;
    }

    /**
     * Affiche les boutons nouvelle partie quand la partie est terminée.
     * Affiche les boutons mise, se coucher et suivre/check quand la partie n'est pas terminée.
     */

    public void cacherBoutons() {
        if(partiePoker.getEtatpartie().getEtatpartie() == 4) {
            for(int i = 0; i < 4; i++) {
                Node bouton = boutons.getChildren().get(i);
                bouton.setVisible(false);
            }
            boutons.getChildren().get(4).setVisible(true);
        }
        else {
            affichageBoutons();
        }
    }
}
