package org.algodev.graph.poker;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.algodev.jeux.poker.Carte;
import org.algodev.jeux.poker.JoueurPoker;
import org.algodev.jeux.poker.PartiePoker;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui affiche les informations des joueurs et la partie.
 * Affiche les cartes, les soldes, les mises.
 */

public class AffichagePartie {

    private Group cartesCommunes;               //Groupe contenant les cartes communes.
    private List<Group> cartesJoueurs;          //Liste de groupes, chaque groupe contient les deux cartes d'un joueur.
    private List<Group> soldesJoueurs;          //Liste de groupes, chaque groupe contient l'affichage du solde d'un joueur.
    private List<Group> misesJoueurs;           //Liste de groupes, chaque groupe contient l'affichage de la mise d'un joueur.
    private Group pot;                          //Groupe contenant le pot.
    private StackPane root;                     //Fenêtre du poker.
    private PartiePoker partiePoker;            //Gestion de la partie (backend).

    /**
     * Constructeur AffichagePartie.
     * @param partiePoker PartiePoker, backend.
     * @param root La fenêtre du poker, provient de AffichagePoker.
     */

    public AffichagePartie(PartiePoker partiePoker, StackPane root) {
        cartesCommunes = new Group();
        this.partiePoker = partiePoker;
        this.root = root;
        cartesJoueurs = new ArrayList<>();
        soldesJoueurs = new ArrayList<>();
        misesJoueurs = new ArrayList<>();
        pot = new Group();
        nouvellePartie();
    }

    /**
     * Appelle la création du visuel des cartes en fonction du nombre de joueurs.
     */

    public void creerCartesJoueurs() {
        int nbJoueurs = partiePoker.getNbJoueurs();
        switch (nbJoueurs) {
            case 2:
                deuxJoueurs();
                break;
            case 3:
                troisJoueurs();
                break;
            case 4:
                quatreJoueurs();
                break;
        }
    }

    /**
     * Affiche les cartes communes en fonction de l'état d'avancement de la partie.
     * 0 : n'affiche aucune carte, 1 : affiche 3 cartes (river), 2 : affiche 1 carte (flop), 3 : affiche 1 carte (turn).
     */

    public void affichageCartesCommunes() {
        int etatPartie = partiePoker.getEtatpartie().getEtatpartie();
        switch (etatPartie) {
            case 1:
                river();
                reinitialiseMises();            //Réinitialise l'affichage de la mise.
                break;
            case 2:
                flop();
                reinitialiseMises();            //Réinitialise l'affichage de la mise.
                break;
            case 3:
                turn();
                reinitialiseMises();            //Réinitialise l'affichage de la mise.
                break;
            case 4:
                finPartie();                    //A l'état 4 la partie se termine, appelle l'affichage de fin de partie.
                break;
        }
    }

    /**
     * Affichage des trois premières cartes communes si elles ne sont pas encore affichées.
     */

    public void river() {
        if (cartesCommunes.getChildren().size() == 0) {                                             //Si elles ne sont pas affichées.
            List<Carte> cartesCommunes = partiePoker.getCartescommunes().getListecartescommunes();  //Prend la liste des cartes communes dans le backend.
            for (int i = 0; i < 3; i++) {                                                           //Boucle pour créer les cartes.
                String url = creerUrl(cartesCommunes.get(i));                                       //Créer l'url pour aller chercher l'image de la carte dans le dossier "resources".
                Rectangle carte = new Rectangle(80, 121);                             //Créer le rectangle qui contient la carte.
                carte.setX(82 * i);                                                                 //Décale le rectangle à chaque boucle.
                carte.setFill(getImageCarte(url));                                                  //Rempli le rectangle avec l'image.
                this.cartesCommunes.getChildren().add(carte);                                       //Ajoute la carte au groupe.
            }
            root.getChildren().add(this.cartesCommunes);                                            //Ajoute le groupe des cartesCommunes à l'affichage de la fenêtre.
        }
    }

    /**
     * Affiche la quatrième carte commune.
     */

