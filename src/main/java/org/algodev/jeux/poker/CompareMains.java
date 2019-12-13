package org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.List;

public class CompareMains {

    /**
     * Méthode appelée lorsque l'on veut définir le gagnant.
     * Appelée après que l'on ait défini toutes les mains possibles des joueurs et sélectionné leur meilleure.
     * @param joueursPoker Liste des joueurs encore en jeu.
     * @return La liste des gagnants. Il y en a plusieurs en cas d'égalité.
     */

    public List<JoueurPoker> definitGagnant(List<JoueurPoker> joueursPoker) {
        List<JoueurPoker> gagnants = new ArrayList<>();
        List<JoueurPoker> joueursMemePuissance = remplitListePuissances(joueursPoker);
        if(joueursMemePuissance.size() > 1) {                                           //Si un seul joueur a la combinaison la plus forte.
            gagnants.add(compareMainsJoueurs(joueursMemePuissance));                    //Alors on compare les mains de tous les joueurs, retourne la meilleure et l'ajoute à la liste des gagnants.
            joueursPoker.remove(gagnants.get(0));                                       //Retire le joueur déjà ajouté dans "gagnants".
            gagnants = verifieSiEgalite(gagnants, joueursPoker);                        //Vérifie si d'autres joueurs ont la même main que le joueur déjà sélectionné comme vainqueur.
        }
        else return joueursMemePuissance;                                               //Sinon, on retourne le seul joueur.
        return gagnants;                                                                //Retourne la liste des gagnants.
    }

    /**
     * Vérifie s'il y a une égalité parmi les mains.
     * @param gagnants Liste des gagnants. Un seul joueur.
     * @param joueursPoker Liste des tous les joueurs encore en jeu, moins celui de la liste "gagnants".
     * @return
     */

    public List<JoueurPoker> verifieSiEgalite(List<JoueurPoker> gagnants, List<JoueurPoker> joueursPoker) {
        for(JoueurPoker joueurPoker : joueursPoker) {
            if(joueurPoker.getMain().equals(gagnants.get(0).getMain())) {
                gagnants.add(joueurPoker);
            }
        }
        return gagnants;
    }

    /**
     * Va chercher la valeur de la combinaison la plus forte.
     * @param joueursPoker Liste des joueurs encore en jeu.
     * @return Long, la valeur de la combinaison la plus forte.
     */

    public List<JoueurPoker> remplitListePuissances(List<JoueurPoker> joueursPoker) {
        long puissanceMax = puissanceMax(joueursPoker);
        List<JoueurPoker> joueursMemePuissance = new ArrayList<>();
        for(JoueurPoker joueurPoker : joueursPoker) {
            if(joueurPoker.getMain().getPuissancedelamain() == puissanceMax) {
                joueursMemePuissance.add(joueurPoker);
            }
        }
        return joueursMemePuissance;
    }

    /**
     * Méthode appelée lorsque l'on a créé toutes les mains possibles d'un joueur et que l'on veut prendre la meilleure.
     * @param mainsPossiblesJoueur Les mains possibles d'un joueur.
     * @return La meilleure main.
     */

    public MainDeJoueur compareMainsPossibles(MainsPossiblesJoueur mainsPossiblesJoueur) {
        int puissance = (int) mainsPossiblesJoueur.getTypeDeMain();
        switch (puissance) {
            case 10:                                                                    //C'est une carte haute.
            case 1000000:                                                               //C'est une couleur.
                return carteHauteOuCouleur(mainsPossiblesJoueur);
            case 100:                                                                   //C'est une paire.
            case 1000:                                                                  //C'est une double paire.
                return paireOuDoublePaire(mainsPossiblesJoueur);
            case 10000:                                                                 //C'est un brelan.
            case 10000000:                                                              //C'est un full.
                return brelanOuFull(mainsPossiblesJoueur);
            case 100000000:                                                             //C'est un carré.
                return carre(mainsPossiblesJoueur);
            default:
                return mainsPossiblesJoueur.getListeMainsPossiblesJoueur().get(0);      //C'est une quinte.
        }
    }

    /**
     * Méthode appelée lorsque le joueur a une carte haute ou une couleur.
     * @param mainsPossiblesJoueur Mains Possibles, contient la liste des mains.
     * @return La meilleure main.
     */

    public MainDeJoueur carteHauteOuCouleur(MainsPossiblesJoueur mainsPossiblesJoueur) {
        return compare(mainsPossiblesJoueur, 1);
    }

    /**
     * Méthode appelée lorsque le joueur a une paire ou une double paire.
     * @param mainsPossiblesJoueur Mains Possibles, contient la liste des mains.
     * @return La meilleure main.
     */

    public MainDeJoueur paireOuDoublePaire(MainsPossiblesJoueur mainsPossiblesJoueur) {
        return compare(mainsPossiblesJoueur, 2);
    }

    /**
     * Méthode appelée lorsque le joueur a un brelan ou un full.
     * @param mainsPossiblesJoueur Mains Possibles, contient la liste des mains.
     * @return La meilleure main.
     */

    public MainDeJoueur brelanOuFull(MainsPossiblesJoueur mainsPossiblesJoueur) {
        return compare(mainsPossiblesJoueur, 3);
    }

