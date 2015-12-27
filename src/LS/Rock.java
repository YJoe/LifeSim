package LS;

import javafx.scene.shape.Circle;

public class Rock extends Obstacle{
    public Rock(int x, int y){
        super(x, y);
        setSize(10);
        setImage(new Circle(getX(), getY(), getSize()));
    }
}
