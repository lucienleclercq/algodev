package grille;

public class Case {
   protected String valeur;
   protected Boolean modifiable;

    public Case() {
        valeur = "vide";
        this.modifiable = true;
    }

    public void setModifiable() {
          this.modifiable = false;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Boolean getModifiable() {
        return modifiable;
    }

    @Override
    public String toString() {
        return valeur;
    }
}