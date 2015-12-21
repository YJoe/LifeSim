package LS;

import javafx.scene.paint.Color;

public class AntHill extends Shelter {
    public AntHill(int x, int y){
        super(x, y, 20);
        getImage().setFill(Color.rgb(200, 200, 100));
    }
}
