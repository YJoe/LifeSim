package LS;

import javafx.scene.paint.Color;

public class Cave extends Shelter{
    public Cave(int x, int y, int ID){
        super(x, y, 50, 10, ID, "Cave");
        getImage().setFill(Color.rgb(150, 150, 150));
    }
}