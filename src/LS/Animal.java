package LS;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Animal provides an entity with the capability to navigate a world of objects deciding how to best
 * keep them self alive. Animal is an abstract class and can be instantiated only through the use of the derived
 * classes Ant, Bear, Eagle and Lizard.
 */
public abstract class Animal {
    private Group foodGroupRef, waterGroupRef, animalGroupRef, animalSmellRef, animalStatsRef, animalLabelRef,
            animalTargetRef, animalHomeLocationRef;
    private float size, metabolism, hunger = 0, thirst = 0;
    private int id, x, y, energy, maxEnergy, smellRange, turnAngle, pathDistance, foodSearchCoolDown,
            followMainCoolDown, waitAtHome, homeID, breedTimer, lastAngle = new Random().nextInt(360),
            targetFoodID, poisonTime, ageYear, ageDay, dayBorn, yearBorn, maxAge, speedChangeAge, breedAge,
            lastAge, strength;
    private boolean localTargetBool, mainTargetBool, targetingFood, targetingWater, targetingHome,
            targetingAnimal, poisoned, inShelter, shouldBreed;
    private double speed, originalSpeed, dx, dy;private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();
    private ArrayList<Water> waterList = new ArrayList<>();
    private ArrayList<Shelter> shelterList = new ArrayList<>();
    private ArrayList<Obstacle> obstacleList = new ArrayList<>();
    public Inventory foodInventory, waterInventory;
    private Target localTarget, mainTarget, homeTarget;
    private World worldRef;
    private Configuration configuration;
    private Circle image, smellCircle;
    private Text text;
    private StatsBar statsBar;
    private Rectangle targetLocation, homeLocation;
    private String species, name;
    private char symbol, gender;

    /**
     * Called as a super constructor from all child classes. The parameters assume that other Animal traits
     * are set within the child class constructor.
     *
     * @param speciesIn Species of the Animal
     * @param symbolIn Symbol of the Animal, typically the first character of the species
     * @param IDIn A unique ID of the Animal
     * @param dayBorn The day (0 - 365) in which the Animal was born
     * @param yearBorn The year in which the Animal was born
     * @param energyIn The stating and max energy of the Animal
     * @param xIn X coordinate
     * @param yIn Y coordinate
     * @param food Food group reference, a node of the root node
     * @param animal Animal group reference, a node of the root node
     * @param water Water group reference, a node of the root node
     * @param worldRef World object in which the Animal lives
     * @param animalList List of all Animals also living in the world
     * @param foodList List of all Food within the world
     * @param waterList List of all Water within the world
     * @param obstacleList List of all Obstacles within the world
     * @param shelterList List of all Shelters within the world
     * @param animalSmellRef Animal smellCircle group reference, a node of the root node
     * @param animalStatsRef Animal statBar group reference, a node of the root node
     * @param animalLabelRef Animal label group reference, a node of the root node
     * @param animalTargetRef Animal target group reference, a node of the root node
     * @param animalHomeLocationRef Animal home group reference, a node of the root node
     * @param configuration Configuration in which the animal follows hunting and eating rules of
     */
    public Animal(String speciesIn, char symbolIn, int IDIn, int dayBorn, int yearBorn, int energyIn, int xIn, int yIn,
                  Group food, Group animal, Group water, World worldRef, ArrayList<Animal> animalList,
                  ArrayList<Food> foodList, ArrayList<Water> waterList, ArrayList<Obstacle> obstacleList,
                  ArrayList<Shelter> shelterList, Group animalSmellRef, Group animalStatsRef, Group animalLabelRef,
                  Group animalTargetRef, Group animalHomeLocationRef, Configuration configuration){

        setSpecies(speciesIn);
        setSymbol(symbolIn);
        setID(IDIn);
        setEnergy(energyIn);
        setMaxEnergy(energyIn);
        setX(xIn);
        setY(yIn);
        setFoodGroupRef(food);
        setAnimalGroupRef(animal);
        setWaterGroupRef(water);
        setWorldRef(worldRef);
        setAnimalList(animalList);
        setFoodList(foodList);
        setWaterList(waterList);
        setObstacleList(obstacleList);
        setShelterList(shelterList);
        setAnimalSmellRef(animalSmellRef);
        setAnimalStatsRef(animalStatsRef);
        setAnimalLabelRef(animalLabelRef);
        setAnimalTargetRef(animalTargetRef);
        setAnimalHomeLocationRef(animalHomeLocationRef);
        setConfiguration(configuration);

        // Create stats bars
        setStatsBar(new StatsBar(x, y, 3));
        getStatsBar().getBar(0).setFill(Color.rgb(255, 100, 100));
        getStatsBar().getBar(1).setFill(Color.rgb(100, 100, 255));
        getStatsBar().getBar(2).setFill(Color.rgb(100, 255, 100));

        // Set a random gender
        giveGender();

        // Create target indicator
        Rectangle r = new Rectangle(0, 0, 5, 5);
        r.setFill(Color.rgb(255, 0, 0));
        setTargetLocation(r);

        // Create home locator
        Rectangle h = new Rectangle(0, 0, 5, 5);
        h.setFill(Color.rgb(0, 0, 255));
        setHomeLocation(h);

        // set a food cool down value
        setFoodSearchCoolDown(0);
        setFollowMainCoolDown(0);

        // Create label
        setText(new Text(getID() + ""));
        getText().setTranslateX(getX());
        getText().setTranslateY(getY());
        getText().setFont(Font.font ("Verdana", 12));
        getText().setFill(Color.rgb(200, 0, 0));

        // Set shelter variables
        setInShelter(false);
        setWaitAtHome(0);

        // Set poison variables
        setPoisoned(false);
        setPoisonTime(0);

        // Set age
        setLastAge(0);
        setYearBorn(yearBorn);
        setDayBorn(dayBorn);

        // Set targeting animal default
        setTargetingAnimal(false);

        setFollowMainCoolDown(10000 + new Random().nextInt(5000));
    }

