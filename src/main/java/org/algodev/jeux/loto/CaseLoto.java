package org.algodev.jeux.loto;


import org.algodev.jeux.Case;

public class CaseLoto extends Case
{
    protected boolean tirer; //numero tirer ou pas
    protected boolean jetons; //jetons sur la case ou pas

    public CaseLoto(String num)
    {
        super(num);
        tirer = false;
        jetons = false;
    }
    public CaseLoto()
    {
        super();
        tirer = false;
        jetons = false;
    }
    public boolean istirer() {
        return tirer;
    }
    public boolean isJetons() {
        return jetons;
    }
    public void settirer(boolean tirer) {
        this.tirer = tirer;
    }
    public void setJetons(boolean jetons) {
        this.jetons = jetons;
    }
    public String getnum()
    {
        return super.valeur;
    }
    public boolean gettirer()
    {
        return tirer;
    }
    public  boolean getjeton()
    {
        return jetons;
    }
    @Override
    public String toString()
    {
        return "CaseLoto{" + "numtirer =" + tirer + ", jetons =" + jetons + '}';
    }
}