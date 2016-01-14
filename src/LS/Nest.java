package LS;

import javafx.scene.paint.Color;

public class Nest extends Shelter{
    public Nest(int x, int y, int ID){
        super(x, y, 50, 10, ID, "Nest");
        getImage().setFill(Color.rgb(50, 50, 50));
    }
}