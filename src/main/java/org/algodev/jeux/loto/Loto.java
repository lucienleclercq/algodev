package org.algodev.jeux.loto;

import org.algodev.jeux.Case;
import org.algodev.jeux.Grille;
import org.algodev.jeux.poker.InterfaceMain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Loto
{
    protected int nbgrille; //indique le nombre de grille cas le joueur
    protected static ArrayList<Integer> numtirer; //liste de tous les numéros qui sont tirer
    protected Grille grille; //liste de toute les grilles
    protected static int etatpartie;
    protected static boolean fin;
    public Loto()
    {
        fin = false;
        etatpartie = 1;
        nbgrille = 0;
        numtirer = new ArrayList<Integer>();
        grille = new Grille(3,9,"Loto");
        CreerCarton();
    }
    public void restart()
    {
        numtirer.clear();
        CaseLoto cast;
        for(ArrayList<Case> c : grille.getGrille())
        {
            for(Case l : c)
            {
                cast = (CaseLoto)l;
                if(cast.tirer)cast.tirer=false;
                if(cast.jetons)cast.jetons = false;
            }
        }
        restartetatpartie();
    }
    public void CreerCarton()
    {// 5 par ligne
        int val = 0;
        int d = 0;
        if(nbgrille>0)grille.agrandir(3,9);
        for(int i = 0; i < 3 ; i ++)
        {
            for(int j = 0; j < 5; j++)
            {
                val = (int)(Math.random()*(9-1));
                d = (int)(Math.random()*(9-1));
                while (grille.getCase(i+(nbgrille*3),d).getValeur() !="vide" )
                {
                    d++;
                    if(d > 8)d = 0;
                }
                while (grille.getCase((nbgrille*3),d).getValeur().equals(String.valueOf(val+1+(d*10))) || grille.getCase((nbgrille*3)+1,d).getValeur().equals(String.valueOf(val+1+(d*10))) || grille.getCase((nbgrille*3)+2,d).getValeur().equals(String.valueOf(val+1+(d*10))))
                {
                    val++;
                    if(val > 8)val = 0;
                }
                    grille.setCase(i+(nbgrille*3),d,new CaseLoto(String.valueOf(val+1+(d*10))));
            }
        }
        this.nbgrille++;
    }
    public ArrayList NumTirer()
    {
        return numtirer;
    }
    public boolean toutSorti()
    {
        boolean estok = true;
        for(int j = 1; j < 90;j++ )
        {
            estok = true;
            for(Integer i : numtirer)
            {
                if(i.intValue() == j)estok = false;
            }
            if(estok)return true;
        }
        return  false;
    }
    public int aleatoire()
    {
        if(toutSorti())
        {
            int numero = 0;
            boolean estok = false;

            while (estok == false)
            {
                numero = (int) (Math.random()*(91-1))+1;
                estok = true;
                for(Integer i : numtirer)
                {
                    if(i.equals(new Integer(numero)))estok = false;
                }
            }
            numtirer.add(numero);
            CaseLoto c2 ;
            for(ArrayList<Case> c : grille.getGrille())
            {
                for(Case c1 : c)
                {
                    c2 = (CaseLoto)c1;
                    if(c2.getnum().equals(new Integer(numero).toString()))
                    {
                        c2.settirer(true);
                    }
                }
            }
            return numero;
        }
        else return 0;
    }
    public void poserRetirerJeton(int i , int y,boolean j)
    {

        CaseLoto c = (CaseLoto) grille.getGrille().get(i).get(y);
        c.setJetons(j);
    }
    public boolean VerifLigne(int nb)
    {

        boolean valider = true , fin = false;
        int nbligne = 0;
        CaseLoto c;
        for(int k = 0; k < nbgrille; k++)
        {
            nbligne = 0;
            for(int i = 0 ; i < 3 ; i++)
            {
                valider =true;
                for(int j = 0 ; j < 9 ; j ++)
                {
                    c = (CaseLoto)grille.getGrille().get(i+(3*k)).get(j);
                    if((!c.getjeton()|| !tirer(Integer.valueOf(c.getnum()))) && !c.getnum().equals("vide"))
                    {
                        System.out.println(valider);
                        valider = false;
                    }
                }
                if(valider)
                {
                    nbligne++;
                    if(nbligne == nb)return true;
                }
            }

        }
        return false;
    }
    @Override
    public String toString() {
        String sgrille = new String();
        CaseLoto c2;

        for(ArrayList<Case> c : grille.getGrille())
        {
            for(Case c1 : c)
            {
                c2 = (CaseLoto) c1;
                if(c2.getnum()!="vide")
                {
                    if(Integer.parseInt(c2.getnum()) > 0)
                    {
                        sgrille += c2.getnum();
                        if(c2.gettirer())sgrille+="/";
                        else sgrille+="|";
                        if(c2.getjeton())sgrille+="/";
                        else sgrille+="|";

                    }
                }
                else sgrille+= "    ";
            }
            sgrille+= "\n";
        }
        return "Loto{" + "nbgrille =" + nbgrille + ", numtirer =" + numtirer.toString() + ", grille =  \n" +sgrille+ '}';
    }
    public ArrayList<ArrayList<Case>> getGrille()
    {
        return grille.getGrille();
    }
    public void partie()
    {
        boolean fin = false;
        System.out.println(toString());
        while (fin == false)
        {
            Scanner scanner = new Scanner(System.in);
            int choix = 0;
            int x , y;
            System.out.println("0 - quitter");
            System.out.println("1 - numero suivant");
            System.out.println("2 - mettre un pion");
            System.out.println("3 - retirer un pion");
            choix = scanner.nextInt();
            switch (choix)
            {
                case 0:
                    fin = true;
                    break;
                case 1:
                    System.out.println(aleatoire());
                    break;
                case 2 :

                    System.out.println("saisir le numero de la ligne");
                    y = scanner.nextInt()-1;
                    System.out.println("saisi le numero de la colonne");
                    x = scanner.nextInt()-1;
                    poserRetirerJeton(y,x,true);
                    break;
                case 3:
                    System.out.println("saisir le numero de la ligne");
                    y = scanner.nextInt()-1;
                    System.out.println("saisi le numero de la colonne");
                    x = scanner.nextInt()-1;
                    poserRetirerJeton(y,x,false);
                    break;
            }
            System.out.println(toString());
        }
    }
    public void ajoutEtatpartie()
    {
     if(etatpartie <3)etatpartie ++;
     else fin = true;
    }
    public void restartetatpartie()
    {
        etatpartie = 1;
        fin = false;
    }

    public int getEtatpartie() {
        return etatpartie;
    }
    public boolean tirer(int nb)
    {
        for(int i : numtirer)
        {
            if(i == nb)return true;
        }
        return false;
    }
    public boolean getfin()
    {
        return fin;
    }
}