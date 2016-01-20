package LS;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

/**
 * World provides a container in which various entities interact with one another and holds functions
 * to generate Animals, Water, Food and Obstacles
 */
public class World {
    private Random rand = new Random();
    private Configuration configuration;
    private int year, day, dayLength, dayLengthCounter;
    public static int trackFoodID = 0, trackAnimalID = 0;
    private int shelterID = 0;
    private ArrayList<Animal> animalList = new ArrayList<>();
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

    /**
     * Generation of the World object form a given Configuration
     * @param root The root node in which all other nodes should be added to
     * @param configuration The variable set that will define the properties of the World
     */
    public World(Group root, Configuration configuration){
        // Set attributes
        trackAnimalID = 0;
        trackFoodID = 0;
        setDay(0);
        setYear(0);
        setDayLength(10);
        setDayLengthCounter(0);
        setConfiguration(configuration);

        // Add all group elements to the root
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

        // Add the relative element dependent on the count provided by the Configuration
        for(int i = 0; i < configuration.getPoolCount(); i++){
            addRandomPool();
        }
        for(int i = 0; i < configuration.getFoodTrees(); i++){
            addRandomFoodTree();
        }
        for(int i = 0; i < configuration.getAntHillCount(); i++){
            addRandomShelter("AntHill");
        }
        for(int i = 0; i < configuration.getRockShelterCount(); i++){
            addRandomShelter("RockShelter");
        }
        for(int i = 0; i < configuration.getCaves(); i++){
            addRandomShelter("Cave");
        }
        for(int i = 0; i < configuration.getNests(); i++){
            addRandomShelter("Nest");
        }
        for(int i = 0; i < configuration.getAnts(); i++) {
            addRandomAnimal("Ant");
        }
        for(int i = 0; i < configuration.getLizards(); i++){
            addRandomAnimal("Lizard");
        }
        for(int i = 0; i < configuration.getBears(); i++){
            addRandomAnimal("Bear");
        }
        for(int i = 0; i < configuration.getEagles(); i++){
            addRandomAnimal("Eagle");
        }
        for(int i = 0; i < configuration.getFoodCount(); i++){
            addRandomFood();
        }
        for(int i = 0; i < configuration.getObstacleCount(); i++){
            addRandomObstacle();
        }
    }