    /**
     * Méthode appelée lorsque le joueur a un carre.
     * @param mainsPossiblesJoueur Mains Possibles, contient la liste des mains.
     * @return La meilleure main.
     */

    public MainDeJoueur carre(MainsPossiblesJoueur mainsPossiblesJoueur) {
        return compare(mainsPossiblesJoueur, 4);
    }

    /**
     * Méthode qui va comparer toutes les mains possibles.
     * @param mainsPossiblesJoueur Mains possibles, contient la liste des mains.
     * @param indice Entier, sert à savoir à quel indice il faut regarder après comparaison des premières cartes de deux mains. Exemple si on sait qu'il a un brelan, une fois le brelan comparé on va regarder la 4ème carte.
     * @return La main la plus forte.
     */

    public MainDeJoueur compare(MainsPossiblesJoueur mainsPossiblesJoueur, int indice) {
        int valeurComparaison;
        MainDeJoueur mainPlusForte = mainsPossiblesJoueur.getListeMainsPossiblesJoueur().get(0);                                                        //Prend la première main.
        List<MainDeJoueur> listeMainsPossible = mainsPossiblesJoueur.getListeMainsPossiblesJoueur();                                                    //Prend la liste des mains possibles.
        for (int i = 1; i < listeMainsPossible.size(); i++) {                                                                                           //Boucle jusque la dernière main possible.
            valeurComparaison = mainPlusForte.getCarte(0).getHauteur().compareTo(listeMainsPossible.get(i).getCarte(0).getHauteur());     //Compare la hauteur des premières cartes de deux mains.
            if (mainPlusForte.getCarte(0).getHauteur().getValeur() < listeMainsPossible.get(i).getCarte(0).getHauteur().getValeur()) {    //Si mainPlusForte est plus faible que la nouvelle.
                mainPlusForte = listeMainsPossible.get(i);                                                                                              //Alors on remplace mainPlusForte.
            } else {
                if (valeurComparaison == 0) {                                                                                                           //Si la première carte est la même.
                    for (int j = indice; j < 5; j++) {                                                                                                  //Boucle jusque la dernière carte des deux mains.
                        if (mainPlusForte.getCarte(j).getHauteur().getValeur() < listeMainsPossible.get(i).getCarte(j).getHauteur().getValeur()) {      //Si la carte de mainPlusForte à j est plus faible.
                            mainPlusForte = listeMainsPossible.get(i);                                                                                  //Alors on change la main.
                            break;                                                                                                                      //On sort du for.
                        } else {
                            if (mainPlusForte.getCarte(j).getHauteur().getValeur() > listeMainsPossible.get(i).getCarte(j).getHauteur().getValeur()) {  //On sort du for si mainPlusForte est toujours la plus forte.
                                break;
                            }
                        }
                    }
                }
            }
        }
        return mainPlusForte;
    }

    /**
     * Méthode qui va comparer les mains des joueurs.
     * Voir le commentaire de la méthode du dessus pour comprendre.
     * @param mainsDeJoueurs liste des joueurs toujours en jeu.
     * @return La main la plus forte parmi tous les joueurs.
     */

    public JoueurPoker compareMainsJoueurs(List<JoueurPoker> mainsDeJoueurs) {
        int valeurComparaison;
        List<JoueurPoker> gagnants;
        JoueurPoker gagnant = mainsDeJoueurs.get(0);
        MainDeJoueur mainPlusForte = gagnant.getMain();
        for (int i = 1; i < mainsDeJoueurs.size(); i++) {
            valeurComparaison = mainPlusForte.getCarte(0).getHauteur().compareTo(mainsDeJoueurs.get(i).getMain().getCarte(0).getHauteur());
            if (mainPlusForte.getCarte(0).getHauteur().getValeur() < mainsDeJoueurs.get(i).getMain().getCarte(0).getHauteur().getValeur()) {
                mainPlusForte = mainsDeJoueurs.get(i).getMain();
                gagnant = mainsDeJoueurs.get(i);
            } else {
                if (valeurComparaison == 0) {
                    for (int j = 1; j < 5; j++) {
                        if (mainPlusForte.getCarte(j).getHauteur().getValeur() < mainsDeJoueurs.get(i).getMain().getCarte(j).getHauteur().getValeur()) {
                            mainPlusForte = mainsDeJoueurs.get(i).getMain();
                            gagnant = mainsDeJoueurs.get(i);
                            break;
                        } else {
                            if (mainPlusForte.getCarte(j).getHauteur().getValeur() > mainsDeJoueurs.get(i).getMain().getCarte(j).getHauteur().getValeur()) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return gagnant;
    }

    /**
     * Méthode qui cherche la combinaison la plus puissante parmi les joueurs encore en jeu.
     * @param joueursPoker Liste des joueurs encore en jeu.
     * @return La puissance de la combinaison la plus forte.
     */

    public long puissanceMax(List<JoueurPoker> joueursPoker) {
        long puissance = 0, puissanceCourante = 0;
        for(JoueurPoker joueurPoker : joueursPoker) {
            puissanceCourante = joueurPoker.getMain().getPuissancedelamain();
            if(puissanceCourante > puissance) {
                puissance = puissanceCourante;
            }
        }
        return puissance;
    }
}
