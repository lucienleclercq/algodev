package td1;

public class Bidule
{
    String nom;
    int val;
    static int nb;
    int no;

    Bidule()
    {
        val = -1;
        nom = "";
        no = Bidule.nb;
        Bidule.nb++;
    }

    Bidule(String nom, int val)
    {
        this.nom = nom;
        this.val = val;
        no = Bidule.nb++;
    }

    public String toString()
    {
        return ("bidule " + nom + "val = " + val + "no = " + no);
    }

    public void Finalize()
    {
        System.out.println("moi bidule no = " + no + "je sors du systeme ");
    }
}
