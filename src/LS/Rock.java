package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Rock extends Obstacle{
    private Random rand = new Random();
    public Rock(int x, int y){
        super(x, y);
        setSize(rand.nextInt(8) + 3);
        setImage(new Circle(getX(), getY(), getSize()));
        getImage().setFill(Color.rgb(50, 50, 70));
    }
}