    /**
     * Add an animal in a random position within the world
     * @param type the type of Animal wanted i.e. "Ant" or "Lizard"
     */
    public void addRandomAnimal(String type){
        // Define an Animal
        Animal a;
        // Confirm the type of animal and generate an animal of the correct type
        if (type.equals("Ant")) {
            // A do while loop of which its terminating condition is that that it was generated in a valid place
            do {
                int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y) + 25;
                a = new Ant(x, y, trackAnimalID, getDay(), getYear(), foodGroup, animalGroup, waterGroup, this,
                        animalList, foodList, waterList, obstacleList, shelterList, animalSmellGroup, animalStatsGroup,
                        animalLabelGroup, animalTargetGroup, animalHomeLocationGroup, getConfiguration());
            } while (overlapsAnything(a.getImage()));
            a.addSelfToLists();
        } else {
            if (type.equals("Lizard")) {
                // A do while loop of which its terminating condition is that that it was generated in a valid place
                do {
                    int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y) + 25;
                    a = new Lizard(x, y, trackAnimalID, getDay(), getYear(), foodGroup, animalGroup, waterGroup, this,
                            animalList, foodList, waterList, obstacleList, shelterList, animalSmellGroup, animalStatsGroup,
                            animalLabelGroup, animalTargetGroup, animalHomeLocationGroup, getConfiguration());
                } while (overlapsAnything(a.getImage()));
                a.addSelfToLists();
            } else {
                if (type.equals("Bear")){
                    // A do while loop of which its terminating condition is that that it was generated in a valid place
                    do {
                        int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y) + 25;
                        a = new Bear(x, y, trackAnimalID, getDay(), getYear(), foodGroup, animalGroup, waterGroup, this,
                                animalList, foodList, waterList, obstacleList, shelterList, animalSmellGroup, animalStatsGroup,
                                animalLabelGroup, animalTargetGroup, animalHomeLocationGroup, getConfiguration());
                    } while (overlapsAnything(a.getImage()));
                    a.addSelfToLists();
                }else {
                    // A do while loop of which its terminating condition is that that it was generated in a valid place
                    if (type.equals("Eagle")){
                        do {
                            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y) + 25;
                            a = new Eagle(x, y, trackAnimalID, getDay(), getYear(), foodGroup, animalGroup, waterGroup, this,
                                    animalList, foodList, waterList, obstacleList, shelterList, animalSmellGroup, animalStatsGroup,
                                    animalLabelGroup, animalTargetGroup, animalHomeLocationGroup, getConfiguration());
                        } while (overlapsAnything(a.getImage()));
                        a.addSelfToLists();
                    }
                }
            }
        }
        trackAnimalID++;
    }

    /**
     * Add an animal with a given set of attributes
     * @param type The type of animal wanted i.e. "Ant" or "Lizard"
     * @param x X coordinate
     * @param y y Coordinate
     * @param gender Gender of the Animal
     * @param name Name fo the Animal
     * @param speed Speed at which the Animal can move
     * @param metabolism The rate at which hunger and thirst will grow
     * @param strength Strength of the Animal used to determine the winner of fights and size of an
     *                 Animal's inventory space
     * @param smell The range at which the Animal can perceive its world
     * @param size Body size of the Animal
     */
    public void addAnimal(String type, int x, int y, char gender, String name, double speed, float metabolism, int strength, int smell, int size){
        Animal a = null;
        // Check the type of Animal wanted to be created and create the relevant object passing all attributes
        if (type.equals("Ant")){
            a = new Ant(x, y, gender, name, speed, metabolism, strength, smell, size, trackAnimalID, getDay(), getYear(),
                    foodGroup, animalGroup, waterGroup, this, animalList, foodList, waterList, obstacleList, shelterList,
                    animalSmellGroup, animalStatsGroup, animalLabelGroup, animalTargetGroup, animalHomeLocationGroup,
                    getConfiguration());
        }
        else {
            if (type.equals("Lizard")){
                a = new Lizard(x, y, gender, name, speed, metabolism, strength, smell, size, trackAnimalID, getDay(), getYear(),
                        foodGroup, animalGroup, waterGroup, this, animalList, foodList, waterList, obstacleList, shelterList,
                        animalSmellGroup, animalStatsGroup, animalLabelGroup, animalTargetGroup, animalHomeLocationGroup,
                        getConfiguration());
            } else {
                if (type.equals("Bear")){
                    a = new Bear(x, y, gender, name, speed, metabolism, strength, smell, size, trackAnimalID, getDay(), getYear(),
                            foodGroup, animalGroup, waterGroup, this, animalList, foodList, waterList, obstacleList, shelterList,
                            animalSmellGroup, animalStatsGroup, animalLabelGroup, animalTargetGroup, animalHomeLocationGroup,
                            getConfiguration());
                } else {
                    if (type.equals("Eagle")){
                        a = new Eagle(x, y, gender, name, speed, metabolism, strength, smell, size, trackAnimalID, getDay(), getYear(),
                                foodGroup, animalGroup, waterGroup, this, animalList, foodList, waterList, obstacleList, shelterList,
                                animalSmellGroup, animalStatsGroup, animalLabelGroup, animalTargetGroup, animalHomeLocationGroup,
                                getConfiguration());
                    }
                }
            }
        }
        a.addSelfToLists();
    }

    /**
     * Add a Food object of type "Meat" in a random location within the world
     */
    public void addRandomFood(){
        Food f;
        // A do while loop of which its terminating condition is that that it was generated in a valid place
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y) + 25;
            f = new Food("Meat", x, y, trackFoodID, rand.nextInt(4) + 3, Color.rgb(0, 0, 0));
        }while(overlapsAnything(f.getImage()));
        // Add food to all lists
        foodList.add(f);
        foodGroup.getChildren().add(f.getImage());
        trackFoodID++;
    }

    /**
     * Add a specific type of Food to the World with specific coordinates and size
     * @param type The type of food to be created i.e. "Fruit", "Ant"
     * @param x X coordinate
     * @param y Y coordinate
     * @param size Size of the food object
     */
    public void addFood(String type, int x, int y, int size){
        Food f;
        // create a food object of the type relevant to the animal that was passed
        if (type.equals("Ant")){
            f = new Food(type, x, y, trackFoodID, size, Color.rgb(50, 50, 50));
        } else {
            if (type.equals("Lizard")) {
                f = new Food(type, x, y, trackFoodID, size, Color.rgb(0, 150, 40));
            } else {
                if (type.equals("Bear")) {
                    f = new Food(type, x, y, trackFoodID, size, Color.rgb(200, 100, 0));
                } else {
                    if (type.equals("Eagle")){
                        f = new Food(type, x, y, trackFoodID, size, Color.rgb(200, 200, 200));
                    }
                    else{
                        f = new Food(type, x, y, trackFoodID, size, Color.rgb(0, 0, 0));
                    }
                }
            }
        }
        // Add food to all lists
        foodList.add(f);
        foodGroup.getChildren().add(f.getImage());
        trackFoodID++;
    }

    /**
     * Add a food generating tree in a random location within the World
     */
    public void addRandomFoodTree(){
        FoodTree f;
        // A do while loop of which its terminating condition is that that it was generated in a valid place
        do {
            f = new FoodTree(rand.nextInt(Main.SIZE_X), rand.nextInt(Main.SIZE_Y) + 25, foodList, foodGroup, waterList, foodTreeList);
        } while(overlapsAnything(f.getTreeTrunk().getImage()));
        // Add food tree elements to all lists
        foodTreeList.add(f);
        foodTreeLeafGroup.getChildren().add(f.getLeafCircle());
        foodTreeTrunkGroup.getChildren().add(f.getLeafCircle());

        obstacleList.add(f.getTreeTrunk());
        foodTreeTrunkGroup.getChildren().add(f.getTreeTrunk().getImage());
    }

    /**
     * Add a pool(A collection of Water objects) at a random location within the world
     */
    public void addRandomPool(){
        int waterCount = rand.nextInt(5) + 2;
        // Add one water to base other water positions on
        waterList.add(new Water(rand.nextInt(Main.SIZE_X), rand.nextInt(Main.SIZE_Y) + 25));
        int PSize = waterList.size();
        // Add the first water element to the group
        waterGroup.getChildren().add(waterList.get(PSize - 1).getCircle());
        // Add a water hazard in the same place but slightly smaller
        addWaterHazard( waterList.get(PSize - 1).getX(),
                        waterList.get(PSize - 1).getY(),
                        waterList.get(PSize - 1).getSize() - 10);

        // Create a random angle to create the next circle
        int angleDeg = rand.nextInt(360);

        // loop for the amount of water to add
        for(int i = 0; i < waterCount; i++){
            int attempt = 0, newX, newY;
            Water w;
            // A do while loop of which its terminating condition is that that it was generated in a valid place
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
            } while(newX > Main.SIZE_X || newX < 0 || newY > Main.SIZE_Y || newY < 25 || !overlapsAnything(w.getCircle()));
            waterList.add(w);
            // Add the water hazard over the newly created pool element
            addWaterHazard( waterList.get(i + PSize).getX(),
                            waterList.get(i + PSize).getY(),
                            waterList.get(i + PSize).getSize() - 10);

            // Find an angle within 120 degrees to create the next water
            angleDeg += rand.nextInt(120) - 60;

            // truncate the angle
            if(angleDeg > 360){
                angleDeg -= 360;
            } else{
                if (angleDeg < 0){
                    angleDeg += 360;
                }
            }
            // add the water to the water list
            waterGroup.getChildren().add(waterList.get(i + PSize).getCircle());
        }
    }

    /**
     * Add a Shelter at a random location within the World
     * @param type The type of Shelter to be created i.e. "AntHill" or "Nest"
     */
    public void addRandomShelter(String type){
        Shelter s;
        // Add a shelter determined by the type passed to it
        // A do while loop of which its terminating condition is that that it was generated in a valid place
        do {
            int x = rand.nextInt(Main.SIZE_X), y = rand.nextInt(Main.SIZE_Y) + 25;
            if (type.equals("AntHill")) {
                s = new Shelter(type, x, y, 200, 10, shelterID, Color.rgb(220, 200, 160));
            } else {
                if (type.equals("RockShelter")) {
                    s = new Shelter(type, x, y, 200, 10, shelterID, Color.rgb(100, 100, 10));
                } else {
                    if (type.equals("Cave")) {
                        s = new Shelter(type, x, y, 200, 10, shelterID, Color.rgb(150, 150, 170));
                    } else {
                        if (type.equals("Nest")) {
                            s = new Shelter(type, x, y, 200, 10, shelterID, Color.rgb(130, 100, 60));
                        }
                        else{
                            System.out.println("Error creating shelter");
                            return;
                        }
                    }
                }
            }
        } while(overlapsAnything(s.getImage()));
        // Add the shelter to the lists
        shelterID++;
        shelterList.add(s);
        shelterGroup.getChildren().add(s.getImage());
        shelterStatsGroup.getChildren().add(s.getStatsBar().getGroup());
    }

    /**
     * Add an Obstacle in a random location within the World
     */
    public void addRandomObstacle(){
        Obstacle o;
        // A do while loop of which its terminating condition is that that it was generated in a valid place
        do {
            o = new Obstacle("Rock", rand.nextInt(Main.SIZE_X), rand.nextInt(Main.SIZE_Y) + 25, rand.nextInt(10) + 10, Color.rgb(150, 150, 150));
        } while(overlapsAnything(o.getImage()));
        obstacleList.add(o);
        obstacleGroup.getChildren().add(o.getImage());
    }

    /**
     * Add a Water object at a specified location within the World
     * @param x X coordinate
     * @param y Y coordinate
     * @param size The size of the Water object
     */
    public void addWaterHazard(int x, int y, int size){
        // Create a WaterHazard in the desired location
        Obstacle o = new Obstacle("WaterHazard", x, y, size, Color.rgb(50, 50, 200));
        // Add it to the lists
        obstacleList.add(o);
        obstacleGroup.getChildren().add(o.getImage());
    }

    /**
     * Check if a Circle overlaps any other Circles within the World
     * @param c1 The Circle to test
     * @return if the Circle passed collides with any other Circle
     */
    public boolean overlapsAnything(Circle c1){
        // Loop for all animals
        for(Animal animal : animalList){
            if (Collision.overlapsEfficient(c1, animal.getImage())){
                if (Collision.overlapsAccurate(c1, animal.getImage())) {
                    // Return  true if a collision was recorded
                    return true;
                }
            }
        }
        // Loop for all obstacles
        for(Obstacle obstacle : obstacleList){
            if (Collision.overlapsEfficient(c1, obstacle.getImage())){
                if (Collision.overlapsAccurate(c1, obstacle.getImage())) {
                    // Return  true if a collision was recorded
                    return true;
                }
            }
        }
        // Loop for all food
        for(Food food : foodList){
            if (Collision.overlapsEfficient(c1, food.getImage())){
                if (Collision.overlapsAccurate(c1, food.getImage())) {
                    // Return  true if a collision was recorded
                    return true;
                }
            }
        }
        // Loop for all water
        for(Water water : waterList){
            if (Collision.overlapsEfficient(c1, water.getCircle())){
                if (Collision.overlapsAccurate(c1, water.getCircle())) {
                    // Return  true if a collision was recorded
                    return true;
                }
            }
        }
        // loop for all foodTrees
        for(FoodTree foodTree : foodTreeList){
            if (Collision.overlapsEfficient(c1, foodTree.getTreeTrunk().getImage())){
                if (Collision.overlapsAccurate(c1, foodTree.getTreeTrunk().getImage())){
                    // Return  true if a collision was recorded
                    return true;
                }
            }
        }
        // Loop for all shelters
        for(Shelter shelter : shelterList){
            if (Collision.overlapsEfficient(c1, shelter.getImage())){
                if (Collision.overlapsAccurate(c1, shelter.getImage())){
                    // Return  true if a collision was recorded
                    return true;
                }
            }
        }
        // Return false if no collision was made
        return false;
    }

    /**
     * Kill a specified Animal and remove it from the World, creating a food of the same type in its place
     * @param i index of the Animal to kill
     */
    public void killAnimal(int i){
        // Add a food element with the relevant type in the same location as the Animal
        addFood(getAnimalList().get(i).getSpecies(), (int) (animalList.get(i).getImage().getCenterX() + animalList.get(i).getImage().getTranslateX()),
                (int) (animalList.get(i).getImage().getCenterY() + animalList.get(i).getImage().getTranslateY()),
                (int) (animalList.get(i).getImage().getRadius()));

        // Remove the animal from all lists and groups
        animalGroup.getChildren().remove(i);
        animalSmellGroup.getChildren().remove(i);
        animalTargetGroup.getChildren().remove(i);
        animalStatsGroup.getChildren().remove(i);
        animalLabelGroup.getChildren().remove(i);
        animalList.remove(i);
    }

    /**
     * Update the World, calling the update for all elements within the World
     */
    public void update(){
        // Update the world clock and update the Animal ages
        updateClock();
        ageAnimals();

        // call update for all animals
        for (int i = 0; i < animalList.size(); i++) {
            // Update each Animal
            animalList.get(i).update();
            for(int j = 0; j < animalList.size(); j++){
                // Check all animal health to in case a fight has killed an animal
                if (animalList.get(j).getEnergy() <= 0) {
                    // Step back in the update ensuring no animal was skipped as the list moves back
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

    /**
     * Update and increment the date of the World
     */
    public void updateClock(){
        // Increment the day length counter
        setDayLengthCounter(getDayLengthCounter() + 1);
        // If a day has passed
        if (getDayLengthCounter() >= getDayLength()){
            // Reset the day length counter
            setDayLengthCounter(0);
            // Increment the day counter
            setDay(getDay() + 1);
            // if 365 days have passed, increment the year counter
            if (getDay() > 365){
                setDay(0);
                setYear(getYear() + 1);
            }
        }
    }

    /**
     * Age all animals solving the new age using the World's current date
     */
    public void ageAnimals() {
        // Call the update to age all animals
        for(Animal animal : getAnimalList()){
            animal.solveAge(getYear(), getDay());
        }
    }

    public void toggleSmellCircles(){
        // Set the visibility of the group to the opposite of the group visibility
        animalSmellGroup.setVisible(!animalSmellGroup.isVisible());
    }

    public void toggleTargetSquares(){
        // Set the visibility of the group to the opposite of the group visibility
        animalTargetGroup.setVisible(!animalTargetGroup.isVisible());
    }

    public void toggleStatBars(){
        // Set the visibility of the group to the opposite of the group visibility
        animalStatsGroup.setVisible(!animalStatsGroup.isVisible());
    }

    public void toggleHomeSquares(){
        // Set the visibility of the group to the opposite of the group visibility
        animalHomeLocationGroup.setVisible(!animalHomeLocationGroup.isVisible());
    }

    public void toggleShelterStatBars(){
        // Set the visibility of the group to the opposite of the group visibility
        shelterStatsGroup.setVisible(!shelterStatsGroup.isVisible());
    }

    public void toggleAnimalLabels(){
        // Set the visibility of the group to the opposite of the group visibility
        animalLabelGroup.setVisible(!animalLabelGroup.isVisible());
    }

    public void toggleAnimals(){
        // Set the visibility of the group to the opposite of the group visibility
        animalGroup.setVisible(!animalGroup.isVisible());
    }

    public ArrayList<Animal> getAnimalList(){
        return animalList;
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

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