    public void flop() {
        if (cartesCommunes.getChildren().size() == 3) {                                             //Si il y a déjà 3 cartes affichées.
            root.getChildren().remove(this.cartesCommunes);                                         //Il y déjà des cartes, on les enlève de l'affichage pour éviter conflits.
            List<Carte> cartesCommunes = partiePoker.getCartescommunes().getListecartescommunes();  //Prend la liste des cartes communes dans le backend.
            String url = creerUrl(cartesCommunes.get(3));                                           //Créer l'url pour aller chercher l'image de la carte dans le dossier "resources".
            Rectangle carte = new Rectangle(80, 121);                                 //Créer le rectangle qui contient la carte.
            carte.setX(82 * 3);                                                                     //Décale le rectangle.
            carte.setFill(getImageCarte(url));                                                      //Rempli le rectangle avec l'image.
            this.cartesCommunes.getChildren().add(carte);                                           //Ajoute la carte au groupe.
            root.getChildren().add(this.cartesCommunes);                                            //Réaffiche les cartes.
        }
    }

    /**
     * Affiche la cinquième carte commune.
     */

    public void turn() {
        if (cartesCommunes.getChildren().size() == 4) {                                             //S'il y a déjà 4 cartes d'affichiées.
            root.getChildren().remove(this.cartesCommunes);                                         //Il y déjà des cartes, on les enlève de l'affichage pour éviter conflits.
            List<Carte> cartesCommunes = partiePoker.getCartescommunes().getListecartescommunes();  //Prend la liste des cartes communes dans le backend.
            String url = creerUrl(cartesCommunes.get(4));                                           //Créer l'url pour aller chercher l'image de la carte dans le dossier "resources".
            Rectangle carte = new Rectangle(80, 121);                                 //Créer le rectangle qui contient la carte.
            carte.setX(82 * 4);                                                                     //Décale le rectangle.
            carte.setFill(getImageCarte(url));                                                      //Rempli le rectangle avec l'image.
            this.cartesCommunes.getChildren().add(carte);                                           //Ajoute la carte au groupe.
            root.getChildren().add(this.cartesCommunes);                                            //Réaffiche les cartes.
        }
    }

    /**
     * Créer l'url pour aller chercher l'image de la carte.
     * @param carte Carte dont on veut l'image.
     * @return
     */

    public String creerUrl(Carte carte) {
        String url = "/poker/cartes-poker/";
        String couleur = carte.getCouleur().toString();
        String hauteur = carte.getHauteur().toString();
        return url + hauteur + " " + couleur + ".png";
    }

    /**
     * Créer l'affichage pour deux joueurs.
     */

    public void deuxJoueurs() {
        List<JoueurPoker> listeJoueurs = partiePoker.getListejoueurs();
        for (JoueurPoker joueurPoker : listeJoueurs) {
            Group cartes = new Group();
            Group solde = new Group();
            Group mise = new Group();
            misesJoueurs.add(mise);
            soldesJoueurs.add(solde);
            cartesJoueurs.add(cartes);
            creerMisesJoueurs(mise);
            creerSoldesJoueurs(solde, joueurPoker);
            creerCartesJoueurs(cartes, joueurPoker);
            int indice = listeJoueurs.indexOf(joueurPoker);
            switch (indice) {
                case 0:
                    cartesJoueurs.get(0).setTranslateY(390);
                    afficherSoldes(-150, 390, soldesJoueurs.get(0));
                    afficherMisesJoueurs(0, 300, misesJoueurs.get(0));
                    break;
                case 1:
                    cartesJoueurs.get(1).setTranslateY(-390);
                    afficherSoldes(-150, -390, soldesJoueurs.get(1));
                    afficherMisesJoueurs(0, -300, misesJoueurs.get(1));
                    break;
            }
        }
        root.getChildren().addAll(cartesJoueurs);
        root.getChildren().addAll(soldesJoueurs);
        root.getChildren().addAll(misesJoueurs);
    }

    /**
     * Créer l'affichage pour trois joueurs.
     */

