package LS;

import java.util.Random;

public class Ant extends Animal{
    private static Random rand = new Random();
    public Ant(int x, int y, int id){
        //  species name symbol id, x, y, energy, smell, size, speed, turnAngle
        super("Ant", "Joe", 'A', id, x, y, 100, (rand.nextInt(30) + 50 - 15), 5, 0.5, 30);
        String [] names_m = {"Antdrew", "Anty", "Antain", "Antanas", "Antar", "Anturas", "Antavas"};
        String [] names_f = {"Anttoinette", "Antalia", "Anta", "Anthia", "Antalia", "Antandra", "Antia", "Antheemia"};
        giveName(names_m, names_f);
    }
}
