package LS;

import javafx.scene.paint.Color;

public class DeadEagle extends Meat{
    public DeadEagle(int x, int y, int id, int size){
        super(x, y, id, size, "Eagle");
        getImage().setFill(Color.rgb(200, 200, 200));
    }
}