    public void troisJoueurs() {
        List<JoueurPoker> listeJoueurs = partiePoker.getListejoueurs();
        for (JoueurPoker joueurPoker : listeJoueurs) {
            Group cartes = new Group();
            Group solde = new Group();
            Group mise = new Group();
            misesJoueurs.add(mise);
            soldesJoueurs.add(solde);
            cartesJoueurs.add(cartes);
            creerMisesJoueurs(mise);
            creerSoldesJoueurs(solde, joueurPoker);
            creerCartesJoueurs(cartes, joueurPoker);
            int indice = listeJoueurs.indexOf(joueurPoker);
            switch (indice) {
                case 0:
                    cartesJoueurs.get(0).setTranslateY(390);
                    afficherSoldes(-150, 390, soldesJoueurs.get(0));
                    afficherMisesJoueurs(0, 300, misesJoueurs.get(0));
                    break;
                case 1:
                    cartesJoueurs.get(1).setTranslateX(-720);
                    afficherSoldes(-720, -100, soldesJoueurs.get(1));
                    afficherMisesJoueurs(-570, 0, misesJoueurs.get(1));
                    break;
                case 2:
                    cartesJoueurs.get(2).setTranslateY(-390);
                    afficherSoldes(-150, -390, soldesJoueurs.get(2));
                    afficherMisesJoueurs(0, -300, misesJoueurs.get(2));
                    break;
            }
        }
        root.getChildren().addAll(cartesJoueurs);
        root.getChildren().addAll(soldesJoueurs);
        root.getChildren().addAll(misesJoueurs);
    }

    /**
     * Créer l'affichage pour quatre joueurs.
     */

    public void quatreJoueurs() {
        List<JoueurPoker> listeJoueurs = partiePoker.getListejoueurs();
        for (JoueurPoker joueurPoker : listeJoueurs) {
            Group cartes = new Group();
            Group solde = new Group();
            Group mise = new Group();
            misesJoueurs.add(mise);
            soldesJoueurs.add(solde);
            cartesJoueurs.add(cartes);
            creerMisesJoueurs(mise);
            creerSoldesJoueurs(solde, joueurPoker);
            creerCartesJoueurs(cartes, joueurPoker);
            int indice = listeJoueurs.indexOf(joueurPoker);
            switch (indice) {
                case 0:
                    cartesJoueurs.get(0).setTranslateY(390);
                    afficherSoldes(-150, 390, soldesJoueurs.get(0));
                    afficherMisesJoueurs(0, 300, misesJoueurs.get(0));
                    break;
                case 1:
                    cartesJoueurs.get(1).setTranslateX(-720);
                    afficherSoldes(-720, -100, soldesJoueurs.get(1));
                    afficherMisesJoueurs(-570, 0, misesJoueurs.get(1));
                    break;
                case 2:
                    cartesJoueurs.get(2).setTranslateY(-390);
                    afficherSoldes(-150, -390, soldesJoueurs.get(2));
                    afficherMisesJoueurs(0, -300, misesJoueurs.get(2));
                    break;
                case 3:
                    cartesJoueurs.get(3).setTranslateX(720);
                    afficherSoldes(720, -100, soldesJoueurs.get(3));
                    afficherMisesJoueurs(570, 0, misesJoueurs.get(3));
                    break;
            }
        }
        root.getChildren().addAll(cartesJoueurs);
        root.getChildren().addAll(soldesJoueurs);
        root.getChildren().addAll(misesJoueurs);
    }

    /**
     * Créer l'affichage de la mise.
     * @param group
     */

    public void creerMisesJoueurs(Group group) {
        Text mise = new Text();
        mise.setText("Mise : 0");
        mise.setFill(Color.WHITE);
        mise.setStyle("-fx-font-size: 20");
        group.getChildren().add(mise);
    }

    /**
     * Créer le visuel des cartes pour un joueur.
     * @param group Group dans lequel on veut ajouter le visuel.
     * @param joueurPoker Le joueur dont on veut afficher les cartes.
     */

    public void creerCartesJoueurs(Group group, JoueurPoker joueurPoker) {
        List<Carte> mainJoueur = joueurPoker.getMain().getMain();
        for (int i = 0; i < 2; i++) {
            String url = creerUrl(mainJoueur.get(i));
            Rectangle carte = new Rectangle(80, 121);
            carte.setX(82 * i);
            carte.setY(121);
            carte.setFill(getImageCarte(url));
            group.getChildren().add(carte);
        }
    }

