package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Obstacle allows for Obstacles of  multiple types to be created within the world Animals can not enter
 * any instantiation of this class.
 */
public class Obstacle {
    int x, y, size;
    String type;
    Circle image;

    /**
     * Obstacle constructor creating an Obstacle at a given location
     * @param type Type of Obstacle to create
     * @param x X coordinate
     * @param y Y coordinate
     * @param size Size of the Obstacle to create
     * @param colour Colour of the Obstacle to create
     */
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
