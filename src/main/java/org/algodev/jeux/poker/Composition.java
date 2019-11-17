package poker;

import java.util.ArrayList;
import java.util.Collections;

public class Composition {

    /*
     * Renvoi la liste des cartes qui on la meme couleur en fonction de la liste de carte placer en parametre
     * si le nombre de carte de la meme couleur est supérieur ou égale a 5
     */
    public ArrayList<Carte> couleur(ArrayList<Carte> jeux){
        ArrayList<Carte> compo = new ArrayList<Carte>();
        int i = 0;

        for (Carte carte: jeux) {
            for (Carte cart: jeux) {
                if (carte.getCouleur().equals(cart.getCouleur())) {
                    compo.add(cart);
                    i++;
                }
            }
            if (i >= 5)
                return compo;
            compo.clear();
            i = 0;
        }
        return compo;
    }

    /*
     * renvoie vrai si la valeur est dans la list
     * renvoi faux sinon
     */
    public boolean appartient(int valeur, ArrayList<Carte> list){
        for(Carte carte : list){
            if (carte.getValeur() == valeur)
                return true;
        }
        return false;
    }

    /*
     * Renvoi la liste des cartes qui se suive de maniére consécutive en fonction de la liste de carte placer en parametre
     * si le nombre de carte qui se suive est supérieur ou égale a 5
     */
    public ArrayList<Carte> suite(ArrayList<Carte> jeux){
        ArrayList<Carte> jeux2 = new ArrayList<Carte>();
        ArrayList<Carte> suite = new ArrayList<Carte>();
        ArrayList<Carte> as = new ArrayList<Carte>();
        for (Carte carte: jeux) {
            if (!(appartient(carte.getValeur(), jeux2))) {
                if (carte.getValeur() == 1) {
                    as.add(carte);
                    jeux2.add(new Carte(14, carte.getCouleur()));
                }
                jeux2.add(carte);
            }
        }
        Collections.sort(jeux2);
        if (jeux2.size() < 5)
            return suite;
        int valeur;
        for (Carte carte: jeux2){
            valeur = carte.getValeur();
            for (Carte carte2: jeux2) {
                if(carte2.getValeur() == valeur) {
                    if (carte2.getValeur() == 14){
                        for (Carte c : as){
                            if (carte2.getCouleur().equals(c.getCouleur()))
                                carte2 = c;
                        }
                    }
                    suite.add(carte2);
                    valeur--;
                }
            }
            if(suite.size() >= 5)
                return suite;
            suite.clear();
        }
        return suite;
    }

    /*
     * Renvoi une Liste de Carte qui contient les éléments present dans les deux liste passer en parametre
     */
    public ArrayList<Carte> copieEquivalence(ArrayList<Carte> suite, ArrayList<Carte> couleur){
        ArrayList <Carte> copie = new ArrayList<>();
        for (Carte carte : suite){
            if (carte.getValeur() == 14) {
                for (Carte carte1 : couleur) {
                    if (carte1.getValeur() == 1)
                        copie.add(carte1);
                }
            }
            else
                for(Carte carte1 : couleur){
                    if (carte1.getValeur() == carte.getValeur())
                        copie.add(carte1);
                }
        }
        return copie;
    }

    /*
     * Renvoi la liste des cartes qui on la meme couleur et qui se suive de maniére consécutiveen fonction de la liste de carte placer en parametre
     * si le nombre de carte trouvé est supérieur ou égale a 5
     */
    public ArrayList<Carte> flush(ArrayList<Carte> suite, ArrayList<Carte> couleur){
        ArrayList<Carte> compo = new ArrayList<>();
        compo = copieEquivalence(suite, couleur);
        compo = suite(compo);
        return compo;
    }

    /*
     * renvoi la liste des cartes qui ont des valeurs identiques dans la liste de carte en parametre si elle corresponde au nombre placer en parametre
     * 2 : pour les duos
     * 3 : les trios
     * 4 : les quadruplés
     */
    public ArrayList<Carte>  couple(ArrayList<Carte> jeux, int nombre){
        int i = 0;
        boolean present = false;
        ArrayList<Carte> compo = new ArrayList<Carte>();
        for (Carte carte: jeux) {
            for (Carte cart: jeux) {
                if (carte.getValeur() == cart.getValeur())
                    i++;
            }
            if (i == nombre) {
                for (Carte verif : compo) {
                    if (verif.equals(carte))
                        present = true;
                }
                if (present == false)
                    compo.add(carte);
            }
            present = false;
            i = 0;
        }
        return compo;
    }
}
