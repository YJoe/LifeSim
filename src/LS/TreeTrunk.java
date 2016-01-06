package LS;

import javafx.scene.paint.Color;

public class TreeTrunk extends Obstacle{
    public TreeTrunk(int x, int y, int size){
        super(x, y, size);
        getImage().setFill(Color.rgb(100, 70, 30));
    }
}
