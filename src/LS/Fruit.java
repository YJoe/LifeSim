package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Fruit extends Food{
    private Random rand = new Random();
    private int baseSize = 2;
    private Color colourG = Color.rgb(0, 100, 0);
    private Color colourR = Color.rgb(200, 50, 50);

    public Fruit(int x, int y, int id){
        super(x, y, id);
        setImage(new Circle(getX(), getY(), baseSize + rand.nextInt(2)));
        if (rand.nextInt(2) == 1) {
            getImage().setFill(colourR);
        } else {
            getImage().setFill(colourG);
        }
        setCal((int)(getImage().getRadius()));
        setSize((int)(getImage().getRadius()));
    }
}
