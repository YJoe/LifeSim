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
        // Set all attributes
        setX(x);
        setY(y);
        setType(type);
        setSize(size);
        setImage(new Circle(getX(), getY(), getSize()));
        getImage().setFill(colour);
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
    public int getY(){
        return y;
    }

    /**
     * @param y Y coordinate
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * @return Size of Obstacle
     */
    public int getSize(){
        return size;
    }

    /**
     * @param size Size of obstacle
     */
    public void setSize(int size){
        this.size = size;
    }

    /**
     * @return Circle node representing the Obstacle
     */
    public Circle getImage(){
        return image;
    }

    /**
     * @param image Circle node representing the Obstacle
     */
    public void setImage(Circle image){
        this.image = image;
    }

    /**
     * @param type Type of Obstacle
     */
    public void setType(String type){
        this.type = type;
    }

}