    /**
     * Called as a super constructor from all child classes. Used to create an animal of specific attributes,
     * typically called by the createBaby function within all child classes
     *
     * @param speciesIn Species of the Animal
     * @param symbolIn Symbol of the Animal, typically the first character of the species
     * @param IDIn A unique ID of the Animal
     * @param dayBorn The day (0 - 365) in which the Animal was born
     * @param yearBorn The year in which the Animal was born
     * @param energyIn The stating and max energy of the Animal
     * @param xIn X coordinate
     * @param yIn Y coordinate
     * @param gender Gender of an Animal 'M' or 'F'
     * @param name Name of the Animal
     * @param speed Speed at which the Animal can move
     * @param metabolism Rate at which the Animals hunger and thirst grow
     * @param strength Strength of the Animal used to determine the winner of fights and size of an
     *                 Animal's inventory space
     * @param smell The range at which the Animal can perceive its world
     * @param size Body size of the Animal
     * @param food Food group reference, a node of the root node
     * @param animal Animal group reference, a node of the root node
     * @param water Water group reference, a node of the root node
     * @param worldRef World object in which the Animal lives
     * @param animalList List of all Animals also living in the world
     * @param foodList List of all Food within the world
     * @param waterList List of all Water within the world
     * @param obstacleList List of all Obstacles within the world
     * @param shelterList List of all Shelters within the world
     * @param animalSmellRef Animal smellCircle group reference, a node of the root node
     * @param animalStatsRef Animal statBar group reference, a node of the root node
     * @param animalLabelRef Animal label group reference, a node of the root node
     * @param animalTargetRef Animal target group reference, a node of the root node
     * @param animalHomeLocationRef Animal home group reference, a node of the root node
     * @param colour Color variable of the Animals body circle
     */
    public Animal(String speciesIn, char symbolIn, int IDIn, int dayBorn, int yearBorn, int energyIn, int xIn, int yIn,
                  char gender, String name, double speed, float metabolism, int strength, int smell, int size,
                  Group food, Group animal, Group water, World worldRef, ArrayList<Animal> animalList,
                  ArrayList<Food> foodList, ArrayList<Water> waterList, ArrayList<Obstacle> obstacleList,
                  ArrayList<Shelter> shelterList, Group animalSmellRef, Group animalStatsRef, Group animalLabelRef,
                  Group animalTargetRef, Group animalHomeLocationRef, Configuration configuration, Color colour){

        // Set all attributes
        setSpecies(speciesIn);
        setSymbol(symbolIn);
        setID(IDIn);
        setAgeDay(dayBorn);
        setAgeYear(yearBorn);
        setLastAge(yearBorn);
        setEnergy(energyIn);
        setMaxEnergy(energyIn);
        setX(xIn);
        setY(yIn);
        setGender(gender);
        setName(name);
        setSpeed(speed);
        setOriginalSpeed(getSpeed());
        setMetabolism(metabolism);
        setStrength(strength);
        setSmellRange(smell);
        setSize(size);
        setFoodGroupRef(food);
        setAnimalGroupRef(animal);
        setWaterGroupRef(water);
        setWorldRef(worldRef);
        setAnimalList(animalList);
        setFoodList(foodList);
        setWaterList(waterList);
        setObstacleList(obstacleList);
        setShelterList(shelterList);
        setAnimalSmellRef(animalSmellRef);
        setAnimalStatsRef(animalStatsRef);
        setAnimalLabelRef(animalLabelRef);
        setAnimalTargetRef(animalTargetRef);
        setAnimalHomeLocationRef(animalHomeLocationRef);
        setConfiguration(configuration);

        // Create smell attributes
        setSmellCircle(new Circle(x, y, getSmellRange()));
        getSmellCircle().setFill(Color.rgb(0, 100, 100));
        getSmellCircle().setOpacity(0.3);
        setPathDistance(getSmellRange());

        // Create body attributes
        setImage(new Circle(x, y, getSize()));
        getImage().setFill(colour);

        // Set a random turning angle
        setTurnAngle(40);

        // Create food inventory
        setFoodInventory(new Inventory(getStrength(), getStrength()));

        // Create water inventory
        setWaterInventory(new Inventory(getStrength(), getStrength()));


        // Create stats bars
        setStatsBar(new StatsBar(x, y, 3));
        getStatsBar().getBar(0).setFill(Color.rgb(255, 100, 100));
        getStatsBar().getBar(1).setFill(Color.rgb(100, 100, 255));
        getStatsBar().getBar(2).setFill(Color.rgb(100, 255, 100));

        // Create target indicator
        Rectangle r = new Rectangle(0, 0, 5, 5);
        r.setFill(Color.rgb(255, 0, 0));
        setTargetLocation(r);

        // Create home locator
        Rectangle h = new Rectangle(0, 0, 5, 5);
        h.setFill(Color.rgb(0, 0, 255));
        setHomeLocation(h);

        // set a food cool down value
        setFoodSearchCoolDown(0);
        setFollowMainCoolDown(0);

        // Create label
        setText(new Text(getID() + ""));
        getText().setTranslateX(getX());
        getText().setTranslateY(getY());
        getText().setFont(Font.font ("Verdana", 10));
        getText().setFill(Color.rgb(200, 0, 0));

        // Set shelter variables
        setInShelter(false);
        setWaitAtHome(0);

        // Set poison variables
        setPoisoned(false);
        setPoisonTime(0);

        // Set targeting animal default
        setTargetingAnimal(false);

        setFollowMainCoolDown(10000 + new Random().nextInt(5000));
    }

    /**
     * Adds the Animal to all lists needed to function and be visible within the world
     */
    public void addSelfToLists(){
        animalList.add(this);
        getAnimalGroupRef().getChildren().add(getImage());
        getAnimalSmellRef().getChildren().add(getSmellCircle());
        getAnimalTargetRef().getChildren().add(getTargetLocation());
        getAnimalStatsRef().getChildren().add(getStatsBar().getGroup());
        getAnimalHomeLocationRef().getChildren().add(getHomeLocation());
        getAnimalLabelRef().getChildren().add(getText());
    }

    /**
     * Updates the Animal between keyFrames, calling all functions needed to appear to live within the world
     */
    public void update(){
        if(isInShelter()){
            setWaitAtHome(getWaitAtHome() - 1);
            if (getWaitAtHome() <= 0){
                exitShelter();
            }
        }
        else {
            updateText();
            checkHungerThirst();
            target();
            directDxDy();
            move();
            hungerEnergyWaterDecay();
        }
        ageEvents();
    }

    /**
     * Triggers life events when a specific age (defined by the child class) is reached
     */
    public void ageEvents(){
        if (getLastAge() != getAgeYear()) {
            setLastAge(getAgeYear());
            if (getAgeYear() >= getBreedAge()) {
                setShouldBreed(true);
            }
            if (getAgeYear() >= getSpeedChangeAge()) {
                setSpeed(getOriginalSpeed() * 0.7);
            }
            if (getAgeYear() == getMaxAge()) {
                setMaxEnergy(getMaxEnergy()/4);
            }
        }
    }

