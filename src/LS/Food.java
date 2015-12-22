package LS;

import javafx.scene.shape.Circle;

public abstract class Food {
    private int x;
    private int y;
    private int id;
    private int cal;
    private int size;
    private Circle image;

    public Food(int xIn, int yIn, int id){
        setX(xIn);
        setY(yIn);
        setID(id);
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

    public int getID(){
        return this.id;
    }
    public void setID(int id){
        this.id = id;
    }

    public int getCal() {
        return cal;
    }
    public void setCal(int cal) {
        this.cal = cal;
    }

    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }

    public Circle getImage() {
        return image;
    }

    public void setImage(Circle image) {
        this.image = image;
    }

}
