package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Shelter creates an object within the world in which Animals can;
 * enter, store food, breed within and exit.
 */
public class Shelter {
    private int x, y, ID;
    private String type;
    private StatsBar statsBar;
    private Circle image;
    private Inventory foodInventory, waterInventory;
    protected ArrayList<Animal> shelteredAnimals = new ArrayList<>();

    /**
     * Constructor for Shelter generating an object or Animals to interact with
     * @param type The type of Shelter i.e. "AntHill" or "Nest"
     * @param x X coordinate
     * @param y Y coordinate
     * @param inventorySizeC Max slots within the inventory
     * @param inventorySizeS Slot size of inventory spaces
     * @param ID Unique ID
     * @param colour Colour of the Shelter to create
     */
    public Shelter(String type, int x, int y, int inventorySizeC, int inventorySizeS, int ID, Color colour){
        // Set all attributes
        setX(x);
        setY(y);
        setType(type);
        setFoodInventory(new Inventory(inventorySizeC, inventorySizeS));
        setWaterInventory(new Inventory(inventorySizeC, inventorySizeS));

        // Create stats bar to display shelter information
        setStatsBar(new StatsBar(x, y, 2));
        getStatsBar().getBar(0).setFill(Color.rgb(255, 100, 100));
        getStatsBar().getBar(1).setFill(Color.rgb(100, 100, 255));

        // Create Circle for shelter
        setImage(new Circle(getX(), getY(), 30));
        getImage().setFill(colour);

        // Set the unique ID of the shelter
        setID(ID);
    }

    /**
     * Update the stats bar of the Shelter representing accurately the inventory size
     */
    public void update(){
        // update the stats bar lengths representing the inventory
        getStatsBar().getBar(0).setWidth(getFoodInventory().getSize() * ((float)getStatsBar().getStatBarWidth() / (float)getFoodInventory().getCapacity()));
        getStatsBar().getBar(1).setWidth(getWaterInventory().getSize() * ((float)getStatsBar().getStatBarWidth() / (float)getWaterInventory().getCapacity()));

        // loop for all sheltered animals and decrement the wait at home time
        for (int i = 0; i < shelteredAnimals.size(); i++){
            shelteredAnimals.get(i).setWaitAtHome(shelteredAnimals.get(i).getWaitAtHome() - 1);
            if (shelteredAnimals.get(i).getWaitAtHome() <= 0){
                // remove animal from the sheltered list
                shelteredAnimals.get(i).exitShelter();
                shelteredAnimals.remove(i);
            }
        }
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

    public Circle getImage(){
        return image;
    }
    public void setImage(Circle image){
        this.image = image;
    }

    public Inventory getFoodInventory() {
        return foodInventory;
    }

    public void setFoodInventory(Inventory foodInventory) {
        this.foodInventory = foodInventory;
    }

    public Inventory getWaterInventory() {
        return waterInventory;
    }

    public void setWaterInventory(Inventory waterInventory) {
        this.waterInventory = waterInventory;
    }

    public StatsBar getStatsBar() {
        return statsBar;
    }

    public void setStatsBar(StatsBar statsBar) {
        this.statsBar = statsBar;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
