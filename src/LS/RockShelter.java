package LS;

import javafx.scene.paint.Color;

public class RockShelter extends Shelter {
    public RockShelter(int x, int y, int ID) {
        super(x, y, 50, 10, ID, "RockShelter");
        getImage().setFill(Color.rgb(200, 200, 200));
    }
}
