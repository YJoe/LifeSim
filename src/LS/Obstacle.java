package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Obstacle {
    int x, y, size;
    String type;
    Circle image;

    public Obstacle(String type, int x, int y, int size, Color colour){
        setX(x);
        setY(y);
        setType(type);
        setSize(size);
        setImage(new Circle(getX(), getY(), getSize()));
        getImage().setFill(colour);
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

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

}
