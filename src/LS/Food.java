package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Food {
    private int x;
    private int y;
    private int cal;
    private Circle image;

    public Food(int xIn, int yIn, int calIn, Color colour, int size){
        setX(xIn);
        setY(yIn);
        setCal(calIn);
        setImage(new Circle(getX(), getY(), size));
        getImage().setFill(colour);
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

    public int getCal() {
        return cal;
    }
    public void setCal(int cal) {
        this.cal = cal;
    }

    public Circle getImage() {
        return image;
    }

    public void setImage(Circle image) {
        this.image = image;
    }

}