    /**
     * Créer le visuel du solde.
     * @param group
     * @param joueurPoker
     */

    public void creerSoldesJoueurs(Group group, JoueurPoker joueurPoker) {
        Text solde = new Text();
        Text nom = new Text();
        solde.setTranslateY(0);
        nom.setTranslateY(30);
        group.prefWidth(50);
        group.prefHeight(50);
        String valeurSolde = "Solde : " + joueurPoker.getSolde();
        solde.setText(valeurSolde);
        solde.setFill(Color.WHITE);
        solde.setStyle("-fx-font-size: 20");
        nom.setText(joueurPoker.getNom());
        nom.setFill(Color.WHITE);
        nom.setStyle("-fx-font-size: 20");
        group.getChildren().addAll(solde, nom);
    }

    /**
     * Mets à jour la mise.
     * Appelée à chaque fois qu'un joueur joue.
     * @param indice Indice du joueur dans la liste de joueur.
     * @param joueurPoker Joueur dont on veut afficher la mise.
     */

    public void modifierMise(int indice, JoueurPoker joueurPoker) {
        Group group = misesJoueurs.get(indice);
        String valeur = "Mise : " + joueurPoker.getMise();
        if(group.getChildren().get(0) instanceof Text) {
            Text mise = (Text) group.getChildren().get(0);
            mise.setText(valeur);
        }
    }

    /**
     * Change la position de la mise.
     * Appelée à chaque positionnement de joueur.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @param group Groupe contenant l'affichage de la mise.
     */

    public void afficherMisesJoueurs(int x, int y, Group group) {
        group.setTranslateX(x);
        group.setTranslateY(y);
    }

    /**
     * Récupère l'image que l'on veut.
     * @param url Url de l'image.
     * @return ImagePattern de l'image.
     */

    public ImagePattern getImageCarte(String url) {
        Image image = new Image(String.valueOf(this.getClass().getResource(url)));
        return new ImagePattern(image);
    }

    /**
     * Affiche le recto ou le verso en fonction de l'état du joueur. Si c'est à lui de jouer ou non.
     */

    public void retournerCartes() {
        JoueurPoker joueurActuel = partiePoker.getJoueurActuel();
        int indiceJoueur = partiePoker.getListejoueurs().indexOf(joueurActuel);
        for (Group group : cartesJoueurs) {
            if (cartesJoueurs.indexOf(group) != indiceJoueur) {
                afficherVerso(group);
            } else {
                afficherRecto(group, joueurActuel);
            }
        }
    }

    /**
     * Affiche le dos de la carte.
     * @param group Groupe contenant les cartes.
     */

    public void afficherVerso(Group group) {
        Image image = new Image(String.valueOf(this.getClass().getResource("/poker/cartes-poker/red_back.png")));
        for (int i = 0; i < 2; i++) {
            if (group.getChildren().get(i) instanceof Rectangle) {
                Rectangle carte = (Rectangle) group.getChildren().get(i);
                carte.setFill(new ImagePattern(image));
            }
        }
    }

    /**
     * Affiche le devant (hauteur et couleur) de la carte.
     * @param group Groupe contenant les visuels des cartes.
     * @param joueurActuel JoueurPoker dont on veut montrer les cartes.
     */

    public void afficherRecto(Group group, JoueurPoker joueurActuel) {
        List<Carte> main = joueurActuel.getMain().getMain();
        for (int i = 0; i < 2; i++) {
            String url = creerUrl(main.get(i));
            if (group.getChildren().get(i) instanceof Rectangle) {
                Rectangle carte = (Rectangle) group.getChildren().get(i);
                carte.setFill(getImageCarte(url));
            }
        }
    }

    /**
     * Méthode appelée en fin de partie.
     * Réaffiche les cartes communes dans le cas où tous les joueurs ont tapis au premier tour.
     */

    public void finPartie() {
        List<JoueurPoker> listeJoueurs = partiePoker.getListejoueurs();
        for (JoueurPoker joueurPoker : listeJoueurs) {
            if (!joueurPoker.isCouche()) {
                afficherRecto(cartesJoueurs.get(listeJoueurs.indexOf(joueurPoker)), joueurPoker);
            }
        }
        //viderAffichage();
        river();
        flop();
        turn();
    }

