package LS;

import java.util.Random;
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
    // All constructor parameters
    private float size, metabolism, hunger = 0, thirst = 0;
    private int id, x, y, energy, maxEnergy, smellRange, turnAngle, pathDistance, foodSearchCoolDown,
            followMainCoolDown, waitAtHome, homeID, breedTimer, lastAngle = new Random().nextInt(360),
            targetFoodID, poisonTime, ageYear, ageDay, dayBorn, yearBorn, maxAge, speedChangeAge, breedAge,
            lastAge, strength;
    private boolean localTargetBool, mainTargetBool, targetingFood, targetingWater, targetingHome,
            targetingAnimal, poisoned, inShelter, shouldBreed;
    private double speed, originalSpeed, dx, dy;
    public Inventory foodInventory, waterInventory;
    private Target localTarget, mainTarget, homeTarget;
    private World worldRef;
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
     */
    public Animal(String speciesIn, char symbolIn, int IDIn, int dayBorn, int yearBorn, int energyIn, int xIn, int yIn, World world){

        // Set all passed attributes to the object
        setSpecies(speciesIn);
        setSymbol(symbolIn);
        setID(IDIn);
        setEnergy(energyIn);
        setMaxEnergy(energyIn);
        setX(xIn);
        setY(yIn);
        setWorldRef(world);

        // Create stats bar and set the colour of each bar
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
     * @param colour Color variable of the Animals body circle
     */
    public Animal(String speciesIn, char symbolIn, int IDIn, int dayBorn, int yearBorn, int energyIn, int xIn, int yIn,
                  char gender, String name, double speed, float metabolism, int strength, int smell, int size, Color colour, World world){

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
        setWorldRef(world);

        // Create smell attributes colouring and making slightly transparent
        setSmellCircle(new Circle(x, y, getSmellRange()));
        getSmellCircle().setFill(Color.rgb(0, 100, 100));
        getSmellCircle().setOpacity(0.3);
        // set the path distance to the radius of the smell Circle
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
    }

    /**
     * Adds the Animal to all lists needed to function and be visible within the world
     */
    public void addSelfToLists(){
        // Add self to animalList
        getWorldRef().getAnimalList().add(this);
        // Link all nodes to the relative roots
        getWorldRef().getAnimalGroup().getChildren().add(getImage());
        getWorldRef().getAnimalSmellGroup().getChildren().add(getSmellCircle());
        getWorldRef().getAnimalTargetGroup().getChildren().add(getTargetLocation());
        getWorldRef().getAnimalStatsGroup().getChildren().add(getStatsBar().getGroup());
        getWorldRef().getAnimalHomeLocationGroup().getChildren().add(getHomeLocation());
        getWorldRef().getAnimalLabelGroup().getChildren().add(getText());
    }

    /**
     * Updates the Animal between keyFrames, calling all functions needed to appear to live within the world
     */
    public void update(){
        // If the animal is in the shelter
        if(isInShelter()){
            // Cool down the wait at home timer
            setWaitAtHome(getWaitAtHome() - 1);
            // If the cool down is 0 exit the shelter
            if (getWaitAtHome() <= 0){
                exitShelter();
            }
        }
        else {
            // Call functions to allow the Animal to function within the world
            updateText();
            checkHungerThirst();
            target();
            directDxDy();
            move();
            hungerEnergyWaterDecay();
        }
        // Check the age of the Animal
        ageEvents();
    }

    /**
     * Triggers life events when a specific age (defined by the child class) is reached
     */
    public void ageEvents(){
        // Check that the age has changed
        if (getLastAge() != getAgeYear()) {
            // Set the last age of the animal
            setLastAge(getAgeYear());
            // Check that the animal reaches various stages of life triggering events
            if (getAgeYear() >= getBreedAge()) {
                // Allow the Animal to breed
                setShouldBreed(true);
            }
            if (getAgeYear() >= getSpeedChangeAge()) {
                // Slow the Animal down
                setSpeed(getOriginalSpeed() * 0.7);
            }
            if (getAgeYear() == getMaxAge()) {
                // Deteriorate the Animal's maxEnergy
                setMaxEnergy(getMaxEnergy()/4);
            }
        }
    }

    /**
     * Checks that eating/drinking water will not be wasteful, called on every update allowing
     * the chance to consume
     */
    public void checkHungerThirst(){
        // If the Animal has food
        if (foodInventory.getSize() > 0) {
            // check that eating food wont be wasteful
            if (getHunger() > (double)foodInventory.getElement(0)) {
                eatFood();
            }
        }
        // If the Animal has water
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
        // If the animal will not be colliding with an Obstacle within the next update
        if (checkMove((int)(getImage().getTranslateX() + getImage().getCenterX() + (getDx())),
                (int)(getImage().getTranslateY() + getImage().getCenterY() + (getDy())))) {

            // Move body
            getImage().setTranslateX(getImage().getTranslateX() + getDx());
            getImage().setTranslateY(getImage().getTranslateY() + getDy());
            // Move smell
            getSmellCircle().setTranslateX(getSmellCircle().getTranslateX() + getDx());
            getSmellCircle().setTranslateY(getSmellCircle().getTranslateY() + getDy());
            // Move hunger bar
            getStatsBar().getBar(0).setTranslateX(getStatsBar().getBar(0).getTranslateX() + getDx());
            getStatsBar().getBar(0).setTranslateY(getStatsBar().getBar(0).getTranslateY() + getDy());
            // Move thirst bar
            getStatsBar().getBar(1).setTranslateX(getStatsBar().getBar(1).getTranslateX() + getDx());
            getStatsBar().getBar(1).setTranslateY(getStatsBar().getBar(1).getTranslateY() + getDy());
            // Move energy bar
            getStatsBar().getBar(2).setTranslateX(getStatsBar().getBar(2).getTranslateX() + getDx());
            getStatsBar().getBar(2).setTranslateY(getStatsBar().getBar(2).getTranslateY() + getDy());
            // Move back bar
            getStatsBar().getBackBar().setTranslateX(getStatsBar().getBackBar().getTranslateX() + getDx());
            getStatsBar().getBackBar().setTranslateY(getStatsBar().getBackBar().getTranslateY() + getDy());
            // Move label
            getText().setTranslateX(getText().getTranslateX() + getDx());
            getText().setTranslateY(getText().getTranslateY() + getDy());

            // Move target indicator
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
        if (getThirst() < 10 && getHunger() < 10 && getEnergy() < getMaxEnergy()){
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

        // Adjust the stats bar lengths
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
        // Cool down all elements that hinder targeting
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
            // If there is no main target to follow
            if (!hasLocalTarget()){
                getRandomLocalTarget();
            }

            // If the Animal is not targeting food and is not targeting an Animal to hunt and the Animal food search
            // cool down is equals 0 and the Animal has space in the inventory to pick food up
            if ((!isTargetFood() && !isTargetingAnimal()) && getFoodSearchCoolDown() == 0 && foodInventory.getSize() < foodInventory.getCapacity()) {
                checkFood();
            }
            // If the Animal is not targeting water and has space to pick up water
            if (!isTargetingWater() && waterInventory.getSize() < waterInventory.getCapacity()){
                checkWater();
            }
            // If the Animal does not have a home
            if (homeTarget == null){
                checkShelters();
            }
            // If the animal has a home and has inventory items to drop off and relative cool downs are active
            if (homeTarget != null && waterInventory.getSize() == waterInventory.getCapacity()
                    && foodInventory.getSize() == foodInventory.getCapacity() && getFoodSearchCoolDown() == 0
                    && getFollowMainCoolDown() == 0){
                targetHome();
            }
            // If the Animal has a home and is set to breed and is allowed to target home
            if (homeTarget != null && isShouldBreed() && getBreedTimer() == 0  && getFoodSearchCoolDown() == 0
                    && getFollowMainCoolDown() == 0){
                targetHome();
            }
            // If the Animal has a home and can go home and is thirsty and without water or is hungry and without food
            if (homeTarget != null && getFollowMainCoolDown() == 0){
                if (waterInventory.getSize() == 0 && getThirst() == 10) {
                    targetHome();
                    checkWater();
                }
                if (foodInventory.getSize() == 0 && getHunger() == 10  && getFoodSearchCoolDown() == 0 ) {
                    targetHome();
                    checkFood();
                }
            }
            // If the Animal has a local target
            if (hasLocalTarget()){
                checkCollideLocalTarget();
            }
        }
    }

    /**
     * Create a localTarget directed towards the mainTarget
     */
    public void createLocalTargetDirectedToMain(){
        // Define the center X and Y points of the Main Target
        double x = getMainTarget().getCircle().getCenterX() + getMainTarget().getCircle().getTranslateX();
        double y = getMainTarget().getCircle().getCenterY() + getMainTarget().getCircle().getTranslateY();
        // Define the angle towards the Target coordinates
        double angle = getAngleTo(x, y);
        // Create a LocalTarget in the direction of the Main Target
        int tX = (int) ((getImage().getCenterX() + getImage().getTranslateX()) + getPathDistance() * Math.cos(angle));
        int tY = (int) ((getImage().getCenterY() + getImage().getTranslateY()) + getPathDistance() * Math.sin(angle));
        // Set the local target and store the last angle used
        setLocalTarget(new Target(tX, tY));
        setLastAngle((int)Math.toDegrees(angle));
    }

    /**
     * Create a random localTarget along the smell range circumference within a given turn angle, if too
     * many attempts are made choose a target from all 360 degrees of direction
     */
    public void getRandomLocalTarget(){
        // Define variables to be used within the function
        Random rand = new Random();
        int randomAttemptTracker = 0;
        int anAngle, tX, tY, path = getPathDistance();
        // Run a do while loop in which a target will be picked, the terminating condition is that
        // the target is not in an invalid location.
        do{
            if (randomAttemptTracker < getTurnAngle() * 2){
                anAngle = rand.nextInt(getTurnAngle() * 2) + getLastAngle() - getTurnAngle();
                randomAttemptTracker++;
            } else{
                // Too many attempts at picking an angle within the turn range were made
                anAngle = rand.nextInt(360);
                path = rand.nextInt(getPathDistance());
            }
            // convert angle to radians
            double angle = Math.toRadians(anAngle);
            // Set the new Target x and y
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
        // Define variables to be used within the function
        Random rand = new Random();
        int tX, tY, anAngle;
        // Run a do while in which the terminating condition is that the Target location is a valid target
        do {
            // Pick a random angle in degrees
            anAngle = rand.nextInt(360);
            // Convert to radians
            double angleRad = Math.toRadians(anAngle);
            int path = rand.nextInt(getPathDistance());
            // Solve the new Target location
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
        // Get the center points of the Animal
        double thisX = getImage().getCenterX() + getImage().getTranslateX();
        double thisY = getImage().getCenterY() + getImage().getTranslateY();
        // Return the arc tan of the difference between the points
        return Math.atan2(targetY - thisY, targetX - thisX);
    }

    /**
     * Set dx and dy towards the localTarget
     */
    public void directDxDy(){
        // Work out the Target x and y center
        double targetX = (getLocalTarget().getCircle().getCenterX() + getLocalTarget().getCircle().getTranslateX());
        double targetY = (getLocalTarget().getCircle().getCenterY() + getLocalTarget().getCircle().getTranslateY());
        // Get the angle in radians
        double angle = getAngleTo(targetX, targetY);
        // Set the directions in which the Animal needs to move
        setDx((Math.cos(angle) * getSpeed()));
        setDy((Math.sin(angle) * getSpeed()));
    }

    /**
     * Check the Food seen has not been eaten by another animal
     * @return if the Food object is still available for consumption
     */
    public boolean foodIsStillThere(){
        // loop for all food elements
        for(Food food : getWorldRef().getFoodList()){
            if (food.getID() == targetFoodID){
                // Return true if the target food id was found
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
        // loop through all animals
        for(Animal animal : getWorldRef().getAnimalList()){
            if (animal.getID() == targetFoodID){
                if (Collision.overlapsEfficient(animal.getImage(), getSmellCircle())) {
                    // return true if the animal was found within the list
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
        // check that the Animal has energy to fight
        if (getEnergy() > 0) {
            // loop for all Animals
            for (Animal animal : getWorldRef().getAnimalList()) {
                if (animal.getID() == getTargetFoodID()){
                    if (animal.getStrength() <= getStrength()) {
                        // If the animal has a better strength rating kill the other animal
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
        // check the Animal is still there to collide with
        if (isTargetingAnimal()){
            if (!animalStillThere()){
                removeLocalTarget();
            }
        }
        // Check if the Animal is colliding wih its local target
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
                // Remove the local target once task is completed
                removeLocalTarget();
            }
        }
    }

    /**
     * Check if the Animal is colliding with the mainTarget set
     */
    public void checkCollideMainTarget(){
        // check if the Animal is colliding with its Main target
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
        // Check the surrounding area for Animals to target
        for(Animal animal : getWorldRef().getAnimalList()) {
            // check it is in its hunt list
            if (isInHuntList(animal.getSymbol())) {
                // check the Animal is not itself
                if (getID() != animal.getID()) {
                    // check the Animal is within the smell range
                    if (Collision.overlapsEfficient(this.getSmellCircle(), animal.getImage())) {
                        if (Collision.overlapsAccurate(this.getSmellCircle(), animal.getImage())) {
                            // save the information
                            setTargetingAnimal(true);
                            setTargetFoodID(animal.getID());
                            setLocalTarget(animal.getImage());
                            break;
                        }
                    }
                }
            }
        }
        // Check for food within the surrounding area
        for(Food food : getWorldRef().getFoodList()){
            // check it is in its eat list
            if (isInEatList(food.getType().charAt(0))) {
                // check if the food is within the smell range
                if (Collision.overlapsEfficient(this.getSmellCircle(), food.getImage())) {
                    if (Collision.overlapsAccurate(this.getSmellCircle(), food.getImage())) {
                        // save the information
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

        if (type == 'M'){
            return true;
        }

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

        return getWorldRef().getConfiguration().getEatList().get(indexX).get(indexY);
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

        return getWorldRef().getConfiguration().getHuntList().get(indexX).get(indexY);
    }

    /**
     * Consume Food from the Animal's inventory and remove the element from the inventory
     */
    public void eatFood(){
        // give the Animal the relevant value of food stores in its inventory
        setHunger(getHunger() - foodInventory.getElement(0));
        foodInventory.remove(0);
    }

    /**
     * Solve the age of the Animal
     * @param year The current year
     * @param day The current day
     */
    public void solveAge(int year, int day){
        // Work out the age of the Animal
        int ageDay = day - getDayBorn();
        int ageYear = year - getYearBorn();
        if (ageDay < 0){
            ageDay += 365;
            ageYear -= 1;
        }
        // set those ages
        setAgeYear(ageYear);
        setAgeDay(ageDay);
    }

    /**
     * Store a Food object within the Animal's inventory if it has space for it, if it does not then leave the food
     * or take some and resize the Food object within the world
     */
    public void storeFood(){
        // Store food within the Animal inventory
        setTargetingFood(false);
        // loop for all food
        for(int i = 0; i < getWorldRef().getFoodList().size(); i++){
            // check to test the correct food ID
            if(getTargetFoodID() == getWorldRef().getFoodList().get(i).getID()) {
                // if the food is poison
                if (getWorldRef().getFoodList().get(i).isPoisonous()) {
                    setPoisoned(true);
                    // Set a timer for poison to take effect
                    setPoisonTime(getWorldRef().getFoodList().get(i).getDecay()/2);
                    getWorldRef().getFoodGroup().getChildren().remove(i);
                    getWorldRef().getFoodList().remove(i);
                } else {
                    // check the Animal has room to store the food item
                    if (foodInventory.getSlotMax() > getWorldRef().getFoodList().get(i).getSize()) {
                        if (foodInventory.add(getWorldRef().getFoodList().get(i).getSize())) {
                            getWorldRef().getFoodGroup().getChildren().remove(i);
                            getWorldRef().getFoodList().remove(i);
                        }
                    } else {
                        // if it does not then take all it can
                        do {
                            if (foodInventory.add(foodInventory.getSlotMax())) {
                                getWorldRef().getFoodList().get(i).getImage().setRadius(getWorldRef().getFoodList().get(i).getImage().getRadius() - foodInventory.getSlotMax());
                            }
                        }
                        while (foodInventory.getSize() < foodInventory.getCapacity() && getWorldRef().getFoodList().get(i).getImage().getRadius() > -1);
                        // if the food is too small then just remove it from the world
                        if (getWorldRef().getFoodList().get(i).getImage().getRadius() < 1) {
                            getWorldRef().getFoodGroup().getChildren().remove(i);
                            getWorldRef().getFoodList().remove(i);
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
        // drink the water within the inventory
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
        // check the local area for water
        for (Water water : getWorldRef().getWaterList()){
            // if a collision is happening between the water and the smell range of the animal
            if (Collision.overlapsEfficient(this.getSmellCircle(), water.getCircle())) {
                if (Collision.overlapsAccurate(this.getSmellCircle(), water.getCircle())) {
                    // save that information
                    setTargetingWater(true);
                    setTargetingFood(false);
                    setTargetingAnimal(false);
                    setLocalTarget(water.getCircle());
                    break;
                }
            }
        }
    }

    /**
     * Remove the localTarget and set all target variables to the original state
     */
    public void removeLocalTarget(){
        // remove the local target of the Animal and remove any target definitions
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
        // make self invisible while within the shelter
        setSelfVisibility(false);
        setTargetingHome(false);
        setFollowMainCoolDown(10000 + new Random().nextInt(5000));

        // loop through all shelter
        for (int i = 0; i < getWorldRef().getShelterList().size(); i++) {
            if (getWorldRef().getShelterList().get(i).getID() == getHomeID()) {
                // Check if the opposite sex is in the shelter and is also the correct age
                if (isShouldBreed()){
                    setWaitAtHome(1000 + rand.nextInt(500));
                    for (Animal animal : getWorldRef().getAnimalList()){
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
                    getWorldRef().getShelterList().get(i).getFoodInventory().add(foodInventory.getElement(j));
                }
                for (int j = 0; j < waterInventory.getSize(); j++) {
                    getWorldRef().getShelterList().get(i).getWaterInventory().add(waterInventory.getElement(j));
                }

                if (getWorldRef().getShelterList().get(i).getFoodInventory().getSize() < getWorldRef().getShelterList().get(i).getFoodInventory().getCapacity()) {
                    foodInventory.empty();
                }
                if (getWorldRef().getShelterList().get(i).getWaterInventory().getSize() < getWorldRef().getShelterList().get(i).getFoodInventory().getCapacity()) {
                    waterInventory.empty();
                }

                // take some food and/or water
                if (waterInventory.getSize() == 0 && getThirst() >= 10) {
                    for (int j = 0; j < getWaterInventory().getCapacity() / 2; j++) {
                        if (getWorldRef().getShelterList().get(i).getWaterInventory().getSize() > 1) {
                            if (getWorldRef().getShelterList().get(i).getWaterInventory().getElement(0) > getWaterInventory().getSlotMax()) {
                                getWaterInventory().add(getWaterInventory().getSlotMax());
                            } else {
                                getWaterInventory().add(getWorldRef().getShelterList().get(i).getWaterInventory().getElement(0));
                            }
                            getWorldRef().getShelterList().get(i).getWaterInventory().remove(0);
                        }
                    }
                }
                if (foodInventory.getSize() == 0 && getHunger() >= 10) {
                    for (int j = 0; j < getFoodInventory().getCapacity() / 2; j++) {
                        if (getWorldRef().getShelterList().get(i).getFoodInventory().getSize() > 1) {
                            if (getWorldRef().getShelterList().get(i).getFoodInventory().getElement(0) > getFoodInventory().getSlotMax()) {
                                getFoodInventory().add(getFoodInventory().getSlotMax());
                            } else {
                                getFoodInventory().add(getWorldRef().getShelterList().get(i).getFoodInventory().getElement(0));
                            }
                            getWorldRef().getShelterList().get(i).getFoodInventory().remove(0);
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
        // remove the wait at home
        setWaitAtHome(0);
        // set the cool down to follow the main target
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
        // Cycle through all obstacles
        for(int i = 0; i < getWorldRef().getObstacleList().size(); i++){
            // if there is a collision
            if(getWorldRef().getObstacleList().get(i).getX() - (getImage().getRadius() + getWorldRef().getObstacleList().get(i).getImage().getRadius()) < posX &&
                    posX < getWorldRef().getObstacleList().get(i).getX() + (getImage().getRadius() + getWorldRef().getObstacleList().get(i).getImage().getRadius()) &&
                    getWorldRef().getObstacleList().get(i).getY() - (getImage().getRadius() + getWorldRef().getObstacleList().get(i).getImage().getRadius()) < posY &&
                    posY < getWorldRef().getObstacleList().get(i).getY() + (getImage().getRadius() + getWorldRef().getObstacleList().get(i).getImage().getRadius())){
                if (Collision.overlapsAccurate(getWorldRef().getObstacleList().get(i).getImage(), new Circle(posX, posY, getSize()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Update the label of the Animal
     */
    public void updateText(){
        getText().setText(statistics());
    }

    /**
     * Set the Animal visibility within the root groups i.e. setSelfVisibility(false) will make the Animal invisible
     * @param visibility value to set visibility to
     */
    public void setSelfVisibility(boolean visibility){
        // Set the visibility of the then
        int index = 0;
        // loop for all animals to find the an index
        for(int i = 0; i < getWorldRef().getAnimalList().size(); i++){
            if (getID() == getWorldRef().getAnimalList().get(i).getID()){
                index = i;
                break;
            }
        }
        // Set the animals visuals to be invisible or visible
        getWorldRef().getAnimalGroup().getChildren().get(index).setVisible(visibility);
        getWorldRef().getAnimalHomeLocationGroup().getChildren().get(index).setVisible(visibility);
        getWorldRef().getAnimalSmellGroup().getChildren().get(index).setVisible(visibility);
        getWorldRef().getAnimalStatsGroup().getChildren().get(index).setVisible(visibility);
        getWorldRef().getAnimalTargetGroup().getChildren().get(index).setVisible(visibility);
        getWorldRef().getAnimalLabelGroup().getChildren().get(index).setVisible(visibility);
    }

    /**
     * Create a baby with the animal passed
     * @param animal Animal to have a baby with
     */
    public void createBaby(Animal animal){
        // work out the averages of both animals to breed
        int x = (int) (getImage().getCenterX() + getImage().getTranslateX()), y = (int) (getImage().getCenterY() + getImage().getTranslateY());
        int smellRange = (getSmellRange() + animal.getSmellRange()) / 2;
        int size = (int)((getSize() + animal.getSize()) / 2);
        float speed = (float) (getSpeed() + animal.getSpeed()) / 2;
        float metabolism = (getMetabolism() + animal.getMetabolism()) / 2;
        int strength = (getStrength() + animal.getStrength()) / 2;

        // create a random gender
        char gender;
        if(new Random().nextInt(2) == 1){
            gender = 'M';
        } else {
            gender = 'F';
        }
        // add an animal to the world with the specified traits
        getWorldRef().addAnimal(getSpecies(), x, y, gender, getName() + "Jr", speed, metabolism, strength, smellRange, size);
    }

    /**
     * @return A string a data describing the Animal
     */
    public String statistics(){
        // Return the String of the statistics
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

    /**
     * @return A simplified data string describing the Animal
     */
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

    /**
     * @return x co-ordinate for the animal
     */
    public int getX(){
        return x;
    }

    /**
     * The integer passed is set to the X coordinate of the Animal
     * @param x X coordinate
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * @return Y coordinate
     */
    public int getY(){
        return y;
    }

    /**
     * The integer passed is set to the Y coordinate of the Animal
     * @param y Y coordinate
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * @return animals name
     */
    public String getName(){
        return name;
    }

    /**
     * set the String passed to the private variable within the class
     * @param name Animal name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @return animal species
     */
    public String getSpecies(){
        return species;
    }

    /**
     * The string species is passed as a parameter and set to a private variable
     * @param species Species
     */
    public void setSpecies(String species){
        this.species = species;
    }

    /**
     * @return animal symbol
     */
    public char getSymbol(){
        return symbol;
    }

    /**
     * set the symbol of the animal by taking the parameter and setting it to a private variable
     * @param symbol Symbol
     */
    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    /**
     * @return animal ID
     */
    public int getID(){
        return id;
    }

    /**
     * set Animal ID by taking the parameter and setting it to a private variable
     * @param id Unique ID
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * @return animal energy
     */
    public int getEnergy(){
        return energy;
    }

    /**
     * The animal energy is set by taking the parameter and setting it to a private int within the class
     * @param energy Animal energy value
     */
    public void setEnergy(int energy){
        this.energy = energy;
    }

    /**
     * @return animals smell range
     */
    public int getSmellRange() {
        return smellRange;
    }

    /**
     * Set the smell range by setting the parameter to a private variable
     * @param smellRange Smell range of the Animal
     */
    public void setSmellRange(int smellRange){
        this.smellRange = smellRange;
    }

    /**
     * @return animal body size
     */
    public float getSize(){
        return size;
    }

    /**
     * set animal body size to a private variable in the animal class
     * @param size Size of the Animal
     */
    public void setSize(float size){
        this.size = size;
    }

    /**
     *
     * @return animal image
     */
    public Circle getImage() {
        return image;
    }

    /**
     * set the animal image to a private variable
     * @param image Image of the Animal
     */
    public void setImage(Circle image) {
        this.image = image;
    }

    /**
     * @return animals smell circle
     */
    public Circle getSmellCircle() {
        return smellCircle;
    }

    /**
     * set the smell circle of the animal to a private variable
     * @param smellCircle Smell circle of the Animal
     */
    public void setSmellCircle(Circle smellCircle) {
        this.smellCircle = smellCircle;
    }

    /**
     * @return animal speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * set the speed of the animal to a private variable within the class
     * @param speed Movement speed of the Animal
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return the change in x
     */
    public double getDx() {
        return dx;
    }

    /**
     * the change in x is set by setting the parameter to a private variable
     * @param dx Change in X
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * @return the change in y
     */
    public double getDy() {
        return dy;
    }

    /**
     * the change in y is set to a private variable
     * @param dy Change in Y
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * @return the angle range in which the animal can choose a target
     */
    public int getTurnAngle(){
        return turnAngle;
    }

    /**
     * set the turn angle to a private variable within the class
     * @param t Turn angle
     */
    public void setTurnAngle(int t){
        turnAngle = t;
    }

    /**
     * @return animal hunger
     */
    public float getHunger(){
        return hunger;
    }

    /**
     * set the hunger value of the animal to a private variable
     * @param hunger Hunger of the Animal
     */
    public void setHunger(float hunger){
        this.hunger = hunger;
    }

    /**
     * @return the time the animal must wait in their shelter
     */
    public int getWaitAtHome(){
        return waitAtHome;
    }

    /**
     * set the wait at home time to a private variable
     * @param waitAtHome Time to wait at home
     */
    public void setWaitAtHome(int waitAtHome){
        this.waitAtHome = waitAtHome;
    }

    /**
     * @return animals metabolism
     */
    public float getMetabolism(){
        return metabolism;
    }

    /**
     * set the animals metabolism to a private float within the class
     * @param metabolism Metabolism of the Animal
     */
    public void setMetabolism(float metabolism){
        this.metabolism = metabolism;
    }

    /**
     * @return animals gender
     */
    public char getGender(){
        return gender;
    }

    /**
     * parameter passes the animals gender into the function and is set as a private variable
     * @param gender Gender of the Animal
     */
    public void setGender(char gender){
        this.gender = gender;
    }

    /**
     * @return The location of he animals home
     */
    public Rectangle getHomeLocation(){
        return homeLocation;
    }

    /**
     * set the shape of the animals home to a rectangle which is passed into the function and set to private
     * within the class
     * @param homeLocation Home locator
     */
    public void setHomeLocation(Rectangle homeLocation){
        this.homeLocation = homeLocation;
    }

    /**
     * set the animals home by using the target and the ID. This function is used to set the Home target and the
     * HomeId
     * @param home Home Target
     * @param ID Unique ID
     */
    public void setHome(Target home, int ID){
        setHomeTarget(home);
        setHomeID(ID);
    }

    /**
     * @return last angle selected by the animal
     */
    public int getLastAngle() {
        return lastAngle;
    }

    /**
     * set the last angle to a private variable
     * @param lastAngle Last angle moved in by the Animal
     */
    public void setLastAngle(int lastAngle) {
        this.lastAngle = lastAngle;
    }

    /**
     * @return the distance at which a local target is placed when randomly generated
     */
    public int getPathDistance() {
        return pathDistance;
    }

    /**
     * set the pathDistance to a private variable in the animal class
     * @param pathDistance Distance to create a target
     */
    public void setPathDistance(int pathDistance) {
        this.pathDistance = pathDistance;
    }

    /**
     * @return the location of the animals target
     */
    public Rectangle getTargetLocation() {
        return targetLocation;
    }

    /**
     * The targets location, represented by a rectangle, is set to a private variable
     * @param targetLocation Target locator
     */
    public void setTargetLocation(Rectangle targetLocation) {
        this.targetLocation = targetLocation;
    }

    /**
     * @return if the local target has been set
     */
    public boolean hasLocalTarget() {
        return localTargetBool;
    }

    /**
     * set the boolean localTarget to a private  boolean variable
     * @param t local Target boolean
     */
    public void setLocalTargetBool(boolean t){
        localTargetBool = t;
    }

    /**
     * @return if the animals local target is food
     */
    public boolean isTargetFood(){
        return targetingFood;
    }

    /**
     * sets whether the animals local target is food to a private variable
     * @param t Targeting food boolean
     */
    public void setTargetingFood(boolean t){
        targetingFood = t;
    }

    /**
     * @return if the local target is the animals home
     */
    public boolean isTargetingHome(){
        return targetingHome;
    }

    /**
     * set the animal targeting home to a boolean and a private variable
     * @param targetingHome Targeting home boolean
     */
    public void setTargetingHome(boolean targetingHome){
        this.targetingHome = targetingHome;
    }

    /**
     * @return the target foods ID
     */
    public int getTargetFoodID(){
        return targetFoodID;
    }

    /**
     * set target food ID to a private variable in the class
     * @param f Food ID
     */
    public void setTargetFoodID(int f){
        targetFoodID = f;
    }

    /**
     * @return if an animal has a target to a home
     */
    public boolean hasMainTarget(){
        return mainTargetBool;
    }

    /**
     * set the main target to a private variable
     * @param mainTargetBool Main target boolean
     */
    public void setHasMainTarget(boolean mainTargetBool){
        this.mainTargetBool = mainTargetBool;
    }

    /**
     * @return the local target
     */
    public Target getLocalTarget() {
        return localTarget;
    }

    /**
     * The animals local target is a target object. If this has been set then the setLocalTargetBool setter is
     * set to true
     * @param localTarget Local target boolean
     */
    public void setLocalTarget(Target localTarget) {
        this.localTarget = localTarget;
        setLocalTargetBool(true);
    }

    /**
     * a circle is passed and set as a local target which is a new target object. The localTargetBool setter is
     * then set to  true
     * @param circle Circle to target
     */
    public void setLocalTarget(Circle circle) {
        this.localTarget = new Target(circle);
        setLocalTargetBool(true);
    }

    /**
     * @return the animals main target which is it's home
     */
    public Target getMainTarget() {
        return mainTarget;
    }

    /**
     * set the main target as a private variable from the Target object that is passed in and the setHasMainTarget
     * setter to true
     * @param mainTarget Main Target
     */
    public void setMainTarget(Target mainTarget) {
        this.mainTarget = mainTarget;
        setHasMainTarget(true);
    }

    /**
     * @return the animals target for their home
     */
    public Target getHomeTarget() {
        return homeTarget;
    }

    /**
     * set the home target private variable to the target object passed into the function
     * @param homeTarget Home Target
     */
    public void setHomeTarget(Target homeTarget) {
        this.homeTarget = homeTarget;
    }

    /**
     * @return food search cool down time
     */
    public int getFoodSearchCoolDown() {
        return foodSearchCoolDown;
    }

    /**
     * sets the food cool down time
     * @param foodSearchCoolDown Timer to not search for food
     */
    public void setFoodSearchCoolDown(int foodSearchCoolDown) {
        this.foodSearchCoolDown = foodSearchCoolDown;
    }

    /**
     * @return get follow main cool down time
     */
    public int getFollowMainCoolDown(){
        return followMainCoolDown;
    }

    /**
     * set the followMainCoolDown time to a private integer which acts as a timer
     * @param followMainCoolDown Timer to not search for home
     */
    public void setFollowMainCoolDown(int followMainCoolDown){
        this.followMainCoolDown = followMainCoolDown;
    }

    /**
     * set animals food inventory to a private variable by setting it to the Inventory object that is passed in
     * to the function
     * @param foodInventory Inventory used by the Animal
     */
    public void setFoodInventory(Inventory foodInventory){
        this.foodInventory = foodInventory;
    }

    /**
     * @return the animals food inventory
     */
    public Inventory getFoodInventory(){
        return foodInventory;
    }

    /**
     * @return animals water inventory
     */
    public Inventory getWaterInventory() {
        return waterInventory;
    }

    /**
     * set the water inventory for the animal
     * @param waterInventory Inventory used by the Animal
     */
    public void setWaterInventory(Inventory waterInventory) {
        this.waterInventory = waterInventory;
    }

    /**
     * @return animals thirst
     */
    public float getThirst() {
        return thirst;
    }

    /**
     * set animal thirst to a private variable
     * @param thirst Thirst of the Animal
     */
    public void setThirst(float thirst) {
        this.thirst = thirst;
    }

    /**
     * @return if the animal's target is water
     */
    public boolean isTargetingWater() {
        return targetingWater;
    }

    /**
     * set the targeting water boolean to a private variable
     * @param targetingWater Water target boolean
     */
    public void setTargetingWater(boolean targetingWater) {
        this.targetingWater = targetingWater;
    }

    /**
     * @return the animals statistic bars
     */
    public StatsBar getStatsBar() {
        return statsBar;
    }

    /**
     * set the statistics bar to a private variable in the class using the statsBar object that is passed in to the
     * function
     * @param statsBar Stats bar of the Animal
     */
    public void setStatsBar(StatsBar statsBar) {
        this.statsBar = statsBar;
    }

    /**
     * @return text
     */
    public Text getText() {
        return text;
    }

    /**
     * set the animals text as a private variable using the Text object that has been passed in
     * @param text Text
     */
    public void setText(Text text) {
        this.text = text;
    }

    /**
     * @return the animals original speed
     */
    public double getOriginalSpeed() {
        return originalSpeed;
    }

    /**
     * set the original speed to a private variable in the class
     * @param originalSpeed Original speed of the Animal
     */
    public void setOriginalSpeed(double originalSpeed) {
        this.originalSpeed = originalSpeed;
    }

    /**
     * @return home ID
     */
    public int getHomeID() {
        return homeID;
    }

    /**
     * set the home ID to a private variable in the animal class
     * @param homeID Unique home ID
     */
    public void setHomeID(int homeID) {
        this.homeID = homeID;
    }

    /**
     * @return if the animal is in a shelter
     */
    public boolean isInShelter() {
        return inShelter;
    }

    /**
     * set the variable to private within the class
     * @param inShelter in shelter boolean
     */
    public void setInShelter(boolean inShelter) {
        this.inShelter = inShelter;
    }

    /**
     * @return if the animal is poisoned
     */
    public boolean isPoisoned() {
        return poisoned;
    }

    /**
     * set if the animal is poisoned as a private variable in the class
     * @param poisoned Poisoned boolean
     */
    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    /**
     * @return amount of time animal is poisoned
     */
    public int getPoisonTime() {
        return poisonTime;
    }

    /**
     * set poison time to a private integer within the class
     * @param poisonTime Timer for poison to be effective within the Animal
     */
    public void setPoisonTime(int poisonTime) {
        this.poisonTime = poisonTime;
    }

    /**
     * setAgeDay as a private integer within the class
     * @param ageDay Age in days
     */
    public void setAgeDay(int ageDay) {
        this.ageDay = ageDay;
    }

    /**
     * @return animals age in years
     */
    public int getAgeYear() {
        return ageYear;
    }

    /**
     * set age year as a private variable within the class
     * @param ageYear Age in years
     */
    public void setAgeYear(int ageYear) {
        this.ageYear = ageYear;
    }

    /**
     * @return day the animal was born
     */
    public int getDayBorn() {
        return dayBorn;
    }

    /**
     * set the day the animal was born as a private variable within the animal class
     * @param dayBorn Day in which Animal was born
     */
    public void setDayBorn(int dayBorn) {
        this.dayBorn = dayBorn;
    }

    /**
     * @return Year the animal was Born
     */
    public int getYearBorn() {
        return yearBorn;
    }

    /**
     * set as a private variable
     * @param yearBorn Year the Animal was Born
     */
    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    /**
     * @return animals last age
     */
    public int getLastAge() {
        return lastAge;
    }

    /**
     * set as a private variable within the class
     * @param lastAge Latest age milestone
     */
    public void setLastAge(int lastAge) {
        this.lastAge = lastAge;
    }

    /**
     * @return if the animal should breed
     */
    public boolean isShouldBreed() {
        return shouldBreed;
    }

    /**
     * set as a private variable
     * @param shouldBreed Should the Animal breed
     */
    public void setShouldBreed(boolean shouldBreed) {
        this.shouldBreed = shouldBreed;
    }

    /**
     * @return breed timer
     */
    public int getBreedTimer() {
        return breedTimer;
    }

    /**
     * set as a private variable
     * @param breedTimer Timer to stop breeding
     */
    public void setBreedTimer(int breedTimer) {
        this.breedTimer = breedTimer;
    }

    /**
     * @return animal strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * set strength to a private integer
     * @param strength Strength of the Animal
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * @return World reference
     */
    public World getWorldRef() {
        return worldRef;
    }

    /**
     * set world reference to a private variable using world object that is passed into the function
     * @param worldRef Refernce to the World
     */
    public void setWorldRef(World worldRef) {
        this.worldRef = worldRef;
    }

    /**
     * @return targeting an animals
     */
    public boolean isTargetingAnimal() {
        return targetingAnimal;
    }

    /**
     * set to a private variable within the class
     * @param targetingAnimal Targeting Animal boolean
     */
    public void setTargetingAnimal(boolean targetingAnimal) {
        this.targetingAnimal = targetingAnimal;
    }

    /**
     * @return maximum energy
     */
    public int getMaxEnergy() {
        return maxEnergy;
    }

    /**
     * set max energy to a private variable within the class
     * @param maxEnergy Maximum energy of the Animal
     */
    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    /**
     * @return maximum age of the animal
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * set the maximum age to a private integer within the class
     * @param maxAge The maximum age before the Animal will die
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * @return speed change age
     */
    public int getSpeedChangeAge() {
        return speedChangeAge;
    }

    /**
     * set to a private variable
     * @param speedChangeAge Age at which the Animals speed will alter
     */
    public void setSpeedChangeAge(int speedChangeAge) {
        this.speedChangeAge = speedChangeAge;
    }

    /**
     * @return animal breeding age
     */
    public int getBreedAge() {
        return breedAge;
    }

    /**
     * set breeding age to a private integer within the class
     * @param breedAge Age at which the Animal can breed
     */
    public void setBreedAge(int breedAge) {
        this.breedAge = breedAge;
    }
}

