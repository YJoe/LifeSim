package LS;

import java.util.Random;
import javafx.scene.Group;
import java.util.ArrayList;

public class World {
    private Random rand = new Random();
    private int trackID = 0;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();

    // set up the world
    public World(int animals, int food){
        for(int i = 0; i < animals; i++){
            addRandomAnimal();
            trackID++;
        }
        for(int i = 0; i < food; i++){
            addRandomFood();
        }
    }

    public void giveRoot(Group root){
        for(int i = 0; i < animalList.size(); i++){
            root.getChildren().add(animalList.get(i).getSmellCircle());
        }
        for(int i = 0; i < animalList.size(); i++){
            root.getChildren().add(animalList.get(i).getImage());
        }
        for(int i = 0; i < animalList.size(); i++){
            root.getChildren().add(animalList.get(i).getTargetLocation());
        }
        for(int i = 0; i < foodList.size(); i++){
            root.getChildren().add(foodList.get(i).getImage());
        }
    }

    // add to the world
    public void addRandomAnimal(){
        //TODO: Make random Animals not just ants
        int x = rand.nextInt(Main.SIZE_X),
                y = rand.nextInt(Main.SIZE_Y);
        Animal a = new Ant(x, y, trackID);
        animalList.add(a);
    }

    public void addRandomFood(){
        int x = rand.nextInt(Main.SIZE_X),
                y = rand.nextInt(Main.SIZE_Y),
                cal = rand.nextInt(10) + 1;
        Food f = new Food(x, y, cal);
        foodList.add(f);
    }


    // get lists
    public ArrayList<Animal> getAnimalList(){
        return animalList;
    }

    public ArrayList<Food> getFoodList(){
        return foodList;
    }


    // run world
    public void update(){
        // call update for all animals
        for(int i = 0; i < animalList.size(); i++){
            animalList.get(i).update();
        }

        //
    }


}
