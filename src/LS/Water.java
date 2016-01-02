package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Water {
    private int x;
    private int y;
    private int size;
    private Circle circle;

    public Water(int x, int y){
        Random rand = new Random();
        this.x = x;
        this.y = y;
        this.setSize(rand.nextInt(50) + 50);
        this.setCircle(new Circle(x, y, size));
        getCircle().setFill(Color.rgb(0, 100, 200));
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
