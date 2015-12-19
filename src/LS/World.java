package LS;

import java.util.Random;
import javafx.scene.Group;

import java.util.ArrayList;

public class World {
    private Random rand = new Random();
    private int trackID = 0;
    private boolean visibleSmellCircle = true, visibleTargetSquare = true;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();
    private Group animalGroup = new Group();
    private Group animalSmellGroup = new Group();
    private Group animalTargetGroup = new Group();
    private Group foodGroup = new Group();
    private Group rootRef;

    // set up the world
    public World(Group root, int animals, int food){
        rootRef = root;
        root.getChildren().add(foodGroup);
        root.getChildren().add(animalSmellGroup);
        root.getChildren().add(animalGroup);
        root.getChildren().add(animalTargetGroup);
        for(int i = 0; i < animals; i++) {
            addRandomAnimal();
            trackID++;
        }
        for(int i = 0; i < food; i++){
            addRandomFood();
            trackID++;
        }
    }

    // add to the world
    public void addRandomAnimal(){
        //TODO: Make random Animals not just ants
        int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
        Animal a = new Ant(x, y, trackID, foodGroup, animalGroup);
        a.setFoodList(foodList);
        animalList.add(a);
        trackID++;
        animalGroup.getChildren().add(a.getImage());
        animalSmellGroup.getChildren().add(a.getSmellCircle());
        animalTargetGroup.getChildren().add(a.getTargetLocation());
    }

    public void addRandomFood(){
        //TODO: make random food rather than just meat
        int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
        Meat f = new Meat(x, y, trackID);
        foodList.add(f);
        foodGroup.getChildren().add(f.getImage());
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
            if(animalList.get(i).getEnergy() < 0){
                animalGroup.getChildren().remove(i);
                animalSmellGroup.getChildren().remove(i);
                animalTargetGroup.getChildren().remove(i);
                animalList.remove(i);
            }
        }

        //
    }


    // display features
    public void toggleSmellCircles(){
        visibleSmellCircle = !visibleSmellCircle;
        animalSmellGroup.setVisible(visibleSmellCircle);
    }

    public void toggleTargetSquare(){
        visibleTargetSquare = !visibleTargetSquare;
        animalTargetGroup.setVisible(visibleTargetSquare);
    }
}
