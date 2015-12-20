package LS;

import java.util.Random;
import javafx.scene.Group;

import java.util.ArrayList;

public class World {
    private Random rand = new Random();
    private int trackID = 0;
    private boolean visibleSmellCircle = true, visibleTargetSquare = true;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Animal> animalRank = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();
    private Group animalGroup = new Group();
    private Group animalSmellGroup = new Group();
    private Group animalTargetGroup = new Group();
    private Group animalHungerBarGroup = new Group();
    private Group animalEnergyBarGroup = new Group();
    private Group animalBackBarGroup = new Group();
    private Group foodGroup = new Group();

    // set up the world
    public World(Group root, int animals, int food){
        root.getChildren().add(foodGroup);
        root.getChildren().add(animalSmellGroup);
        root.getChildren().add(animalGroup);
        root.getChildren().add(animalBackBarGroup);
        root.getChildren().add(animalHungerBarGroup);
        root.getChildren().add(animalEnergyBarGroup);
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
        animalGroup.getChildren().add(a.getImage());
        animalSmellGroup.getChildren().add(a.getSmellCircle());
        animalTargetGroup.getChildren().add(a.getTargetLocation());
        animalHungerBarGroup.getChildren().add(a.getHungerBar());
        animalEnergyBarGroup.getChildren().add(a.getEnergyBar());
        animalBackBarGroup.getChildren().add(a.getBackBar());
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
        if (animalList.size() > 0) {
            // call update for all animals
            for (int i = 0; i < animalList.size(); i++) {
                animalList.get(i).update();
                if (animalList.get(i).getEnergy() < 0) {
                    animalRank.add(animalList.get(i));
                    animalGroup.getChildren().remove(i);
                    animalSmellGroup.getChildren().remove(i);
                    animalTargetGroup.getChildren().remove(i);
                    animalBackBarGroup.getChildren().remove(i);
                    animalHungerBarGroup.getChildren().remove(i);
                    animalEnergyBarGroup.getChildren().remove(i);
                    animalList.remove(i);
                }
            }
        }
        else{
            printRank();
        }
    }

    public void printRank(){
        for(int i = animalRank.size() - 1; i > 0 ; i--){
            System.out.println("Rank " + (animalRank.size() - i));
            System.out.println(animalRank.get(i).statistics());
            System.out.println();
        }
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
