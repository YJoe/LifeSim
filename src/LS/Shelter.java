package LS;

import javafx.scene.shape.Circle;

public abstract class Shelter {
    private int x, y, capacity;
    private Circle image;

    public Shelter(int x, int y, int capacity){
        setX(x);
        setY(y);
        setCapacity(capacity);

        // Create rectangle for shelter
        setImage(new Circle(getX(), getY(), 30));
    }

    // Getters and setters
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }

    public int getCapacity(){
        return capacity;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public Circle getImage(){
        return image;
    }
    public void setImage(Circle image){
        this.image = image;
    }
}
