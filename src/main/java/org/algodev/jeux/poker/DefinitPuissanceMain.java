package org.algodev.jeux.poker;

import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe qui défini la puissance de toutes les mains possibles d'un joueur.
 */

public class DefinitPuissanceMain {

    private MainsPossiblesJoueur mainspossiblesjoueur;

    /**
     * Constructeur, appelle la méthode compareMains qui va analyser toutes les mains possibles.
     * Toutes les mains sont rangées dans l'ordre décroissant de la hauteur des cartes, ex : As Roi Neuf Huit Deux
     * @param mainspossibles Classe MainsPossiblesJoueur, contient toutes les mains possibles d'un joueur.
     */

    public DefinitPuissanceMain(MainsPossiblesJoueur mainspossibles) {
        for (MainDeJoueur main : mainspossibles.getListeMainsPossiblesJoueur()) {
            compareMains(main);
        }
        this.mainspossiblesjoueur = mainspossibles;
        retireMainsPlusFaibles();
    }

    /**
     * Méthode qui compare les mains.
     * @param main une des mains possibles d'un joueur. Vient du constructeur de cette classe.
     */

    public void compareMains(MainDeJoueur main) {
        if (isCouleur(main)) {              //Si toutes les cartes sont de la même couleur.
            switch (isQuinte(main)) {       //Si la main est une quinte (toutes les hauteurs de suivent, ex : 10, 9, 8, 7, 6). Retourne 0 et 1.
                case 0:                     //Si isQuinte retourne 0, le joueur a au minimum une couleur et au mieux un full.
                    isFull(main);
                    break;
                case 1:                     //Si isQuinte retourne 1, le joueur a une quinte.
                    flushOuRoyale(main);    //Appelle méthode pour définir si le joueur a une quinte flush ou une quinte flush royale.
                    break;
            }
        } else {
            switch (occurenceHauteur(main)) {  //Calcule s'il y a des cartes en plusieurs exemplaires.
                case 1:
                    quinte(main);
                    break;  //Si il n'y a pas de doublons, verifie si c'est une quinte.
                case 2:
                    paire(main);
                    break;   //S'il y a un doublon, c'est une paire.
                case 3:                       //Si retourne 3, il peut y avoir un brelan ou une double paire.
                    brelan(main);
                    break;
                case 4:                       //Si retourne 4, il peut y avoir un carre ou un full.
                    carre(main);
                    break;
            }
        }
    }

    /**
     * Méthode qui verifie si toutes les cartes d'une main sont de la même couleur.
     * @param main MainDeJoueur, 5 cartes.
     * @return True si toutes les cartes sont de la meme couleur.
     */

