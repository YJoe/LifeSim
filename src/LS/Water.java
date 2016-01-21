package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

/**
 * Water creates a circle of blue in which Animals can take Water from a WaterHazard Obstacle is
 * also generated at the given location
 */
public class Water {
    private int x;
    private int y;
    private int size;
    private Circle circle;

    /**
     * Constructor for Water generating two Circles at the given location
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Water(int x, int y){
        // Set attributes
        Random rand = new Random();
        this.x = x;
        this.y = y;
        this.setSize(rand.nextInt(20) + 70);
        this.setCircle(new Circle(x, y, size));
        getCircle().setFill(Color.rgb(120, 120, 255));
    }

    /**
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @param x X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @param y Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return Radius of waterCircle node
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size Radius of waterCircle node
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return WaterCircle node
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * @param circle WaterCircle node
     */
    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