    /**
     * Checks that eating/drinking water will not be wasteful, called on every update allowing
     * the chance to consume
     */
    public void checkHungerThirst(){
        if (foodInventory.getSize() > 0) {
            // check that eating food wont be wasteful
            if (getHunger() > (double)foodInventory.getElement(0)) {
                eatFood();
            }
        }
        if (waterInventory.getSize() > 0) {
            // check that drinking water wont be wasteful
            if (getThirst() > (double)waterInventory.getElement(0)){
                drinkWater();
            }
        }
    }


    /**
     * Moves all elements of the Animal relative to the current dx and dy stored only if the next
     * movement will not result in moving into an Obstacle, if dx and dy will lead to a collision with
     * an Obstacle the animal will pick a new target and ignore desired items for a set number of updates
     */
    public void move(){
        if (checkMove((int)(getImage().getTranslateX() + getImage().getCenterX() + (getDx())),
                (int)(getImage().getTranslateY() + getImage().getCenterY() + (getDy())))) {

            // body
            getImage().setTranslateX(getImage().getTranslateX() + getDx());
            getImage().setTranslateY(getImage().getTranslateY() + getDy());
            // smell
            getSmellCircle().setTranslateX(getSmellCircle().getTranslateX() + getDx());
            getSmellCircle().setTranslateY(getSmellCircle().getTranslateY() + getDy());
            // hunger bar
            getStatsBar().getBar(0).setTranslateX(getStatsBar().getBar(0).getTranslateX() + getDx());
            getStatsBar().getBar(0).setTranslateY(getStatsBar().getBar(0).getTranslateY() + getDy());
            // thirst bar
            getStatsBar().getBar(1).setTranslateX(getStatsBar().getBar(1).getTranslateX() + getDx());
            getStatsBar().getBar(1).setTranslateY(getStatsBar().getBar(1).getTranslateY() + getDy());
            // energy bar
            getStatsBar().getBar(2).setTranslateX(getStatsBar().getBar(2).getTranslateX() + getDx());
            getStatsBar().getBar(2).setTranslateY(getStatsBar().getBar(2).getTranslateY() + getDy());
            // back bar
            getStatsBar().getBackBar().setTranslateX(getStatsBar().getBackBar().getTranslateX() + getDx());
            getStatsBar().getBackBar().setTranslateY(getStatsBar().getBackBar().getTranslateY() + getDy());
            // label
            getText().setTranslateX(getText().getTranslateX() + getDx());
            getText().setTranslateY(getText().getTranslateY() + getDy());

            // move target indicator
            getTargetLocation().setTranslateX(getLocalTarget().getCircle().getCenterX() + getLocalTarget().getCircle().getTranslateX());
            getTargetLocation().setTranslateY(getLocalTarget().getCircle().getCenterY() + getLocalTarget().getCircle().getTranslateY());
        } else {
            // remove any local target bounds and pick a new one
            setTargetingFood(false);
            getRandomLocalTarget360();
            setFoodSearchCoolDown(50);
            setFollowMainCoolDown(50);
        }
    }

    /**
     * Provides the impression of growing hunger and thirst. Metabolism is used to determine the increment
     * of hunger and thirst, thirst grows at a rate * 0.3 faster than hunger. Stats bars are resized here as
     * new hunger, thirst and energy stats are generated
     */
    public void hungerEnergyWaterDecay(){
        // when poisoned the animal's hunger and thirst will rise twice as quickly
        if (isPoisoned()) {
            // add to hunger using metabolism higher metabolism means getting hungry quicker
            setHunger(getHunger() + (getMetabolism() * 2));
            // do the same but make thirst grow slightly faster
            setThirst(getThirst() + (float) ((getMetabolism() * 1.3) * 2));

            // let the poison wear off
            setPoisonTime(getPoisonTime() - 1);
            if (getPoisonTime() <= 0){
                setPoisonTime(0);
                setPoisoned(false);
            }
        }
        else {
            // add to hunger using metabolism higher metabolism means getting hungry quicker
            setHunger(getHunger() + getMetabolism());
            // do the same but make thirst grow slightly faster
            setThirst(getThirst() + (float) (getMetabolism() * 1.3));
        }

        // if both fields are satisfied add to energy
        if (getThirst() < 10 && getHunger() < 10 && getEnergy() < 1000){
            setEnergy(getEnergy() + 4);
        }

        // if hunger reaches max keep it there
        if (getHunger() >= 10){
            setHunger(10);
            setSpeed(getOriginalSpeed()/1.5);
            setEnergy(getEnergy() - 1);
        }
        // if hunger reaches 0 keep it that way
        else {
            setSpeed(getOriginalSpeed());
            if (getHunger() < 0) {
                setHunger(0);
            }
        }

        // if thirst reaches max, lose energy
        if (getThirst() >= 10){
            setThirst(10);
            setEnergy(getEnergy() - 2);
        }
        // if thirst reaches 0 keep it that way
        else if (getThirst() < 0){
            setThirst(0);
        }

        getStatsBar().getBar(0).setWidth(getHunger() * (getStatsBar().getStatBarWidth()/10));
        getStatsBar().getBar(1).setWidth(getThirst() * (getStatsBar().getStatBarWidth()/10));
        getStatsBar().getBar(2).setWidth(getEnergy() * (getStatsBar().getStatBarWidth()/(double)getMaxEnergy()));
    }

    /**
     * A targeting system used by the Animal to make choices in navigating its world. If conditions are met the
     * Animal can check he surrounding area for Food, Water or home, if none of such conditions are met the Animal
     * will choose a random target
     */
    public void target(){
        coolDownFood();
        coolDownFollowMain();
        coolDownBreedTimer();
        if (hasMainTarget()) {
            checkCollideMainTarget();
            if (hasLocalTarget()) {
                checkCollideLocalTarget();
            } else {
                createLocalTargetDirectedToMain();
            }
        } else {
            if (!hasLocalTarget()){
                getRandomLocalTarget();
            }

            if ((!isTargetFood() && !isTargetingAnimal()) && getFoodSearchCoolDown() == 0 && foodInventory.getSize() < foodInventory.getCapacity()) {
                checkFood();
            }
            if (!isTargetingWater() && waterInventory.getSize() < waterInventory.getCapacity()){
                checkWater();
            }
            if (homeTarget == null){
                checkShelters();
            }
            if (homeTarget != null && waterInventory.getSize() == waterInventory.getCapacity()
                    && foodInventory.getSize() == foodInventory.getCapacity() && getFoodSearchCoolDown() == 0
                    && getFollowMainCoolDown() == 0){
                targetHome();
            }
            if (homeTarget != null && isShouldBreed() && getBreedTimer() == 0  && getFoodSearchCoolDown() == 0
                    && getFollowMainCoolDown() == 0){
                targetHome();
            }
            if (homeTarget != null && getFollowMainCoolDown() == 0){
                if (waterInventory.getSize() == 0 && getThirst() == 10){
                    targetHome();
                    checkWater();
                }
                if (foodInventory.getSize() == 0 && getHunger() == 10  && getFoodSearchCoolDown() == 0 ) {
                    targetHome();
                    checkFood();
                }
            }
            if (hasLocalTarget()){
                checkCollideLocalTarget();
            }
        }
    }

