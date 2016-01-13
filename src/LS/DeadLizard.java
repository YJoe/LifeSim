package LS;

import javafx.scene.paint.Color;

public class DeadLizard extends Meat{
    public DeadLizard(int x, int y, int id, int size){
        super(x, y, id, size, "DeadLizard");
        getImage().setFill(Color.rgb(0, 150, 40));
    }
}
