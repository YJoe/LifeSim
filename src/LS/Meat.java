package LS;

import javafx.scene.paint.Color;

import java.util.Random;

public class Meat extends Food{
    public Meat(int x, int y, int id){
        //    x  y             cal                        colour             size
        super(x, y, id, 120 + new Random().nextInt(40), Color.rgb(150, 0, 0), 4 + new Random().nextInt(4));
    }
}
