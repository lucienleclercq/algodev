package td1;

public class Chose extends Bidule
{
    Couleur couleur;
    Chose()
    {
        couleur = CouleurBLEU;
    }
    Chose(String nom,int val, Couleur couleur)
    {
        super(nom,val);
        this.couleur = couleur;
    }
    public String to String ()
    {
        return("Chose " + nom + "val = " + val + "coouleur = " + couleur + "no = " + no);
    }
}
