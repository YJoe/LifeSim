package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public abstract class Food {
    private int x;
    private int y;
    private int id;
    private int cal;
    private int size;
    private int decay;
    private String type;
    private int decayMax;
    private boolean poisonous;
    private Circle image;

    public Food(int xIn, int yIn, int id, String type){
        setX(xIn);
        setY(yIn);
        setID(id);
        setDecay(0);
        setDecayMax(new Random().nextInt(1000) + 2000);
        setType(type);
        setPoisonous(false);
    }
    
    public void update(){
        if (!isPoisonous()) {
            if (getDecay() > getDecayMax()) {
                setPoisonous(true);
                getImage().setFill(Color.rgb(0, 0, 0));
            } else {
                setDecay(getDecay() + 1);
            }
        }
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

    public int getDecay() {
        return decay;
    }

    public void setDecay(int decay) {
        this.decay = decay;
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    public int getDecayMax() {
        return decayMax;
    }

    public void setDecayMax(int decayMax) {
        this.decayMax = decayMax;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
