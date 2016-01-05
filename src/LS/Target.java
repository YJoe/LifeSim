package LS;

import javafx.scene.shape.Circle;

public class Target {
    private int x;
    private int y;
    private Circle circle;

    public Target(int x, int y){
        setX(x);
        setY(y);
        setCircle(new Circle(getX(), getY(), 5));
    }
    public Target(Circle circle){
        setCircle(circle);
        setX((int)getCircle().getCenterX());
        setY((int)getCircle().getCenterY());
    }

    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Circle getCircle() {
        return circle;
    }
    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
