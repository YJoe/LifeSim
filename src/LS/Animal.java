package LS;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Animal {
    private World worldRef;
    private Configuration configuration;
    private Circle image, smellCircle;
    private Text text;
    private StatsBar statsBar;
    private Rectangle targetLocation;
    private Rectangle homeLocation;
    private Group foodGroupRef;
    private Group waterGroupRef;
    private Group animalGroupRef, animalSmellRef, animalStatsRef, animalLabelRef, animalTargetRef, animalHomeLocationRef;
    private String species, name;
    private char symbol, gender;
    private float size;
    private float metabolism;
    private float hunger = 0;
    private float thirst = 0;
    private int id, x, y, energy, maxEnergy, smellRange, turnAngle, pathDistance, foodSearchCoolDown, followMainCoolDown;
    private int homeX, homeY, memory, memoryBiasX, memoryBiasY, waitAtHome, homeID, breedTimer;
    private int lastAngle = new Random().nextInt(360);
    private int targetFoodID;
    private int targetWaterID;
    private int waitInShelterTimer;
    private int poisonTime;
    private int ageYear, ageDay, dayBorn, yearBorn;
    private boolean inShelter, shouldBreed;
    private int lastAge;
    private int strength;
    private int maxAge, speedChangeAge, breedAge;
    private double speed, originalSpeed, dx, dy;
    private boolean localTargetBool, mainTargetBool, targetingFood, targetingWater, targetingHome, targetingAnimal, poisoned;
    private Target localTarget, mainTarget, homeTarget;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Food> foodList = new ArrayList<>();
    private ArrayList<Water> waterList = new ArrayList<>();
    private ArrayList<Shelter> shelterList = new ArrayList<>();
    private ArrayList<Obstacle> obstacleList = new ArrayList<>();
    public Inventory foodInventory, waterInventory;

    // Constructor
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
        setHomeX(0);
        setHomeY(0);

        // set a food cool down value
        setFoodSearchCoolDown(0);
        setFollowMainCoolDown(0);

        // Create memory direction bias
        Random rand = new Random();
        setMemoryBiasX(rand.nextInt(2));
        setMemoryBiasY(rand.nextInt(2));

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

    public Animal(String speciesIn, char symbolIn, int IDIn, int dayBorn, int yearBorn, int energyIn, int xIn, int yIn,
                  char gender, String name, double speed, float metabolism, int strength, int smell, int size,
                  Group food, Group animal, Group water, World worldRef, ArrayList<Animal> animalList,
                  ArrayList<Food> foodList, ArrayList<Water> waterList, ArrayList<Obstacle> obstacleList,
                  ArrayList<Shelter> shelterList, Group animalSmellRef, Group animalStatsRef, Group animalLabelRef,
                  Group animalTargetRef, Group animalHomeLocationRef, Configuration configuration, Color colour){

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

        // Set a random memory
        setMemory(100);

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
        setHomeX(0);
        setHomeY(0);

        // set a food cool down value
        setFoodSearchCoolDown(0);
        setFollowMainCoolDown(0);

        // Create memory direction bias
        Random rand = new Random();
        setMemoryBiasX(rand.nextInt(2));
        setMemoryBiasY(rand.nextInt(2));

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

    public void addSelfToLists(){
        animalList.add(this);
        getAnimalGroupRef().getChildren().add(getImage());
        getAnimalSmellRef().getChildren().add(getSmellCircle());
        getAnimalTargetRef().getChildren().add(getTargetLocation());
        getAnimalStatsRef().getChildren().add(getStatsBar().getGroup());
        getAnimalHomeLocationRef().getChildren().add(getHomeLocation());
        getAnimalLabelRef().getChildren().add(getText());
    }

    // Main functions
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
            forget();
            hungerEnergyWaterDecay();
        }
        ageEvents();
    }

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

    public void checkHungerThirst(){
        if (foodInventory.getSize() > 0) {
            // check that eating food wont be wasteful
            if (getHunger() > (double)foodInventory.getElement(0)) {
                eatFood();
            }
        }
        if (waterInventory.getSize() > 0) {
            // check that drinking water wont be wasteful/
            if (getThirst() > (double)waterInventory.getElement(0)){
                drinkWater();
            }
        }
    }

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

    public void createLocalTargetDirectedToMain(){
        double x = getMainTarget().getCircle().getCenterX() + getMainTarget().getCircle().getTranslateX();
        double y = getMainTarget().getCircle().getCenterY() + getMainTarget().getCircle().getTranslateY();
        double angle = getAngleTo(x, y);
        int tX = (int) ((getImage().getCenterX() + getImage().getTranslateX()) + getPathDistance() * Math.cos(angle));
        int tY = (int) ((getImage().getCenterY() + getImage().getTranslateY()) + getPathDistance() * Math.sin(angle));
        setLocalTarget(new Target(tX, tY));
        setLastAngle((int)Math.toDegrees(angle));
    }

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

    public boolean isValidTarget(int x, int y){
        if(x < Main.SIZE_X && x >= 0 && y < Main.SIZE_Y && y >= 25){
            return true;
        }
        return false;
    }

    public double getAngleTo(double targetX, double targetY){
        double thisX = getImage().getCenterX() + getImage().getTranslateX();
        double thisY = getImage().getCenterY() + getImage().getTranslateY();
        return Math.atan2(targetY - thisY, targetX - thisX);
    }

    public void directDxDy(){
        double targetX = (getLocalTarget().getCircle().getCenterX() + getLocalTarget().getCircle().getTranslateX());
        double targetY = (getLocalTarget().getCircle().getCenterY() + getLocalTarget().getCircle().getTranslateY());
        double angle = getAngleTo(targetX, targetY);
        setDx((Math.cos(angle) * getSpeed()));
        setDy((Math.sin(angle) * getSpeed()));
    }

    public boolean foodIsStillThere(){
        for(Food food : getFoodList()){
            if (food.getID() == targetFoodID){
                return true;
            }
        }
        return false;
    }

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

    public void eatFood(){
        setHunger(getHunger() - foodInventory.getElement(0));
        foodInventory.remove(0);
    }

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
                        while (foodInventory.size < foodInventory.getCapacity() && getFoodList().get(i).getImage().getRadius() > -1);
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

    public void drinkWater(){
        setThirst(getThirst() - waterInventory.getElement(0));
        waterInventory.remove(0);
    }

    public void storeWater(){
        // all water is the same so there is no need to check against the correct one
        setTargetingWater(false);
        // fill the entire inventory with water
        for(int i = 0; i < waterInventory.getCapacity(); i++){
            waterInventory.add(waterInventory.getSlotMax());
        }
    }

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

    public void removeLocalTarget(){
        setLocalTargetBool(false);
        setTargetingAnimal(false);
        setTargetingFood(false);
        setTargetingWater(false);
        targetFoodID = -1;
        setDx(0);
        setDy(0);
    }

    public void removeMainTarget(){
        setHasMainTarget(false);
    }

    public void checkShelters(){
        System.out.println("! no override found in " + getSpecies() + " !");
    }

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

    public void exitShelter(){
        setWaitAtHome(0);
        setFollowMainCoolDown(10000 + new Random().nextInt(5000));
        setInShelter(false);
        setSelfVisibility(true);
    }

    public void targetHome(){
        setTargetingHome(true);
        setFollowMainCoolDown(1000);
        setMainTarget(getHomeTarget());
    }

    public void forget() {
        if (homeTarget != null) {
            Random rand = new Random();

            // A higher memory provides more chances to avoid forgetting
            if (rand.nextInt(getMemory()) == 1) {
                // Distort home values using the memoryBiasX
                if (getMemoryBiasX() == 1) {
                    if (rand.nextInt(2) == 1) {
                        if (rand.nextInt(3) == 1)
                            getHomeTarget().setX(getHomeTarget().getX() - 1);
                        else
                            getHomeTarget().setX(getHomeTarget().getX() + 1);
                    }
                } else {
                    if (rand.nextInt(2) == 1) {
                        if (rand.nextInt(3) == 1)
                            getHomeTarget().setX(getHomeTarget().getX() + 1);
                        else
                            getHomeTarget().setX(getHomeTarget().getX() - 1);
                    }
                }

                // Distort home values using the memoryBiasY
                if (getMemoryBiasY() == 1) {
                    if (rand.nextInt(2) == 1) {
                        if (rand.nextInt(3) == 1)
                            getHomeTarget().setY(getHomeTarget().getY() - 1);
                        else
                            getHomeTarget().setY(getHomeTarget().getY() + 1);
                    }
                } else {
                    if (rand.nextInt(2) == 1) {
                        if (rand.nextInt(3) == 1)
                            getHomeTarget().setY(getHomeTarget().getY() + 1);
                        else
                            getHomeTarget().setY(getHomeTarget().getY() - 1);
                    }
                }

                // Ensure the coordinates aren't going off screen
                if (getHomeTarget().getX() > Main.SIZE_X) {
                    getHomeTarget().setX(Main.SIZE_X);
                } else {
                    if (getHomeTarget().getX() < 0) {
                        getHomeTarget().setX(0);
                    }
                }

                if (getHomeTarget().getY() > Main.SIZE_Y) {
                    getHomeTarget().setY(Main.SIZE_Y);
                } else {
                    if (getHomeTarget().getY() < 25) {
                        getHomeTarget().setY(25);
                    }
                }
            }
            getHomeLocation().setX(getHomeTarget().getX());
            getHomeLocation().setY(getHomeTarget().getY());
        }
    }

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

    public void updateText(){
        getText().setText(statistics());
    }

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

    public void createBaby(Animal animal){
        return;
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

    public int getHomeX(){
        return homeX;
    }
    public void setHomeX(int homeX){
        this.homeX = homeX;
    }

    public int getHomeY(){
        return homeY;
    }
    public void setHomeY(int homeY){
        this.homeY = homeY;
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

    public int getMemory(){
        return memory;
    }
    public void setMemory(int memory){
        this.memory = memory;
    }

    public int getMemoryBiasX(){
        return memoryBiasX;
    }
    public void setMemoryBiasX(int memoryBiasX){
        this.memoryBiasX = memoryBiasX;
    }

    public int getMemoryBiasY(){
        return memoryBiasY;
    }
    public void setMemoryBiasY(int memoryBiasY){
        this.memoryBiasY = memoryBiasY;
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

    public void giveGender(){
        Random rand = new Random();
        if (rand.nextInt(2) == 1)
            setGender('M');
        else
            setGender('F');
    }

    public void giveName(String [] names_m, String [] names_f){
        Random rand = new Random();
        if (this.gender == 'M')
            setName(names_m[rand.nextInt(names_m.length)]);
        else
            setName(names_f[rand.nextInt(names_f.length)]);
    }


    // TARGET GET/SET FUNCTIONS
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

    // INFORMATIONAL
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
    public String toText(){
        return (toString() +
                "Species: " + getSpecies() + "\n" +
                "Symbol: " + getSymbol() + "\n" +
                "ID: " + getID() + "\n" +
                "Energy: " + getEnergy() + "\n");
    }
    public String posString(){
        return("x: " + (getImage().getCenterX() + getImage().getTranslateX())
                + ", y: " + (getImage().getCenterY() + getImage().getTranslateY())
                + ", dx: " + getDx() + ", dy: " + getDy());
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

    public int getFollowMainCoolDown(){
        return followMainCoolDown;
    }
    public void setFollowMainCoolDown(int followMainCoolDown){
        this.followMainCoolDown = followMainCoolDown;
    }
    public void coolDownFollowMain(){
        setFollowMainCoolDown(getFollowMainCoolDown() - 1);
        if(getFollowMainCoolDown() < 0){
            setFollowMainCoolDown(0);
        }
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

    public int getTargetWaterID() {
        return targetWaterID;
    }

    public void setTargetWaterID(int targetWaterID) {
        this.targetWaterID = targetWaterID;
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

    public int getWaitInShelterTimer() {
        return waitInShelterTimer;
    }

    public void setWaitInShelterTimer(int waitInShelterTimer) {
        this.waitInShelterTimer = waitInShelterTimer;
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

    public void coolDownBreedTimer(){
        if (getBreedTimer() <= 0){
            setBreedTimer(0);
        } else {
            setBreedTimer(getBreedTimer() - 1);
        }
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

