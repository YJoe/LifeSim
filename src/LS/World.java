package LS;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class World {
    private Random rand = new Random();
    private int trackID = 0;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Animal> animalRank = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();
    private ArrayList<Shelter> shelterList = new ArrayList<>();
    private ArrayList<Obstacle> obstacleList = new ArrayList<>();
    private Group animalGroup = new Group();
    private Group animalSmellGroup = new Group();
    private Group animalTargetGroup = new Group();
    private Group animalHungerBarGroup = new Group();
    private Group animalEnergyBarGroup = new Group();
    private Group animalBackBarGroup = new Group();
    private Group animalHomeLocationGroup = new Group();
    private Group shelterGroup = new Group();
    private Group foodGroup = new Group();
    private Group obstacleGroup = new Group();

    // set up the world
    public World(Group root, int animals, int food, int shelters, int obstacles){
        System.out.println("Creating world");
        root.getChildren().add(shelterGroup);
        root.getChildren().add(obstacleGroup);
        root.getChildren().add(foodGroup);
        root.getChildren().add(animalSmellGroup);
        root.getChildren().add(animalGroup);
        root.getChildren().add(animalBackBarGroup);
        root.getChildren().add(animalHungerBarGroup);
        root.getChildren().add(animalEnergyBarGroup);
        root.getChildren().add(animalTargetGroup);
        root.getChildren().add(animalHomeLocationGroup);

        for(int i = 0; i < animals; i++) {
            addRandomAnimal();
        }
        for(int i = 0; i < food; i++){
            addRandomFood();
        }
        for(int i = 0; i < shelters; i++){
            addRandomShelter();
        }
        for(int i = 0; i < obstacles; i++){
            addRandomObstacle();
        }
        System.out.println("World created and populated");
    }

    // add to the world
    public void addRandomAnimal(){
        //TODO: Make random Animals not just ants
        Animal a;
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
            a = new Ant(x, y, trackID, foodGroup, animalGroup);
        } while(overlapsAnything(a.getImage()));
        a.setFoodList(foodList);
        a.setShelterList(shelterList);
        a.setObstacleList(obstacleList);
        animalList.add(a);
        animalGroup.getChildren().add(a.getImage());
        animalSmellGroup.getChildren().add(a.getSmellCircle());
        animalTargetGroup.getChildren().add(a.getTargetLocation());
        animalHungerBarGroup.getChildren().add(a.getHungerBar());
        animalEnergyBarGroup.getChildren().add(a.getEnergyBar());
        animalBackBarGroup.getChildren().add(a.getBackBar());
        animalHomeLocationGroup.getChildren().add(a.getHomeLocation());
        trackID++;
    }

    public void addRandomFood(){
        //TODO: make random food rather than just meat
        Meat f;
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
            f = new Meat(x, y, trackID);
        }while(overlapsAnything(f.getImage()));
        foodList.add(f);
        foodGroup.getChildren().add(f.getImage());
        trackID++;
    }

    public void addFood(int x, int y, int size){
        Meat f = new Meat(x, y, trackID, size);
        foodList.add(f);
        foodGroup.getChildren().add(f.getImage());
        trackID++;
    }

    public void addRandomShelter(){
        // TODO: Make random shelters
        Shelter s;
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
            s = new AntHill(x, y);
        } while(overlapsAnything(s.getImage()));
        shelterList.add(s);
        shelterGroup.getChildren().add(s.getImage());
    }

    public void addRandomObstacle(){
        Obstacle o;
        do {
            o = new Rock(rand.nextInt(Main.SIZE_X), rand.nextInt(Main.SIZE_Y));
        } while(overlapsAnything(o.getImage()));
        obstacleList.add(o);
        obstacleGroup.getChildren().add(o.getImage());
    }

    public boolean overlapsAnything(Circle c1){
        for(int i = 0; i < animalList.size(); i++){
            if (Collision.overlapsEfficient(c1, animalList.get(i).getImage())){
                return true;
            }
        }
        for(int i = 0; i < obstacleList.size(); i++){
            if (Collision.overlapsEfficient(c1, obstacleList.get(i).getImage())){
                return true;
            }
        }
        for(int i = 0; i < foodList.size(); i++){
            if (Collision.overlapsEfficient(c1, foodList.get(i).getImage())){
                return true;
            }
        }
        return false;
    }

    // remove from the world
    public void killAnimal(int i){
        addFood((int)(animalList.get(i).getImage().getCenterX() + animalList.get(i).getImage().getTranslateX()),
                (int)(animalList.get(i).getImage().getCenterY() + animalList.get(i).getImage().getTranslateY()),
                (int)(animalList.get(i).getImage().getRadius()));
        animalRank.add(animalList.get(i));
        animalGroup.getChildren().remove(i);
        animalSmellGroup.getChildren().remove(i);
        animalTargetGroup.getChildren().remove(i);
        animalBackBarGroup.getChildren().remove(i);
        animalHungerBarGroup.getChildren().remove(i);
        animalEnergyBarGroup.getChildren().remove(i);
        animalList.remove(i);
    }

    // get lists
    public ArrayList<Animal> getAnimalList(){
        return animalList;
    }
    public ArrayList<Food> getFoodList(){
        return foodList;
    }
    public ArrayList<Shelter> getShelterList() {
        return shelterList;
    }
    public ArrayList<Obstacle> getObstacleList(){
        return obstacleList;
    }

    // run world
    public void update(){
        // call update for all animals
        for (int i = 0; i < animalList.size(); i++) {
            animalList.get(i).update();
            if (animalList.get(i).getEnergy() < 0) {
                killAnimal(i);
            }
        }

        // call update for all shelters
        for (int i = 0; i < shelterList.size(); i++){
            shelterList.get(i).update();
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
        animalSmellGroup.setVisible(!animalSmellGroup.isVisible());
    }

    public void toggleTargetSquares(){
        animalTargetGroup.setVisible(!animalTargetGroup.isVisible());
    }

    public void toggleStatBars(){
        animalEnergyBarGroup.setVisible(!animalEnergyBarGroup.isVisible());
        animalHungerBarGroup.setVisible(!animalHungerBarGroup.isVisible());
        animalBackBarGroup.setVisible(!animalBackBarGroup.isVisible());
    }

    public void toggleHomeSquares(){
        animalHomeLocationGroup.setVisible(!animalHomeLocationGroup.isVisible());
    }

}
