package LS;

import javafx.scene.shape.Rectangle;

public abstract class Shelter {
    private int x, y, capacity;
    private Rectangle image;

    public Shelter(int x, int y, int capacity){
        setX(x);
        setY(y);
        setCapacity(capacity);

        // Create rectangle for shelter
        setImage(new Rectangle(getX(), getY(), 30, 30));
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

    public Rectangle getImage(){
        return image;
    }
    public void setImage(Rectangle image){
        this.image = image;
    }
}
