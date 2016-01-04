package LS;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class Shelter {
    private int x, y, capacity;
    private Circle image;
    private Inventory inventory;
    protected ArrayList<Animal> shelteredAnimals = new ArrayList<>();

    public Shelter(int x, int y, int capacity, int inventorySizeC, int inventorySizeS){
        setX(x);
        setY(y);
        setCapacity(capacity);
        setInventory(new Inventory(inventorySizeC, inventorySizeS));

        // Create rectangle for shelter
        setImage(new Circle(getX(), getY(), 30));
    }

    // Main functions
    public void update(){
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
