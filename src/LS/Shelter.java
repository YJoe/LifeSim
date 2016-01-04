package LS;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class Shelter {
    private int x, y, capacity;
    private StatsBar statsBar;
    private Circle image;
    private Inventory foodInventory, waterInventory;
    protected ArrayList<Animal> shelteredAnimals = new ArrayList<>();

    public Shelter(int x, int y, int capacity, int inventorySizeC, int inventorySizeS){
        setX(x);
        setY(y);
        setCapacity(capacity);
        setFoodInventory(new Inventory(inventorySizeC, inventorySizeS));
        setWaterInventory(new Inventory(inventorySizeC, inventorySizeS));

        // Create stats bar to display shelter information
        setStatsBar(new StatsBar(x, y, 3));
        getStatsBar().getBar(0).setFill(Color.rgb(255, 100, 100));
        getStatsBar().getBar(1).setFill(Color.rgb(100, 100, 255));
        getStatsBar().getBar(2).setFill(Color.rgb(100, 255, 100));

        // Create Circle for shelter
        setImage(new Circle(getX(), getY(), 30));
    }

    // Main functions
    public void update(){
        getStatsBar().getBar(0).setWidth(getFoodInventory().getSize() * ((float)getStatsBar().getStatBarWidth() / (float)getFoodInventory().getCapacity()));
        getStatsBar().getBar(1).setWidth(getWaterInventory().getSize() * ((float)getStatsBar().getStatBarWidth() / (float)getWaterInventory().getCapacity()));

        for (int i = 0; i < shelteredAnimals.size(); i++){
            shelteredAnimals.get(i).setWaitAtHome(shelteredAnimals.get(i).getWaitAtHome() - 1);
            if (shelteredAnimals.get(i).getWaitAtHome() <= 0){
                shelteredAnimals.get(i).exitShelter();
                shelteredAnimals.remove(i);
                // get the worlds list and add the animal to it
                // find and set the node to visible
                // remove the animal from sheltered list
            }
        }
    }

    public boolean checkRoom(){
        if (shelteredAnimals.size() < getCapacity()){
            return true;
        }
        return false;
    }

    public void addAnimal(Animal a){
        shelteredAnimals.add(a);
        System.out.println("An animal is here!");
    }

    public void removeAnimal(int ID){
        for(int i = 0; i < shelteredAnimals.size(); i++){
            if (ID == shelteredAnimals.get(i).getID()){
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
}