    /**
     * Create a localTarget directed towards the mainTarget
     */
    public void createLocalTargetDirectedToMain(){
        double x = getMainTarget().getCircle().getCenterX() + getMainTarget().getCircle().getTranslateX();
        double y = getMainTarget().getCircle().getCenterY() + getMainTarget().getCircle().getTranslateY();
        double angle = getAngleTo(x, y);
        int tX = (int) ((getImage().getCenterX() + getImage().getTranslateX()) + getPathDistance() * Math.cos(angle));
        int tY = (int) ((getImage().getCenterY() + getImage().getTranslateY()) + getPathDistance() * Math.sin(angle));
        setLocalTarget(new Target(tX, tY));
        setLastAngle((int)Math.toDegrees(angle));
    }

    /**
     * Create a random localTarget along the smell range circumference within a given turn angle, if too
     * many attempts are made choose a target from all 360 degrees of direction
     */
    public void getRandomLocalTarget(){
        Random rand = new Random();
        int randomAttemptTracker = 0;
        int anAngle, tX, tY, path = getPathDistance();
        do{
            if (randomAttemptTracker < getTurnAngle() * 2){
                anAngle = rand.nextInt(getTurnAngle() * 2) + getLastAngle() - getTurnAngle();
                randomAttemptTracker++;
            } else{
                // Too many attempts at picking an angle within the turn range were made
                anAngle = rand.nextInt(360);
                path = rand.nextInt(getPathDistance());
            }
            double angle = Math.toRadians(anAngle);
            tX = (int) ((getImage().getCenterX() + getImage().getTranslateX()) + path * Math.cos(angle));
            tY = (int) ((getImage().getCenterY() + getImage().getTranslateY()) + path * Math.sin(angle));
        } while(!isValidTarget(tX, tY));
        setLocalTarget(new Target(tX, tY));
        setLastAngle(anAngle);
    }

    /**
     * Get a random target within the smell range circle
     */
    public void getRandomLocalTarget360(){
        Random rand = new Random();
        int tX, tY, anAngle;
        do {
            anAngle = rand.nextInt(360);
            double angleRad = Math.toRadians(anAngle);
            int path = rand.nextInt(getPathDistance());
            tX = (int) ((getImage().getCenterX() + getImage().getTranslateX()) + path * Math.cos(angleRad));
            tY = (int) ((getImage().getCenterY() + getImage().getTranslateY()) + path * Math.sin(angleRad));
        }while (!isValidTarget(tX, tY));
        setLocalTarget(new Target(tX, tY));
        setLastAngle(anAngle);
    }


    /**
     * Check if a given set of coordinates are within the screen boundaries
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return boolean true if the given coordinates are within the screen boundaries
     */
    public boolean isValidTarget(int x, int y){
        return (x < Main.SIZE_X && x >= 0 && y < Main.SIZE_Y && y >= 25);
    }

    /**
     * Gte the angle in radians of the Animals center point to the given coordintes
     * @param targetX X coordinate of the target
     * @param targetY Y coordinate of the target
     * @return Angle in radians directed towards the target
     */
    public double getAngleTo(double targetX, double targetY){
        double thisX = getImage().getCenterX() + getImage().getTranslateX();
        double thisY = getImage().getCenterY() + getImage().getTranslateY();
        return Math.atan2(targetY - thisY, targetX - thisX);
    }

    /**
     * Set dx and dy towards the localTarget
     */
    public void directDxDy(){
        double targetX = (getLocalTarget().getCircle().getCenterX() + getLocalTarget().getCircle().getTranslateX());
        double targetY = (getLocalTarget().getCircle().getCenterY() + getLocalTarget().getCircle().getTranslateY());
        double angle = getAngleTo(targetX, targetY);
        setDx((Math.cos(angle) * getSpeed()));
        setDy((Math.sin(angle) * getSpeed()));
    }

    /**
     * Check the Food seen has not been eaten by another animal
     * @return if the Food object is still available for consumption
     */
    public boolean foodIsStillThere(){
        for(Food food : getFoodList()){
            if (food.getID() == targetFoodID){
                return true;
            }
        }
        return false;
    }

