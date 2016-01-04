package LS;

import javafx.scene.shape.Circle;

public abstract class Obstacle {
    int x, y, size;
    Circle image;

    public Obstacle(int x, int y){
        setX(x); setY(y);
    }
    public Obstacle(int x, int y, int size){
        setX(x);
        setY(y);
        setSize(size);
        setImage(new Circle(getX(), getY(), getSize()));
    }

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

    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }

    public Circle getImage(){
        return image;
    }
    public void setImage(Circle image){
        this.image = image;
    }
}