    /**
     * Méthode appelée pour afficher toutes les informations de la partie.
     */

    public void nouvellePartie() {
        creerCartesJoueurs();
        affichageCartesCommunes();
        afficherPot();
        retournerCartes();
    }

    /**
     * Appelée en fin de partie, quand on clique sur le bouton "Nouvelle Partie".
     */

    public void relancerPartie() {
        if (!cartesCommunes.getChildren().isEmpty()) {
            viderAffichage();
        }
        partiePoker.relancePartie();
        nouvellePartie();
    }

    /**
     * Positionne le solde à des coordonnées données.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @param group Groupe contennant le solde.
     */

    public void afficherSoldes(int x, int y, Group group) {
        group.setTranslateX(x);
        group.setTranslateY(y);
    }

    /**
     * Modifie la valeur du solde à chaque action du joueur.
     * @param indice Indice du joueur.
     * @param joueurPoker Joueur dont on veut le solde.
     */

    public void modifierValeurSolde(int indice, JoueurPoker joueurPoker) {
        String valeur = "Solde : " + joueurPoker.getSolde();
        Group group = soldesJoueurs.get(indice);
        if (group.getChildren().get(0) instanceof Text) {
            Text solde = (Text) group.getChildren().get(0);
            solde.setText(valeur);
        }
    }

    /**
     * Affiche le pot.
     */

    public void afficherPot() {
        String valeur = "Pot : " + partiePoker.getPot().getPot();
        Text pot = new Text();
        pot.setText(valeur);
        pot.setStyle("-fx-font-size: 20");
        pot.setFill(Color.WHITE);
        this.pot.setTranslateY(-150);
        this.pot.getChildren().add(pot);
        root.getChildren().add(this.pot);
    }

    /**
     * Modifie la valeur du pot à chaque action.
     */

    public void modifierValeurPot() {
        String valeur = "Pot : " + partiePoker.getPot().getPot();
        Text pot = (Text) this.pot.getChildren().get(0);
        pot.setText(valeur);
    }

    /**
     * Réinitialise l'affichage de la mise en fin de tour et en fin de partie.
     */

    public void reinitialiseMises() {
        List<JoueurPoker> listeJoueurs = partiePoker.getListejoueurs();
        for(JoueurPoker joueurPoker : listeJoueurs) {
            Group group = misesJoueurs.get(listeJoueurs.indexOf(joueurPoker));
            if(group.getChildren().get(0) instanceof Text) {
                Text mise = (Text) group.getChildren().get(0);
                mise.setText("Mise : 0");
            }
        }
    }

    /**
     * Redesinne toutes les cartes, tous les soldes, toutes les mises, toutes les cartes communes.
     * @param indice Indice du joueur.
     * @param joueurActuel JoueurPoker, joueur dont on veut les informations.
     */

    public void repaint(int indice, JoueurPoker joueurActuel) {
        modifierValeurSolde(indice, joueurActuel);
        modifierMise(indice, joueurActuel);
        modifierValeurPot();
        retournerCartes();
        affichageCartesCommunes();
    }

    /**
     * Clean tous les groupes, listes et la fenêtre d'affichage.
     */

    public void viderAffichage() {
        root.getChildren().removeAll(cartesJoueurs);
        root.getChildren().remove(cartesCommunes);
        root.getChildren().removeAll(misesJoueurs);
        root.getChildren().removeAll(soldesJoueurs);
        root.getChildren().remove(pot);
        cartesJoueurs.forEach(cartes -> cartes.getChildren().clear());
        misesJoueurs.forEach(mise -> mise.getChildren().clear());
        soldesJoueurs.forEach(solde -> solde.getChildren().clear());
        cartesCommunes.getChildren().clear();
        pot.getChildren().clear();
        cartesJoueurs.clear();
        misesJoueurs.clear();
        soldesJoueurs.clear();
        cartesJoueurs = new ArrayList<>();
    }

    public List<Group> getCartesJoueurs() {
        return cartesJoueurs;
    }
}
