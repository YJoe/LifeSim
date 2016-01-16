package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Meat extends Food{
    private Random rand = new Random();
    private int baseSize = 2;
    private Color colour = Color.rgb(100, 0, 0);

    public Meat(int x, int y, int id, String type){
        super(x, y, id, type);
        setImage(new Circle(getX(), getY(), baseSize + rand.nextInt(3)));
        getImage().setFill(colour);
        setCal((int)(getImage().getRadius()) * 4);
        setSize((int)(getImage().getRadius()));
    }

    public Meat(int x, int y, int id, int size, String type){
        super(x, y, id, type);
        setImage(new Circle(getX(), getY(), size));
        getImage().setFill(colour);
        setCal((int)(getImage().getRadius()) * 4);
        setSize((int)(getImage().getRadius()));
    }
}