    /**
     * Check the Animal seen has not been eaten by another animal
     * @return if the Animal object is still available for consumption
     */
    public boolean animalStillThere(){
        for(Animal animal : getAnimalList()){
            if (animal.getID() == targetFoodID){
                if (Collision.overlapsEfficient(animal.getImage(), getSmellCircle())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fight with the targeted Animal, killing it if this Animal's strength variable is greater
     */
    public void fightAnimal() {
        if (getEnergy() > 0) {
            for (Animal animal : animalList) {
                if (animal.getID() == getTargetFoodID()){
                    if (animal.getStrength() <= getStrength()) {
                        animal.setEnergy(-10);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Check if the Animal has reached its desired Target. Dependent on which type of target was set
     * other functions may be called, such as fightAnimal, storeFood or storeWater
     */
    public void checkCollideLocalTarget(){
        // check the food is still there to collide with
        if (isTargetFood()) {
            if (!foodIsStillThere()) {
                removeLocalTarget();
            }
        }
        if (isTargetingAnimal()){
            if (!animalStillThere()){
                removeLocalTarget();
            }
        }
        if (Collision.overlapsEfficient(getImage(), getLocalTarget().getCircle())) {
            if (Collision.overlapsAccurate(getImage(), getLocalTarget().getCircle())) {
                if (isTargetingAnimal()){
                    fightAnimal();
                } else {
                    if (isTargetFood()) {
                        storeFood();
                    } else {
                        if (isTargetingWater()) {
                            storeWater();
                        }
                    }
                }
                removeLocalTarget();
            }
        }
    }

    /**
     * Check if the Animal is colliding with the mainTarget set
     */
    public void checkCollideMainTarget(){
        if (Collision.overlapsEfficient(getImage(), getMainTarget().getCircle())) {
            if (Collision.overlapsAccurate(getImage(), getMainTarget().getCircle())) {
                if (isTargetingHome()) {
                    enterShelter();
                }
                removeMainTarget();
                removeLocalTarget();
                getRandomLocalTarget360();
            }
        }
    }

    /**
     * Check the area for Food or Animals to target Food is considered the preference as the Animal will not need
     * to exert energy in chasing and fighting a living Animal
     */
    public void checkFood(){
        for(Animal animal : getAnimalList()) {
            if (isInHuntList(animal.getSymbol())) {
                if (getID() != animal.getID()) {
                    if (Collision.overlapsEfficient(this.getSmellCircle(), animal.getImage())) {
                        if (Collision.overlapsAccurate(this.getSmellCircle(), animal.getImage())) {
                            setTargetingAnimal(true);
                            setTargetFoodID(animal.getID());
                            setLocalTarget(animal.getImage());
                            break;
                        }
                    }
                }
            }
        }
        for(Food food : getFoodList()){
            if (isInEatList(food.getType().charAt(0))) {
                if (Collision.overlapsEfficient(this.getSmellCircle(), food.getImage())) {
                    if (Collision.overlapsAccurate(this.getSmellCircle(), food.getImage())) {
                        setTargetingFood(true);
                        setTargetingAnimal(false);
                        setTargetFoodID(food.getID());
                        setLocalTarget(food.getImage());
                        return;
                    }
                }
            }
        }
    }


    /**
     * Checks if an animal is configured to eat a given Food type
     * @param type The first character of the Food type i.e. Fruit = 'F'
     * @return if the Animal should target the given Food
     */
    public boolean isInEatList(char type) {
        int indexX;
        int indexY;

        // Get index of animal to test
        if (type == 'A')
            indexX = 0;
        else if (type == 'L')
            indexX = 1;
        else if (type == 'B')
            indexX = 2;
        else if (type == 'E')
            indexX = 3;
        else if (type == 'F')
            indexX = 4;
        else return false;

        // Get index of self
        if (getSymbol() == 'A')
            indexY = 0;
        else if (getSymbol() == 'L')
            indexY = 1;
        else if (getSymbol() == 'B')
            indexY = 2;
        else if (getSymbol() == 'E')
            indexY = 3;
        else return false;

        return getConfiguration().getEatList().get(indexX).get(indexY);
    }

    /**
     * Checks if an animal is configured to hunt a given Animal type
     * @param type The first character of the Food type i.e. Ant = 'A'
     * @return if the Animal should target the given Animal
     */
    public boolean isInHuntList(char type) {
        int indexX;
        int indexY;

        // Get index of animal to test
        if (type == 'A')
            indexX = 0;
        else if (type == 'L')
            indexX = 1;
        else if (type == 'B')
            indexX = 2;
        else if (type == 'E')
            indexX = 3;
        else return false;

        // Get index of self
        if (getSymbol() == 'A')
            indexY = 0;
        else if (getSymbol() == 'L')
            indexY = 1;
        else if (getSymbol() == 'B')
            indexY = 2;
        else if (getSymbol() == 'E')
            indexY = 3;
        else return false;

        return getConfiguration().getHuntList().get(indexX).get(indexY);
    }

    /**
     * Consume Food from the Animal's inventory and remove the element from the inventory
     */
    public void eatFood(){
        setHunger(getHunger() - foodInventory.getElement(0));
        foodInventory.remove(0);
    }

    /**
     * Solve the age of the Animal
     * @param year The current year
     * @param day The current day
     */
    public void solveAge(int year, int day){
        int ageDay = day - getDayBorn();
        int ageYear = year - getYearBorn();
        if (ageDay < 0){
            ageDay += 365;
            ageYear -= 1;
        }
        setAgeYear(ageYear);
        setAgeDay(ageDay);
    }

    /**
     * Store a Food object within the Animal's inventory if it has space for it, if it does not then leave the food
     * or take some and resize the Food object within the world
     */
    public void storeFood(){
        setTargetingFood(false);
        for(int i = 0; i < getFoodList().size(); i++){
            if(getTargetFoodID() == getFoodList().get(i).getID()) {
                if (getFoodList().get(i).isPoisonous()) {
                    setPoisoned(true);
                    setPoisonTime(getFoodList().get(i).getDecay()/2);
                    getFoodGroupRef().getChildren().remove(i);
                    getFoodList().remove(i);
                } else {
                    if (foodInventory.getSlotMax() > getFoodList().get(i).getSize()) {
                        if (foodInventory.add(getFoodList().get(i).getSize())) {
                            getFoodGroupRef().getChildren().remove(i);
                            getFoodList().remove(i);
                        }
                    } else {
                        do {
                            if (foodInventory.add(foodInventory.getSlotMax())) {
                                getFoodList().get(i).getImage().setRadius(getFoodList().get(i).getImage().getRadius() - foodInventory.getSlotMax());
                            }
                        }
                        while (foodInventory.getSize() < foodInventory.getCapacity() && getFoodList().get(i).getImage().getRadius() > -1);
                        if (getFoodList().get(i).getImage().getRadius() < 1) {
                            getFoodGroupRef().getChildren().remove(i);
                            getFoodList().remove(i);
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * Consume Water from the Animal's inventory and remove the element from the inventory
     */
    public void drinkWater(){
        setThirst(getThirst() - waterInventory.getElement(0));
        waterInventory.remove(0);
    }

    /**
     * Store Water within the Animal's inventory, fill the inventory as all water sources are infinite
     */
    public void storeWater(){
        // all water is the same so there is no need to check against the correct one
        setTargetingWater(false);
        // fill the entire inventory with water
        for(int i = 0; i < waterInventory.getCapacity(); i++){
            waterInventory.add(waterInventory.getSlotMax());
        }
    }

    /**
     * Set a localTarget in at the location of Water if it is seen within the area
     */
    public void checkWater(){
        for (Water water : getWaterList()){
            if (Collision.overlapsEfficient(this.getSmellCircle(), water.getCircle())) {
                if (Collision.overlapsAccurate(this.getSmellCircle(), water.getCircle())) {
                    setTargetingWater(true);
                    setTargetingFood(false);
                    setTargetingAnimal(false);
                    setLocalTarget(water.getCircle());
                }
            }
        }
    }

    /**
     * Remove the localTarget and set all target variables to the original state
     */
    public void removeLocalTarget(){
        setLocalTargetBool(false);
        setTargetingAnimal(false);
        setTargetingFood(false);
        setTargetingWater(false);
        targetFoodID = -1;
        setDx(0);
        setDy(0);
    }

    /**
     * Set the mainTarget bool to false
     */
    public void removeMainTarget(){
        setHasMainTarget(false);
    }

    /**
     * Check the area for shelters
     */
    public void checkShelters(){
        System.out.println("! no override found in " + getSpecies() + " !");
    }

    /**
     * Enter a shelter and complete tasks based on the reason for entering the shelter. Either; breed if
     * another animal of the opposite sex is in the shelter also, drop off all resources or pick up resources
     * if in a dire situation
     */
    public void enterShelter(){
        Random rand = new Random();
        setWaitAtHome(100 + rand.nextInt(500));
        setInShelter(true);
        setSelfVisibility(false);
        setTargetingHome(false);
        setFollowMainCoolDown(10000 + new Random().nextInt(5000));

        for (int i = 0; i < getShelterList().size(); i++) {
            if (getShelterList().get(i).getID() == getHomeID()) {
                // Check if the opposite sex is in the shelter and is also the correct age
                if (isShouldBreed()){
                    setWaitAtHome(1000 + rand.nextInt(500));
                    for (Animal animal : getAnimalList()){
                        if (animal.isInShelter()) {
                            if (animal.getGender() != getGender()) {
                                if (animal.isShouldBreed()) {
                                    if (animal.getHomeID() == getHomeID()){
                                        animal.setShouldBreed(false);
                                        setShouldBreed(false);
                                        createBaby(animal);
                                        setBreedTimer(5000);
                                        animal.setBreedTimer(5000);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    setBreedTimer(5000);
                }


                // Drop all food/ water off
                for (int j = 0; j < foodInventory.getSize(); j++) {
                    getShelterList().get(i).getFoodInventory().add(foodInventory.getElement(j));
                }
                for (int j = 0; j < waterInventory.getSize(); j++) {
                    getShelterList().get(i).getWaterInventory().add(waterInventory.getElement(j));
                }

                if (getShelterList().get(i).getFoodInventory().getSize() < getShelterList().get(i).getFoodInventory().getCapacity()) {
                    foodInventory.empty();
                }
                if (getShelterList().get(i).getWaterInventory().getSize() < getShelterList().get(i).getFoodInventory().getCapacity()) {
                    waterInventory.empty();
                }

                // take some food and/or water
                if (waterInventory.getSize() == 0 && getThirst() >= 10) {
                    for (int j = 0; j < getWaterInventory().getCapacity() / 2; j++) {
                        if (getShelterList().get(i).getWaterInventory().getSize() > 1) {
                            if (getShelterList().get(i).getWaterInventory().getElement(0) > getWaterInventory().getSlotMax()) {
                                getWaterInventory().add(getWaterInventory().getSlotMax());
                            } else {
                                getWaterInventory().add(getShelterList().get(i).getWaterInventory().getElement(0));
                            }
                            getShelterList().get(i).getWaterInventory().remove(0);
                        }
                    }
                }
                if (foodInventory.getSize() == 0 && getHunger() >= 10) {
                    for (int j = 0; j < getFoodInventory().getCapacity() / 2; j++) {
                        if (getShelterList().get(i).getFoodInventory().getSize() > 1) {
                            if (getShelterList().get(i).getFoodInventory().getElement(0) > getFoodInventory().getSlotMax()) {
                                getFoodInventory().add(getFoodInventory().getSlotMax());
                            } else {
                                getFoodInventory().add(getShelterList().get(i).getFoodInventory().getElement(0));
                            }
                            getShelterList().get(i).getFoodInventory().remove(0);
                        }
                    }
                }
            }
        }
    }

    /**
     * Exit the Animal from the shelter setting its self as visible to the scene and creating a timer to not return
     */
    public void exitShelter(){
        setWaitAtHome(0);
        setFollowMainCoolDown(10000 + new Random().nextInt(5000));
        setInShelter(false);
        setSelfVisibility(true);
    }

    /**
     * Set the Animal's mainTarget to the home target defined
     */
    public void targetHome(){
        setTargetingHome(true);
        setFollowMainCoolDown(1000);
        setMainTarget(getHomeTarget());
    }

    /**
     * Check the Animal will not collide with an Obstacle object if the the Animal were to occupy the same location as
     * the coordinates provided
     * @param posX X coordinate
     * @param posY Y coordinate
     * @return if the animal will not be colliding in the given coordinates
     */
    public boolean checkMove(int posX, int posY){
        for(int i = 0; i < getObstacleList().size(); i++){
            if(getObstacleList().get(i).getX() - (getImage().getRadius() + getObstacleList().get(i).getImage().getRadius()) < posX &&
                    posX < getObstacleList().get(i).getX() + (getImage().getRadius() + getObstacleList().get(i).getImage().getRadius()) &&
                    getObstacleList().get(i).getY() - (getImage().getRadius() + getObstacleList().get(i).getImage().getRadius()) < posY &&
                    posY < getObstacleList().get(i).getY() + (getImage().getRadius() + getObstacleList().get(i).getImage().getRadius())){
                if (Collision.overlapsAccurate(getObstacleList().get(i).getImage(), new Circle(posX, posY, getSize()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Update the lable of the Animal
     */
    public void updateText(){
        getText().setText(statistics());
    }

    /**
     * Set the Animal visibility within the root groups i.e. setSelfVisibility(false) will make the Animal invisible
     * @param visibility value to set visibility to
     */
    public void setSelfVisibility(boolean visibility){
        int index = 0;
        for(int i = 0; i < getAnimalList().size(); i++){
            if (getID() == getAnimalList().get(i).getID()){
                index = i;
                break;
            }
        }
        getAnimalGroupRef().getChildren().get(index).setVisible(visibility);
        getAnimalHomeLocationRef().getChildren().get(index).setVisible(visibility);
        getAnimalSmellRef().getChildren().get(index).setVisible(visibility);
        getAnimalStatsRef().getChildren().get(index).setVisible(visibility);
        getAnimalTargetRef().getChildren().get(index).setVisible(visibility);
        getAnimalLabelRef().getChildren().get(index).setVisible(visibility);
    }

    /**
     * Create a baby with the animal passed
     * @param animal Animal to have a baby with
     */
    public void createBaby(Animal animal){
        int x = (int) (getImage().getCenterX() + getImage().getTranslateX()), y = (int) (getImage().getCenterY() + getImage().getTranslateY());
        int smellRange = (getSmellRange() + animal.getSmellRange()) / 2;
        int size = (int)((getSize() + animal.getSize()) / 2);
        float speed = (float) (getSpeed() + animal.getSpeed()) / 2;
        float metabolism = (getMetabolism() + animal.getMetabolism()) / 2;
        int strength = (getStrength() + animal.getStrength()) / 2;

        //int x, int y, char gender, String name, double speed, float metabolism, int strength, int smell, int size

        char gender;
        if(new Random().nextInt(2) == 1){
            gender = 'M';
        } else {
            gender = 'F';
        }
        getWorldRef().addAnimal(getSpecies(), x, y, gender, getName() + "Jr", speed, metabolism, strength, smellRange, size);
    }

    public String statistics(){
        return ("Name:\t\t\t" + getName() + "\n" +
                "ID:\t\t\t\t" + getID() + "\n" +
                "Gender:\t\t\t" + getGender() + "\n" +
                "Species:\t\t\t" + getSpecies() + "\n" +
                "Speed:\t\t\t" + String.format("%.1g", getSpeed()) + "\n" +
                "Smell Range:\t\t" + getSmellRange() + "\n" +
                "Metabolism:\t\t" + String.format("%.1g", getMetabolism()) + "\n" +
                "Food Inventory: \t" + getFoodInventory().getSize() + "/" + getFoodInventory().getCapacity() + "\n" +
                "Water Inventory:\t" + getWaterInventory().getSize() + "/" + getWaterInventory().getCapacity());
    }
    public String toString(){
        return ("Name: " + getName() + "\n" +
                "Xpos: " + getX() + "\n" +
                "Ypos: " + getY() + "\n");
    }

    /**
     * Give Animal a random gender
     */
    public void giveGender(){
        Random rand = new Random();
        if (rand.nextInt(2) == 1)
            setGender('M');
        else
            setGender('F');
    }

    /**
     * Give Animal a name from list of male and female names depending on the gender
     * @param names_m List of male names
     * @param names_f List of female names
     */
    public void giveName(String [] names_m, String [] names_f){
        Random rand = new Random();
        if (this.gender == 'M')
            setName(names_m[rand.nextInt(names_m.length)]);
        else
            setName(names_f[rand.nextInt(names_f.length)]);
    }

    /**
     * Cool down the timer to follow the mainTarget
     */
    public void coolDownFollowMain(){
        setFollowMainCoolDown(getFollowMainCoolDown() - 1);
        if(getFollowMainCoolDown() < 0){
            setFollowMainCoolDown(0);
        }
    }

    /**
     * Cool down the timer to follow a foodTarget
     */
    public void coolDownFood(){
        setFoodSearchCoolDown(getFoodSearchCoolDown() - 1);
        if (getFoodSearchCoolDown() < 0){
            setFoodSearchCoolDown(0);
            getSmellCircle().setFill(Color.rgb(0, 100, 100));
        }
        else{
            getSmellCircle().setFill(Color.rgb(100, 0, 0));
        }
    }

    /**
     * Cool down the timer for breeding
     */
    public void coolDownBreedTimer(){
        if (getBreedTimer() <= 0){
            setBreedTimer(0);
        } else {
            setBreedTimer(getBreedTimer() - 1);
        }
    }


    // SELF GET/SET FUNCTIONS
    public void setFoodList(ArrayList<Food> f){
        foodList = f;
    }

    public void setWaterList(ArrayList<Water> w){
        waterList = w;
    }

    public void setShelterList(ArrayList<Shelter> s){
        shelterList = s;
    }

    public void setObstacleList(ArrayList<Obstacle> o){
        obstacleList = o;
    }

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

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSpecies(){
        return species;
    }
    public void setSpecies(String species){
        this.species = species;
    }

    public char getSymbol(){
        return symbol;
    }
    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }

    public int getEnergy(){
        return energy;
    }
    public void setEnergy(int energy){
        this.energy = energy;
    }

    public int getSmellRange() {
        return smellRange;
    }
    public void setSmellRange(int smellRange){
        this.smellRange = smellRange;
    }

    public float getSize(){
        return size;
    }
    public void setSize(float size){
        this.size = size;
    }

    public Circle getImage() {
        return image;
    }
    public void setImage(Circle image) {
        this.image = image;
    }

    public Circle getSmellCircle() {
        return smellCircle;
    }
    public void setSmellCircle(Circle smellCircle) {
        this.smellCircle = smellCircle;
    }

    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDx() {
        return dx;
    }
    public void setDx(double d) {
        this.dx = d;
    }

    public double getDy() {
        return dy;
    }
    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getTurnAngle(){
        return turnAngle;
    }
    public void setTurnAngle(int t){
        turnAngle = t;
    }

    public float getHunger(){
        return hunger;
    }
    public void setHunger(float hunger){
        this.hunger = hunger;
    }

    public int getWaitAtHome(){
        return waitAtHome;
    }
    public void setWaitAtHome(int waitAtHome){
        this.waitAtHome = waitAtHome;
    }

    public float getMetabolism(){
        return metabolism;
    }
    public void setMetabolism(float metabolism){
        this.metabolism = metabolism;
    }

    public char getGender(){
        return gender;
    }
    public void setGender(char gender){
        this.gender = gender;
    }

    public Rectangle getHomeLocation(){
        return homeLocation;
    }
    public void setHomeLocation(Rectangle homeLocation){
        this.homeLocation = homeLocation;
    }

    public void setHome(Target home, int ID){
        setHomeTarget(home);
        setHomeID(ID);
    }

    public void setAnimalGroupRef(Group a){
        animalGroupRef = a;
    }

    public void setFoodGroupRef(Group f){
        foodGroupRef = f;
    }

    public int getLastAngle() {
        return lastAngle;
    }
    public void setLastAngle(int lastAngle) {
        this.lastAngle = lastAngle;
    }

    public int getPathDistance() {
        return pathDistance;
    }
    public void setPathDistance(int pathDistance) {
        this.pathDistance = pathDistance;
    }

    public Rectangle getTargetLocation() {
        return targetLocation;
    }
    public void setTargetLocation(Rectangle targetLocation) {
        this.targetLocation = targetLocation;
    }

    public boolean hasLocalTarget() {
        return localTargetBool;
    }
    public void setLocalTargetBool(boolean t){
        localTargetBool = t;
    }

    public boolean isTargetFood(){
        return targetingFood;
    }
    public void setTargetingFood(boolean t){
        targetingFood = t;
    }

    public boolean isTargetingHome(){
        return targetingHome;
    }
    public void setTargetingHome(boolean targetingHome){
        this.targetingHome = targetingHome;
    }

    public int getTargetFoodID(){
        return targetFoodID;
    }
    public void setTargetFoodID(int f){
        targetFoodID = f;
    }

    public boolean hasMainTarget(){
        return mainTargetBool;
    }
    public void setHasMainTarget(boolean mainTargetBool){
        this.mainTargetBool = mainTargetBool;
    }

    public Group getFoodGroupRef() {
        return foodGroupRef;
    }

    public Target getLocalTarget() {
        return localTarget;
    }
    public void setLocalTarget(Target localTarget) {
        this.localTarget = localTarget;
        setLocalTargetBool(true);
    }
    public void setLocalTarget(Circle circle) {
        this.localTarget = new Target(circle);
        setLocalTargetBool(true);
    }

    public Target getMainTarget() {
        return mainTarget;
    }
    public void setMainTarget(Target mainTarget) {
        this.mainTarget = mainTarget;
        setHasMainTarget(true);
    }

    public Target getHomeTarget() {
        return homeTarget;
    }
    public void setHomeTarget(Target homeTarget) {
        this.homeTarget = homeTarget;
    }

    public int getFoodSearchCoolDown() {
        return foodSearchCoolDown;
    }

    public void setFoodSearchCoolDown(int foodSearchCoolDown) {
        this.foodSearchCoolDown = foodSearchCoolDown;
    }

    public int getFollowMainCoolDown(){
        return followMainCoolDown;
    }
    public void setFollowMainCoolDown(int followMainCoolDown){
        this.followMainCoolDown = followMainCoolDown;
    }

    public void setFoodInventory(Inventory foodInventory){
        this.foodInventory = foodInventory;
    }
    public Inventory getFoodInventory(){
        return foodInventory;
    }

    public Inventory getWaterInventory() {
        return waterInventory;
    }

    public void setWaterInventory(Inventory waterInventory) {
        this.waterInventory = waterInventory;
    }

    public Group getAnimalGroupRef() {
        return animalGroupRef;
    }

    public Group getWaterGroupRef() {
        return waterGroupRef;
    }

    public void setWaterGroupRef(Group waterGroupRef) {
        this.waterGroupRef = waterGroupRef;
    }

    public float getThirst() {
        return thirst;
    }

    public void setThirst(float thirst) {
        this.thirst = thirst;
    }

    public boolean isTargetingWater() {
        return targetingWater;
    }

    public void setTargetingWater(boolean targetingWater) {
        this.targetingWater = targetingWater;
    }

    public StatsBar getStatsBar() {
        return statsBar;
    }

    public void setStatsBar(StatsBar statsBar) {
        this.statsBar = statsBar;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public double getOriginalSpeed() {
        return originalSpeed;
    }

    public void setOriginalSpeed(double originalSpeed) {
        this.originalSpeed = originalSpeed;
    }

    public int getHomeID() {
        return homeID;
    }

    public void setHomeID(int homeID) {
        this.homeID = homeID;
    }

    public boolean isInShelter() {
        return inShelter;
    }

    public void setInShelter(boolean inShelter) {
        this.inShelter = inShelter;
    }

    public ArrayList<Animal> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(ArrayList<Animal> animalList) {
        this.animalList = animalList;
    }

    public Group getAnimalSmellRef() {
        return animalSmellRef;
    }

    public void setAnimalSmellRef(Group animalSmellRef) {
        this.animalSmellRef = animalSmellRef;
    }

    public Group getAnimalStatsRef() {
        return animalStatsRef;
    }

    public void setAnimalStatsRef(Group animalStatsRef) {
        this.animalStatsRef = animalStatsRef;
    }

    public Group getAnimalLabelRef() {
        return animalLabelRef;
    }

    public void setAnimalLabelRef(Group animalLabelRef) {
        this.animalLabelRef = animalLabelRef;
    }

    public Group getAnimalTargetRef() {
        return animalTargetRef;
    }

    public void setAnimalTargetRef(Group animalTargetRef) {
        this.animalTargetRef = animalTargetRef;
    }

    public Group getAnimalHomeLocationRef() {
        return animalHomeLocationRef;
    }

    public void setAnimalHomeLocationRef(Group animalHomeLocationRef) {
        this.animalHomeLocationRef = animalHomeLocationRef;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public int getPoisonTime() {
        return poisonTime;
    }

    public void setPoisonTime(int poisonTime) {
        this.poisonTime = poisonTime;
    }

    public int getAgeDay() {
        return ageDay;
    }

    public void setAgeDay(int ageDay) {
        this.ageDay = ageDay;
    }

    public int getAgeYear() {
        return ageYear;
    }

    public void setAgeYear(int ageYear) {
        this.ageYear = ageYear;
    }

    public int getDayBorn() {
        return dayBorn;
    }

    public void setDayBorn(int dayBorn) {
        this.dayBorn = dayBorn;
    }

    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    public int getLastAge() {
        return lastAge;
    }

    public void setLastAge(int lastAge) {
        this.lastAge = lastAge;
    }

    public boolean isShouldBreed() {
        return shouldBreed;
    }

    public void setShouldBreed(boolean shouldBreed) {
        this.shouldBreed = shouldBreed;
    }

    public int getBreedTimer() {
        return breedTimer;
    }

    public void setBreedTimer(int breedTimer) {
        this.breedTimer = breedTimer;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public World getWorldRef() {
        return worldRef;
    }

    public void setWorldRef(World worldRef) {
        this.worldRef = worldRef;
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public ArrayList<Water> getWaterList() {
        return waterList;
    }

    public ArrayList<Shelter> getShelterList() {
        return shelterList;
    }

    public ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public boolean isTargetingAnimal() {
        return targetingAnimal;
    }

    public void setTargetingAnimal(boolean targetingAnimal) {
        this.targetingAnimal = targetingAnimal;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getSpeedChangeAge() {
        return speedChangeAge;
    }

    public void setSpeedChangeAge(int speedChangeAge) {
        this.speedChangeAge = speedChangeAge;
    }

    public int getBreedAge() {
        return breedAge;
    }

    public void setBreedAge(int breedAge) {
        this.breedAge = breedAge;
    }
}

