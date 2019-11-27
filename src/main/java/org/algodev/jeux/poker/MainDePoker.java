package main.java.org.algodev.jeux.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainDePoker {
    private List<Carte> main;

    public MainDePoker(Carte...cartes) {
        this.main = new ArrayList<>();
        this.main.addAll(Arrays.asList(cartes));
    }

    public List<Carte> getMain() {
        return main;
    }
}
