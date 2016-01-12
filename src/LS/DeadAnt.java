package LS;

import javafx.scene.paint.Color;

public class DeadAnt extends Meat{
    public DeadAnt(int x, int y, int id, int size){
        super(x, y, id, size, "DeadAnt");
        getImage().setFill(Color.rgb(50, 50, 50));
    }
}
