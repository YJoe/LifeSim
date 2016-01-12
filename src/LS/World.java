package LS;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.control.TreeTableCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class World {
    private Random rand = new Random();
    private int year;
    private int day;
    private int dayLength;
    private int dayLengthCounter;
    private WorldStatsBar worldStatsBar;
    public static int trackFoodID = 0;
    public static int trackAnimalID = 0;
    private int shelterID = 0;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Animal> animalRank = new ArrayList<>();
    private ArrayList<FoodTree> foodTreeList = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();
    private ArrayList<Water> waterList = new ArrayList<>();
    private ArrayList<Shelter> shelterList = new ArrayList<>();
    private ArrayList<Obstacle> obstacleList = new ArrayList<>();
    private Group animalGroup = new Group();
    private Group animalSmellGroup = new Group();
    private Group animalTargetGroup = new Group();
    private Group animalStatsGroup = new Group();
    private Group animalHomeLocationGroup = new Group();
    private Group animalLabelGroup = new Group();
    private Group shelterGroup = new Group();
    private Group shelterStatsGroup = new Group();
    private Group foodGroup = new Group();
    private Group waterGroup = new Group();
    private Group obstacleGroup = new Group();
    private Group foodTreeLeafGroup = new Group();
    private Group foodTreeTrunkGroup = new Group();

    // set up the world
    public World(Group root, int animals, int foodTrees, int food, int shelters, int obstacles, int pools){
        System.out.println("Creating world");
        setDay(0);
        setYear(0);
        setDayLength(10);
        setDayLengthCounter(0);

        root.getChildren().add(shelterGroup);
        root.getChildren().add(shelterStatsGroup);
        root.getChildren().add(waterGroup);
        root.getChildren().add(obstacleGroup);
        root.getChildren().add(foodGroup);
        root.getChildren().add(animalSmellGroup);
        root.getChildren().add(animalGroup);
        root.getChildren().add(animalStatsGroup);
        root.getChildren().add(animalTargetGroup);
        root.getChildren().add(animalHomeLocationGroup);
        root.getChildren().add(animalLabelGroup);
        root.getChildren().add(foodTreeTrunkGroup);
        root.getChildren().add(foodTreeLeafGroup);

        setWorldStatsBar(new WorldStatsBar(root));
        getWorldStatsBar().setDateString(getDateString());
        getWorldStatsBar().setAnimalCountString(animals);
        getWorldStatsBar().setFoodCountString(food);
        getWorldStatsBar().setShelterCountString(shelters);


        System.out.println("Creating pools");
        for(int i = 0; i < pools; i++){
            addRandomPool();
            System.out.println(i+1 + "/" + pools);
        }
        System.out.println("Creating food trees");
        for(int i = 0; i < foodTrees; i++){
            addRandomFoodTree();
            System.out.println(i+1 + "/" + foodTrees);
        }
        System.out.println("Creating shelters");
        for(int i = 0; i < shelters; i++){
            addRandomShelter();
            System.out.println(i+1 + "/" + shelters);
        }
        System.out.println("Creating animals");
        for(int i = 0; i < animals; i++) {
            addRandomAnimal();
            System.out.println(i+1 + "/" + animals);
        }
        System.out.println("Creating food");
        for(int i = 0; i < food; i++){
            addRandomFood();
            System.out.println(i+1 + "/" + food);
        }
        System.out.println("Creating obstacles");
        for(int i = 0; i < obstacles; i++){
            addRandomObstacle();
            System.out.println(i+1 + "/" + obstacles);
        }
        System.out.println("World created and populated");
    }

    // add to the world
    public void addRandomAnimal(){
        //TODO: Make random Animals not just ants
        Animal a;
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
            a = new Ant(x, y, trackAnimalID, getDay(), getYear(), foodGroup, animalGroup, waterGroup, this);
        } while(overlapsAnything(a.getImage()));
        a.setAnimalList(animalList);
        a.setFoodList(foodList);
        a.setShelterList(shelterList);
        a.setWaterList(waterList);
        a.setObstacleList(obstacleList);
        a.setAnimalSmellRef(animalSmellGroup);
        a.setAnimalStatsRef(animalStatsGroup);
        a.setAnimalLabelRef(animalLabelGroup);
        a.setAnimalTargetRef(animalTargetGroup);
        a.setAnimalHomeLocationRef(animalHomeLocationGroup);
        animalList.add(a);
        animalGroup.getChildren().add(a.getImage());
        animalSmellGroup.getChildren().add(a.getSmellCircle());
        animalTargetGroup.getChildren().add(a.getTargetLocation());
        animalStatsGroup.getChildren().add(a.getStatsBar().getGroup());
        animalHomeLocationGroup.getChildren().add(a.getHomeLocation());
        animalLabelGroup.getChildren().add(a.getText());
        trackAnimalID++;
    }

    public void addAnimal(int x, int y){
        Animal a = new Ant(x, y, trackAnimalID, getDay(), getYear(), foodGroup, animalGroup, waterGroup, this);
        a.setAnimalList(animalList);
        a.setFoodList(foodList);
        a.setShelterList(shelterList);
        a.setWaterList(waterList);
        a.setObstacleList(obstacleList);
        a.setAnimalSmellRef(animalSmellGroup);
        a.setAnimalStatsRef(animalStatsGroup);
        a.setAnimalLabelRef(animalLabelGroup);
        a.setAnimalTargetRef(animalTargetGroup);
        a.setAnimalHomeLocationRef(animalHomeLocationGroup);
        animalList.add(a);
        animalGroup.getChildren().add(a.getImage());
        animalSmellGroup.getChildren().add(a.getSmellCircle());
        animalTargetGroup.getChildren().add(a.getTargetLocation());
        animalStatsGroup.getChildren().add(a.getStatsBar().getGroup());
        animalHomeLocationGroup.getChildren().add(a.getHomeLocation());
        animalLabelGroup.getChildren().add(a.getText());
        trackAnimalID++;
    }

    public void addRandomFood(){
        //TODO: make random food rather than just meat
        Meat f;
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
            f = new Meat(x, y, trackFoodID);
        }while(overlapsAnything(f.getImage()));
        foodList.add(f);
        foodGroup.getChildren().add(f.getImage());
        trackFoodID++;
    }

    public void addFood(String type, int x, int y, int size){
        Meat f;
        if (type.equals("Ant")){
            f = new DeadAnt(x, y, trackFoodID, size);
        } else {
            f = new Meat(x, y, trackFoodID, size);
        }
        foodList.add(f);
        foodGroup.getChildren().add(f.getImage());
        trackFoodID++;
    }

    public void addRandomFoodTree(){
        int x, y;
        FoodTree f;
        do {
            f = new FoodTree(rand.nextInt(Main.SIZE_X), rand.nextInt(Main.SIZE_Y), foodList, foodGroup, waterList, foodTreeList);
        } while(overlapsAnything(f.getTreeTrunk().getImage()));
        foodTreeList.add(f);
        foodTreeLeafGroup.getChildren().add(f.getLeafCircle());
        foodTreeTrunkGroup.getChildren().add(f.getLeafCircle());

        obstacleList.add(f.getTreeTrunk());
        foodTreeTrunkGroup.getChildren().add(f.getTreeTrunk().getImage());
    }

    public void addFoodTree(int x, int y){
        FoodTree f = new FoodTree(x, y, foodList, foodGroup, waterList, foodTreeList);
        foodTreeList.add(f);
        foodTreeLeafGroup.getChildren().add(f.getLeafCircle());
        obstacleList.add(f.getTreeTrunk());
        foodTreeTrunkGroup.getChildren().add(f.getTreeTrunk().getImage());
    }

    public void addRandomPool(){
        int waterCount = rand.nextInt(5) + 2;
        // add one water to base other water positions on
        waterList.add(new Water(rand.nextInt(Main.SIZE_X), rand.nextInt(Main.SIZE_Y)));
        int PSize = waterList.size();
        waterGroup.getChildren().add(waterList.get(PSize - 1).getCircle());
        addWaterHazard( waterList.get(PSize - 1).getX(),
                        waterList.get(PSize - 1).getY(),
                        waterList.get(PSize - 1).getSize() - 10);

        int angleDeg = rand.nextInt(360);

        for(int i = 0; i < waterCount; i++){
            int attempt = 0, newX, newY;
            Water w;
            do {
                if (attempt > 0){
                    angleDeg = rand.nextInt(360);
                } else attempt ++;

                double angleRad = Math.toRadians(angleDeg);
                newX = (int) (  waterList.get(i + PSize - 1).getCircle().getCenterX() +
                                waterList.get(i + PSize - 1).getCircle().getRadius() *
                                Math.cos(angleRad));
                newY = (int) (  waterList.get(i + PSize - 1).getCircle().getCenterY() +
                                waterList.get(i + PSize - 1).getCircle().getRadius() *
                                Math.sin(angleRad));
                w = new Water(newX, newY);
            } while(newX > Main.SIZE_X || newX < 0 || newY > Main.SIZE_Y || newY < 0 || !overlapsAnything(w.getCircle()));
            waterList.add(w);
            addWaterHazard( waterList.get(i + PSize).getX(),
                            waterList.get(i + PSize).getY(),
                            waterList.get(i + PSize).getSize() - 10);

            angleDeg += rand.nextInt(120) - 60;

            if(angleDeg > 360){
                angleDeg -= 360;
            } else{
                if (angleDeg < 0){
                    angleDeg += 360;
                }
            }
            waterGroup.getChildren().add(waterList.get(i + PSize).getCircle());
        }
    }

    public void addRandomShelter(){
        // TODO: Make random shelters
        Shelter s;
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y);
            s = new AntHill(x, y, shelterID);
        } while(overlapsAnything(s.getImage()));
        shelterID++;
        shelterList.add(s);
        shelterGroup.getChildren().add(s.getImage());
        shelterStatsGroup.getChildren().add(s.getStatsBar().getGroup());
    }

    public void addRandomObstacle(){
        Obstacle o;
        do {
            o = new Rock(rand.nextInt(Main.SIZE_X), rand.nextInt(Main.SIZE_Y));
        } while(overlapsAnything(o.getImage()));
        obstacleList.add(o);
        obstacleGroup.getChildren().add(o.getImage());
    }

    public void addWaterHazard(int x, int y, int size){
        WaterHazard o = new WaterHazard(x, y, size);
        obstacleList.add(o);
        obstacleGroup.getChildren().add(o.getImage());
    }

    public boolean overlapsAnything(Circle c1){
        for(Animal animal : animalList){
            if (Collision.overlapsEfficient(c1, animal.getImage())){
                if (Collision.overlapsAccurate(c1, animal.getImage())) {
                    return true;
                }
            }
        }
        for(Obstacle obstacle : obstacleList){
            if (Collision.overlapsEfficient(c1, obstacle.getImage())){
                if (Collision.overlapsAccurate(c1, obstacle.getImage())) {
                    return true;
                }
            }
        }
        for(Food food : foodList){
            if (Collision.overlapsEfficient(c1, food.getImage())){
                if (Collision.overlapsAccurate(c1, food.getImage())) {
                    return true;
                }
            }
        }
        for(Water water : waterList){
            if (Collision.overlapsEfficient(c1, water.getCircle())){
                if (Collision.overlapsAccurate(c1, water.getCircle())) {
                    return true;
                }
            }
        }
        for(FoodTree foodTree : foodTreeList){
            if (Collision.overlapsEfficient(c1, foodTree.getTreeTrunk().getImage())){
                if (Collision.overlapsAccurate(c1, foodTree.getTreeTrunk().getImage())){
                    return true;
                }
            }
        }
        for(Shelter shelter : shelterList){
            if (Collision.overlapsEfficient(c1, shelter.getImage())){
                if (Collision.overlapsAccurate(c1, shelter.getImage())){
                    return true;
                }
            }
        }
        return false;
    }

    // remove from the world
    public void killAnimal(int i){
        addFood("Ant", (int)(animalList.get(i).getImage().getCenterX() + animalList.get(i).getImage().getTranslateX()),
                (int)(animalList.get(i).getImage().getCenterY() + animalList.get(i).getImage().getTranslateY()),
                (int)(animalList.get(i).getImage().getRadius()));
        animalRank.add(animalList.get(i));
        animalGroup.getChildren().remove(i);
        animalSmellGroup.getChildren().remove(i);
        animalTargetGroup.getChildren().remove(i);
        animalStatsGroup.getChildren().remove(i);
        animalLabelGroup.getChildren().remove(i);
        System.out.println("Animal " + animalList.get(i).getID() + " died");
        animalList.remove(i);
    }

    // get lists
    public ArrayList<Animal> getAnimalList(){
        return animalList;
    }
    public ArrayList<Food> getFoodList(){
        return foodList;
    }
    public ArrayList<Water> getWaterList(){
        return waterList;
    }
    public ArrayList<Shelter> getShelterList() {
        return shelterList;
    }
    public ArrayList<Obstacle> getObstacleList(){
        return obstacleList;
    }

    // run world
    public void update(){
        updateClock();
        updateStats();
        ageAnimals();

        // call update for all animals
        for (int i = 0; i < animalList.size(); i++) {
            animalList.get(i).update();
            for(int j = 0; j < animalList.size(); j++){
                if (animalList.get(j).getEnergy() <= 0) {
                    killAnimal(j);
                    j--;
                    i--;
                }
            }
        }

        // call update for all shelters
        for (int i = 0; i < shelterList.size(); i++){
            shelterList.get(i).update();
        }

        // call update for all food trees
        for (FoodTree foodTree : foodTreeList){
            foodTree.update();
        }

        // call update for all food
        for (Food food: foodList){
            food.update();
        }
    }

    public void updateClock(){
        setDayLengthCounter(getDayLengthCounter() + 1);
        if (getDayLengthCounter() >= getDayLength()){
            setDayLengthCounter(0);
            setDay(getDay() + 1);
            getWorldStatsBar().setDateString(getDateString());
            if (getDay() > 365){
                setDay(0);
                setYear(getYear() + 1);
            }
        }
    }

    public void ageAnimals(){
        for(Animal animal : getAnimalList()){
            animal.solveAge(getYear(), getDay());
        }
    }

    public void updateStats(){
        getWorldStatsBar().setAnimalCountString(getAnimalList().size());
        getWorldStatsBar().setFoodCountString(getFoodList().size());
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
        animalStatsGroup.setVisible(!animalStatsGroup.isVisible());
    }

    public void toggleHomeSquares(){
        animalHomeLocationGroup.setVisible(!animalHomeLocationGroup.isVisible());
    }

    public void toggleShelterStatBars(){
        shelterStatsGroup.setVisible(!shelterStatsGroup.isVisible());
    }

    public void toggleAnimalLabels(){
        animalLabelGroup.setVisible(!animalLabelGroup.isVisible());
    }

    public void toggleAnimals(){
        animalGroup.setVisible(!animalGroup.isVisible());
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDayLength() {
        return dayLength;
    }

    public void setDayLength(int dayLength) {
        this.dayLength = dayLength;
    }

    public int getDayLengthCounter() {
        return dayLengthCounter;
    }

    public void setDayLengthCounter(int dayLengthCounter) {
        this.dayLengthCounter = dayLengthCounter;
    }

    public String getDateString(){
        return ("Y" + getYear() + " D" + getDay());
    }

    public WorldStatsBar getWorldStatsBar() {
        return worldStatsBar;
    }

    public void setWorldStatsBar(WorldStatsBar worldStatsBar) {
        this.worldStatsBar = worldStatsBar;
    }
}
