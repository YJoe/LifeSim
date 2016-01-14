package LS;

import javafx.scene.paint.Color;

public class DeadBear extends Meat{
    public DeadBear(int x, int y, int id, int size){
        super(x, y, id, size, "DeadAnt");
        getImage().setFill(Color.rgb(200, 100, 0));
    }
}
