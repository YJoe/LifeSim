package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

/**
 * Food provides a generic class in which to create various Food types such as Fruits and Meats.
 * Food objects need to be updated on every circle
 */
public class Food {
    private int x;
    private int y;
    private int id;
    private int size;
    private int decay;
    private String type;
    private int decayMax;
    private boolean poisonous;
    private Circle image;

    /**
     * @param type The type identifier to assign to the Food object
     * @param xIn X coordinate
     * @param yIn Y coordinate
     * @param id Unique ID
     * @param size Size of Food object
     * @param colour Colour of Food object
     */
    public Food(String type, int xIn, int yIn, int id, int size, Color colour){
        // Set all attributes
        setX(xIn);
        setY(yIn);
        setID(id);
        setDecay(0);
        setDecayMax(new Random().nextInt(1000) + 2000);
        setType(type);
        setImage(new Circle(getX(), getY(), size));
        getImage().setFill(colour);
        setSize(size);
        setPoisonous(false);
    }

    /**
     * Update the Food's decay, slowly becoming poison after time
     */
    public void update(){
        // If the food is not poison and add to the decay counter
        if (!isPoisonous()) {
            // If the food has reached its turning point set poisonous to true
            if (getDecay() > getDecayMax()) {
                setPoisonous(true);
                getImage().setFill(Color.rgb(0, 0, 0));
            } else {
                setDecay(getDecay() + 1);
            }
        }
    }

    /**
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @param x X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @param y Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return Unique Food ID
     */
    public int getID(){
        return this.id;
    }

    /**
     * @param id Unique ID
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * @return Size of Food
     */
    public int getSize(){
        return size;
    }

    /**
     * @param size Size of Food
     */
    public void setSize(int size){
        this.size = size;
    }

    /**
     * @return Circle node
     */
    public Circle getImage() {
        return image;
    }

    /**
     * @param image Circle node used to visualise the Food
     */
    public void setImage(Circle image) {
        this.image = image;
    }

    /**
     * @return Decay time of the Food
     */
    public int getDecay() {
        return decay;
    }

    /**
     * @param decay Decay time of the Food
     */
    public void setDecay(int decay) {
        this.decay = decay;
    }

    /**
     * @return If the food is poisonous
     */
    public boolean isPoisonous() {
        return poisonous;
    }

    /**
     * @param poisonous Poison boolean
     */
    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    /**
     * @return Maximum decay value
     */
    public int getDecayMax() {
        return decayMax;
    }

    /**
     * @param decayMax Maximum dacay value
     */
    public void setDecayMax(int decayMax) {
        this.decayMax = decayMax;
    }

    /**
     * @return Type of food i.e. "Fruit"
     */
    public String getType() {
        return type;
    }

    /**
     * @param type Type of Food i.e. "Fruit"
     */
    public void setType(String type) {
        this.type = type;
    }
}
