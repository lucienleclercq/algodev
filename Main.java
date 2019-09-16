package td1;


import java.util.ArrayList;

public class Main
{
    public static void main(String[]args){
        Bidule b1 = new Bidule("test ", 100);
        Bidule b2 = new Bidule("Autre test ", 200);
        System.out.println(b1);
        System.out.println(b2);
        for (int i = 0; i < 1000; i++)
            b1 = new Bidule("b" + i, i * 2);
        System.gc();
        try
        {
            Thread.sleep(100);
        } catch (Exception e)
        {
        }
        var liste = new ArrayList<Bidule>()
        liste.add(new Bidule("d1",10));
        liste.add(new Chose)
    }

}
