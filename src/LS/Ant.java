package LS;

import javafx.scene.Group;

import java.util.Random;

public class Ant extends Animal{
    private static Random rand = new Random();
    public Ant(int x, int y, int id, Group foodGroup, Group animalGroup){
        //  species, name, symbol, id, x, y, energy, smell, size, speed, turnAngle, foodgroup, animalgroup
        super("Ant", "Joe", 'A', id, x, y, 1000, (rand.nextInt(30) + 50 - 15), 2, 0.1 + (rand.nextInt(10) * 0.1), 50,
                foodGroup, animalGroup);
        String [] names_m = {"Antdrew", "Anty", "Antain", "Antanas", "Antar", "Anturas", "Antavas"};
        String [] names_f = {"Anttoinette", "Antalia", "Anta", "Anthia", "Antalia", "Antandra", "Antia", "Antheemia"};
        giveName(names_m, names_f);
    }
}
