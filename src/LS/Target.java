package LS;

import javafx.scene.shape.Circle;

/**
 * Target allows for Targets to be simply created and deleted. Used by the Animal class, the location of targets
 * determines the direction in which an Animal moves
 */
public class Target {
    private int x;
    private int y;
    private Circle circle;

    /**
     * A constructor for a target created from coordinates, a Circle of radius 5 will
     * be created to allow for collisions
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Target(int x, int y){
        // Set attributes
        setX(x);
        setY(y);
        setCircle(new Circle(getX(), getY(), 5));
    }

    /**
     * A constructor for a target created from a Circle, the targetX and targetY is taken
     * from the Circle's center
     * @param circle The Circle node to target
     */
    public Target(Circle circle){
        // Set attributes
        setCircle(circle);
        setX((int)getCircle().getCenterX());
        setY((int)getCircle().getCenterY());
    }

    /**
     * @return X coordinate
     */
    public int getX(){
        return x;
    }

    /**
     * @param x X coordinate
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @param y Y Coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return Circle node to check collisions against
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * @param circle Circle node to check collisions against
     */
    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