    public boolean isCouleur(MainDeJoueur main) {
        Carte carte = main.getCarte(0);
        for (int i = 0; i < main.getMain().size(); i++) {
            if (carte.getCouleur().compareTo(main.getCarte(i).getCouleur()) != 0) { //compareTo renvoie 0 quand les deux ont la meme valeur, -1 quand inférieure et 1 quand supérieure.
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui set la puissance de la main lorsque le joueur a une couleur.
     * @param main MainDeJoueur.
     */

    public void couleur(MainDeJoueur main) {
        main.setPuissancedelamain(1000000);
     }

    /**
     * Méthode qui vérifie si une main ayant au minimum une couleur est une quinte flush royale ou une quinte flush.
     * @param main MainDeJoueur, 5 cartes.
     */

    public void flushOuRoyale(MainDeJoueur main) {
        if (isQuinteFlushRoyale(main)) {            //Si la main est une quinte flush royale : As, Roi, Dame, Valet, 10, toutes de la meme couleur.
            quinteFlushRoyale(main);                //Appelle la méthode qui changera la puissance de la main.
        } else quinteFlush(main);                   //Sinon, c'est une quinte flush : suite de la meme couleur.
    }

    /**
     * Méthode qui vérifie si la main est une quinte flush royale.
     * @param main MainDeJoueur, 5 cartes.
     * @return True si la main est bien une quinte flush royale.
     */

    public boolean isQuinteFlushRoyale(MainDeJoueur main) {
        int mem = main.getCarte(0).getHauteur().getValeur();
        if (mem == 14) {
            return true;
        } else return false;
    }

    /**
     * Méthode qui change la valeur de la puissance de la main.
     * @param main MainDeJoueur, 5 cartes.
     */

    public void quinteFlushRoyale(MainDeJoueur main) {
        main.setPuissancedelamain(10000000000L);
    }

    /**
     * Méthode qui change la valeur de la puissance de la main.
     * @param main MainDeJoueur, 5 cartes.
     */

    public void quinteFlush(MainDeJoueur main) {
        main.setPuissancedelamain(1000000000); //Additionne les valeurs de toutes les cartes de la main.
    }

    /**
     * Méthode qui va vérifier si la main est une quinte ou pas.
     * @param main MainDeJoueur, 5 cartes.
     */

    public void quinte(MainDeJoueur main) {
        if (isQuinte(main) == 0) {           //Si pas une quinte, isQuinte renvoie 0. Si cette methode est appelee quand on a une couleur ou quand le joueur n'a rien.
            carteHaute(main);               //Le joueur n'a que des cartes hautes.
        }
    }

    /**
     * Méthode qui va renvoyer une certaine valeur si la main est une quinte. Elle additionne également les valeurs de toutes les cartes.
     * @param main
     * @return
     */

    public byte isQuinte(MainDeJoueur main) {
        int mem = main.getCarte(0).getHauteur().getValeur();        //Sauvegarde la valeur de la premiere carte.
        for (int i = 1; i < 5; i++) {
            int valactuelle = main.getCarte(i).getHauteur().getValeur();   //A chaque iteration, prend la valeur de la carte.
            if (valactuelle != mem - 1) {                                  //Si la valeur actuelle est differente de la valeur precedente.
                return 0;                                                  //Ce n'est pas une quinte.
            } else {                                                       //Sinon, on continue.
                mem = valactuelle;
            }
        }
        main.setPuissancedelamain(100000); //C'est une quinte, donc on change la puissance de la main.
        return 1;
    }

    /**
     * Méthode qui va appeler isFull si ce n'est pas un carré. Cette methode n'est appelée que lorsque l'occurence = 4.
     * @param main MainDeJoueur, 5 cartes.
     */

    public void carre(MainDeJoueur main) {
        if (!isCarre(main)) {
            isFull(main);
        }
    }

    /**
     * Méthode qui vérifie si une main est un carré.
     * @param main MainDeJoueur.
     * @return True si la main est un carre.
     */

    public boolean isCarre(MainDeJoueur main) {
        List<Carte> mem = new ArrayList<>(main.getMain());                  //Mise en memoire de la liste de cartes de MainDeJoueur.
        List<List<Carte>> ensembles = creerEnsembles(main, 4);   //Créer tous les ensembles possibles de 4 cartes parmi celles de la main.
        for (List<Carte> ensemble : ensembles) {                            //Parcourt la liste des ensembles. Sort chaque ensemble.
            if (puissanceCombinaison(main, ensemble, 4)) {            //Vérifie si un ensemble sorti de la liste est bien un le carré.
                main.setPuissancedelamain(100000000);                       //Il y a un carré, change la valeur de la main.
                mem.removeAll(ensemble);                                    //On retire les cartes du carre de la liste memoire, il reste une carte.
                ensemble.addAll(mem);                                       //Ajoute le reste des cartes de la memoire a l'ensemble.
                main.setMain(ensemble);                                     //Ajoute l'ensemble dans range dans la main. Un carré est rangé dans cet ordre, ex : Quatre Quatre Quatre Quatre As
                return true;
            }
        }
        return false;                                                       //Il n'y a pas de carré.
    }

    /**
     * Méthode qui verifie si une main est un full.
     * @param main MainDeJoueur.
     */

    public void isFull(MainDeJoueur main) {
        boolean contientbrelan = false;                                     //Initialise une valeur qui permettra de rentrer dans la deuxieme partie de la methode, définir la paire s'il y a un brelan. Un full c'est un brelan + paire.
        List<Carte> mem = new ArrayList<>(main.getMain());                  //Crée une mémoire de la liste de cartes passée en paramètre.
        List<List<Carte>> ensembles = creerEnsembles(main, 3);   //Crée tous les ensembles de 3 cartes parmi les 5.
        for (List<Carte> ensemble : ensembles) {                            //Parcourt tous les ensembles et les sort un a un.
            if (puissanceCombinaison(main, ensemble, 3)) {            //Si l'ensemble sorti est un brelan.
                mem.removeAll(ensemble);                                    //Si c'est un brelan, on retire les cartes du brelan de la liste en mémoire.
                ensemble.addAll(mem);                                       //Ajoute le reste des cartes de la mémoire a l'ensemble.
                main.setMain(ensemble);                                     //Ajoute l'ensemble rangé dans la main. Un carré est rangé dans cet ordre, ex : Quatre Quatre Quatre As As
                main.setPuissancedelamain(10000000);                        //Changement de la puissance de la main.
                contientbrelan = true;                                      //Brelan passe a true.
                break;                                                      //Permet de sortir du foreach.
            }
        }
        if (!contientbrelan) {                                               //Vérifie la valeur instantiée au début de la méthode. Est changée à true si jamais il y a un brelan.
            couleur(main);                                                   //Dans le cas où il y a une couleur mais pas de quinte.
        }
    }

    /**
     * Methode qui appelle la methode doublePaire si jamais ce n'est pas un brelan. Cette methode n'est appelee que lorsque occurrence = 3.
     * @param main MainDeJoueur.
     */

    public void brelan(MainDeJoueur main) {
        if (!isBrelan(main)) {
            doublePaire(main);
        }
    }

    /**
     * Methode qui definit si c'est un brelan.
     * @param main MainDeJoueur.
     * @return True si la main est un brelan.
     */

    public boolean isBrelan(MainDeJoueur main) {
        List<Carte> mem = new ArrayList<>(main.getMain());                      //Mémoire de la main.
        List<List<Carte>> ensembles = creerEnsembles(main, 3);       //Crée tous les ensembles de 3 cartes parmi 5.
        for (List<Carte> ensemble : ensembles) {                                //Parcourt les ensembles et les sort un par un.
            if (puissanceCombinaison(main, ensemble, 3)) {                //Si l'ensemble est un brelan.
                main.setPuissancedelamain(10000);                               //Change la puissance de la main.
                mem.removeAll(ensemble);                                        //Retire les cartes de l'ensemble de la liste mise en mémoire.
                ensemble.addAll(mem);
                main.setMain(ensemble);                                         //Ajoute l'ensemble dans range dans la main. Un carré est rangé dans cet ordre, ex : Quatre Quatre Quatre As Deux
                return true;
            }
        }
        return false;                                                           //Pas un brelan.
    }

    /**
     * Si la méthode est une double paire. Appelée dans isBrelan.
     * @param main MainDeJoueur.
     */

    public void doublePaire(MainDeJoueur main) {
        boolean contientunepaire = false;                                   //Initialise un boolean pour verifier si on a bien trouver une paire.
        List<Carte> mem = new ArrayList<>(main.getMain());                  //Crée une mémoire de la main.
        List<Carte> nouvelleMain = new ArrayList<>();                       //Va accueillir la nouvelle main range.
        List<List<Carte>> ensembles = creerEnsembles(main, 2);   //Crée tous les ensembles de 2 cartes parmi 5.
        for (List<Carte> ensemble : ensembles) {                            //Parcourt la liste des ensembles.
            if (puissanceCombinaison(main, ensemble, 2)) {            //Vérifie si un ensemble est une paire.
                mem.removeAll(ensemble);                                    //Retire la paire de la mémoire.
                nouvelleMain.addAll(ensemble);                              //Ajoute la paire de l'ensemble dans la main.
                contientunepaire = true;                                    //Changer la valeur booleene.
                break;
            }
        }
        if (contientunepaire) {                                             //S'il y a déjà une paire, on cherche la deuxieme.
            ensembles = creerEnsembles(mem, 2);                  //Pareil qu'au dessus.
            for (List<Carte> ensemble : ensembles) {
                if (puissanceCombinaison(main, ensemble, 2)) {
                    main.setPuissancedelamain(1000);
                    mem.removeAll(ensemble);                                //Retire la paire de la memoire.
                    ensemble.addAll(mem);                                   //Ajoute le reste des cartes en mémoire a l'ensemble
                    nouvelleMain.addAll(ensemble);                          //Ajoute l'ensemble à la nouvelle main qui contenait déjà une paire.
                    main.setMain(nouvelleMain);                             //Ajoute l'ensemble range dans la main. Un carré est rangé dans cet ordre, ex : Quatre Quatre Deux Deux As
                    break;
                }
            }
        }
    }

    /**
     * Méthode qui cherche la paire dans la main.
     * @param main MainDeJoueur.
     */

    public void paire(MainDeJoueur main) {                                  //Voir les methodes au dessus. Même chose.
        List<Carte> mem = new ArrayList<>(main.getMain());                  //Crée une memoire de la main.
        List<List<Carte>> ensembles = creerEnsembles(main, 2);   //Crée tous les ensembles de 2 cartes parmi 5.
        for(List<Carte> ensemble : ensembles) {
            if(puissanceCombinaison(main, ensemble, 2)) {
                main.setPuissancedelamain(100);
                mem.removeAll(ensemble);                                    //Retire de la memoire les cartes de l'ensemble (la paire).
                ensemble.addAll(mem);                                       //Ajoute les cartes de la memoire a l'ensemble.
                main.setMain(ensemble);                                     //Ajoute l'ensemble range dans la main. Ex : Deux Deux As Roi Dame
            }
        }
    }

    /**
     * Methode pour calculer les valeurs des cartes hautes. Une autre version que celle du dessus, paramètre différent.
     * @param main MainDeJoueur.
     */

    public void carteHaute(MainDeJoueur main) {
       main.setPuissancedelamain(10);
    }

    /**
     * Méthode générale qui va parcourir les listes de cartes passées en paramètre (ensemble).
     * @param main MainDeJoueur.
     * @param ensemble Ensemble de cartes, différentes tailles selon la combinaison que l'on cherche.
     * @param max Taille maximum de la liste d'ensemble.
     * @return True si ce que l'on cherche est correcte.
     * Exemple : si max : 4, vérifie si les 4 cartes sont de la même hauteur.
     */

    public boolean puissanceCombinaison(MainDeJoueur main, List<Carte> ensemble, int max) {
        for (Carte carte : ensemble) {
            if (carte.getHauteur().compareTo(ensemble.get(0).getHauteur()) != 0) {               //Si la carte actuelle est différente de la premier.
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui calcule l'occurence de doublons.
     * Retourne : 1 si pas de combinaison, 2 si paire, 3 si brelan ou double paire, 4 si carré ou full.
     * @param main MainDeJoueur.
     * @return La valeur du compteur.
     */

    public byte occurenceHauteur(MainDeJoueur main) {
        byte cpt = 1;
        for (int i = 1; i < main.getMain().size(); i++) {
            if (main.getCarte(i).getHauteur().compareTo(main.getCarte(i-1).getHauteur()) == 0) {
                cpt++;
            }
        }
        return cpt;
    }

    /**
     * Méthode qui se charge de créer les ensembles de cartes passées en paramètre.
     * @param main MainDeJoueur, la main dont on veut faire des ensembles.
     * @param nbelements Le nombre d'éléments que l'on veut par enesemble.
     * @return La liste des ensembles.
     */

    public List<List<Carte>> creerEnsembles(MainDeJoueur main, int nbelements) {
        return Generator.combination(main.getMain()).simple(nbelements).stream().collect(Collectors.toList()); //Appelle une méthode provenant du package org.paukov.combinatorics3.Generator
    }

    /**
     * Méthode qui se charge de créer les ensembles de cartes passées en paramètre.
     * @param main Liste de carte.
     * @param nbelements Le nombtre d'éléments que l'on veut par ensemble.
     * @return La liste des ensembles.
     */

    public List<List<Carte>> creerEnsembles(List<Carte> main, int nbelements) {
        return Generator.combination(main).simple(nbelements).stream().collect(Collectors.toList()); //Appelle une méthode provenant du package org.paukov.combinatorics3.Generator
    }

    public MainsPossiblesJoueur getMainspossiblesjoueur() {
        return mainspossiblesjoueur;
    }

    /**
     * Méthode qui va chercher la puissance de main la plus haute et retirer les mains ayant une valeur inferieure a celle-ci.
     */

    public void retireMainsPlusFaibles() {
        long valeur = getValeurPlusHaute();     //Appelle methode qui va chercher la valeur de la combinaison la plus haute.
        this.mainspossiblesjoueur.setTypeDeMain(valeur);
        this.mainspossiblesjoueur.getListeMainsPossiblesJoueur().removeIf(mainDeJoueur -> mainDeJoueur.getPuissancedelamain() != valeur); //Retire cette main si la puissance de la main est inferieure.
    }

    /**
     * Méthode qui va donner la puissance la plus élevée parmis toutes les mains.
     * @return Long, puissance superieure.
     */

    public long getValeurPlusHaute() {
        long mem = 0, puissanceActuelle;
        for(MainDeJoueur mainDeJoueur : mainspossiblesjoueur.getListeMainsPossiblesJoueur()) {
            puissanceActuelle = mainDeJoueur.getPuissancedelamain();
            if(puissanceActuelle > mem) {
                mem = puissanceActuelle;
            }
        }
        return mem;
    }
}
