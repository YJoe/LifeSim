package LS;

import javafx.scene.paint.Color;

public class WaterHazard extends Obstacle {
    public WaterHazard(int x, int y, int size){
        super(x, y, size);
        getImage().setFill(Color.rgb(50, 50, 200));
    }
}
