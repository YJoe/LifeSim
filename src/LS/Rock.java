package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Rock extends Obstacle{
    private Random rand = new Random();
    public Rock(int x, int y){
        super(x, y);
        setSize(rand.nextInt(5) + 5);
        setImage(new Circle(getX(), getY(), getSize()));
        getImage().setFill(Color.rgb(150, 150, 170));
    }
}
