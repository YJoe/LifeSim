package LS;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Animal {
    private Circle image, smellCircle, targetCircle;
    private Rectangle targetLocation, hungerBar, energyBar, backBar, homeLocation;
    private Group foodGroupRef;
    private Group animalGroupRef;
    private String species, name;
    private char symbol, gender;
    private float size, metabolism, hunger = 0;
    private int id, x, y, energy, smellRange, turnAngle, pathDistance, foodSearchCoolDown;
    private int homeX, homeY, memory, memoryBiasX, memoryBiasY, waitAtHome;
    private int lastAngle = new Random().nextInt(360), targetFoodID;
    private int statBarHeight = 4, statBarWidth = 50, statBarSpacing = 2;
    private double speed, dx, dy;
    private boolean localTargetBool, mainTargetBool, targetingFood, targetingHome, update;
    private Target localTarget;
    private Target mainTarget;
    private Target homeTarget;
    private ArrayList<Food> foodList = new ArrayList<>();
    private ArrayList<Shelter> shelterList = new ArrayList<>();
    private ArrayList<Obstacle> obstacleList = new ArrayList<>();

    //                             f  w  h
    private int[] taskPriority = { 0, 0, 0 };

    // Constructor
    public Animal(String speciesIn, char symbolIn, int IDIn, int energyIn, int xIn, int yIn, Group food, Group animal){
        setSpecies(speciesIn); setSymbol(symbolIn); setID(IDIn); setEnergy(energyIn);
        setX(xIn); setY(yIn); setFoodGroupRef(food); setAnimalGroupRef(animal);

        // Create bar background
        setBackBar(new Rectangle(x, y, statBarWidth + 4, (statBarHeight * 2) + statBarSpacing + 4));
        getBackBar().setFill(Color.rgb(50, 50, 50));
        getBackBar().setX(getBackBar().getX() - (statBarWidth/2) - 2);
        getBackBar().setY(getBackBar().getY() + 8);

        // Create hunger bar
        setHungerBar(new Rectangle(x, y, statBarWidth, statBarHeight));
        // Centre and colour hunger bar
        getHungerBar().setFill(Color.rgb(255, 0, 0));
        getHungerBar().setX(getHungerBar().getX() - (statBarWidth/2));
        getHungerBar().setY(getHungerBar().getY() + 10);

        // Create energy bar
        setEnergyBar(new Rectangle(x, y, statBarWidth, statBarHeight));
        // Centre and colour energy bar
        getEnergyBar().setFill(Color.rgb(0, 255, 0));
        getEnergyBar().setX(getEnergyBar().getX() - (statBarWidth / 2));
        getEnergyBar().setY(getHungerBar().getY() + statBarHeight + statBarSpacing);

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
        setFoodSearchCoolDown(100);

        // Create memory direction bias
        Random rand = new Random();
        setMemoryBiasX(rand.nextInt(2));
        setMemoryBiasY(rand.nextInt(2));

        setShouldUpdate(true);

        // As all other attributes are determined by random variables based on the animal subclass, other
        // values are set in the constructor of said subclass after the super constructor is called
    }

    // Main functions
    public void update(){
        target();
        directDxDy();
        move();
        forget();
    }

    public void prioritiseTasks(){
        taskPriority[0] = (int)(getHunger()*10);
    }

    public void getTask(){
        int highest = 0;
        for (int i = 0; i < 3; i++){
            if (taskPriority[i] > taskPriority[highest]) {
                highest = i;
            }
        }
        //System.out.println("Prioritise task[" + highest + "]");
    }

    public void move(){
        // check move calls getDx()*2 and getDy()*2 to account for imperfections in the calculation
        // this overestimates the movement and checks against it, ensuring that a smaller movement is
        // safe to occur.
        if (checkMove((int)(getImage().getTranslateX() + getImage().getCenterX() + (getDx())),
                (int)(getImage().getTranslateY() + getImage().getCenterY() + (getDy())))) {

            // body
            getImage().setTranslateX(getImage().getTranslateX() + getDx());
            getImage().setTranslateY(getImage().getTranslateY() + getDy());
            // smell
            getSmellCircle().setTranslateX(getSmellCircle().getTranslateX() + getDx());
            getSmellCircle().setTranslateY(getSmellCircle().getTranslateY() + getDy());
            // hunger bar
            getHungerBar().setTranslateX(getHungerBar().getTranslateX() + getDx());
            getHungerBar().setTranslateY(getHungerBar().getTranslateY() + getDy());
            // energy bar
            getEnergyBar().setTranslateX(getEnergyBar().getTranslateX() + getDx());
            getEnergyBar().setTranslateY(getEnergyBar().getTranslateY() + getDy());
            // back bar
            getBackBar().setTranslateX(getBackBar().getTranslateX() + getDx());
            getBackBar().setTranslateY(getBackBar().getTranslateY() + getDy());

            // move target indicator
            getTargetLocation().setTranslateX(getLocalTarget().getCircle().getCenterX() + getLocalTarget().getCircle().getTranslateX());
            getTargetLocation().setTranslateY(getLocalTarget().getCircle().getCenterY() + getLocalTarget().getCircle().getTranslateY());
        } else {
            // remove any local target bounds and pick a new one
            setTargetingFood(false);
            getRandomLocalTarget360();
            setFoodSearchCoolDown(100);
        }
        // decay hunger or energy depending on how much hungry the animal is
        hungerEnergyDecay();
    }

    public void hungerEnergyDecay(){
        // add to hunger using metabolism higher metabolism means getting hungry quicker
        setHunger(getHunger() + getMetabolism());

        if (getHunger() > 0 && getHunger() < 10){
            if (getEnergy() < 1000) {
                setEnergy(getEnergy() + 2);
            }
        }else {
            if (getHunger() >= 10) {
                setHunger(10);
                setEnergy(getEnergy() - 1);
            } else {
                if (getHunger() < 0) {
                    setHunger(0);
                }
            }
        }

        getHungerBar().setWidth(getHunger() * (statBarWidth/10));
        getEnergyBar().setWidth(getEnergy() * (statBarWidth/1000.0));

    }

    public void checkFood(){
        for(int i = 0; i < foodList.size(); i++){
            if (checkCollide(this.getSmellCircle(), foodList.get(i).getImage())){
                setTargetingFood(true);
                setTargetFoodID(foodList.get(i).getID());
                setLocalTarget(foodList.get(i).getImage());
            }
        }
    }

    public void checkShelters(){
        for(int i = 0; i < shelterList.size(); i++){
            if(checkCollide(getImage(), shelterList.get(i).getImage())){
                Random rand = new Random();
                setWaitAtHome(500);
                enterShelter(i);
            }
        }
    }

    public void enterShelter(int i){
        if (shelterList.get(i).checkRoom()){
            shelterList.get(i).addAnimal(this);
            //animalGroupRef.getChildren().get(i).setVisible(false);
            setShouldUpdate(false);
        }
    }

    public void exitShelter(){
        setShouldUpdate(true);
        setTargetingHome(false);
    }

    public boolean checkCollide(Circle C1, Circle C2){
        int x1pos = (int) (C1.getCenterX() + C1.getTranslateX());
        int y1pos = (int) (C1.getCenterY() + C1.getTranslateY());

        int x2pos = (int) (C2.getCenterX() + C2.getTranslateX());
        int y2pos = (int) (C2.getCenterY() + C2.getTranslateY());

        int a = Math.abs(x1pos - x2pos);
        int b = Math.abs(y1pos - y2pos);
        int c =  (int)(C1.getRadius() + C2.getRadius());

        // |(x2-x1)| + |(y1-y2)| <= (r1+r2)
        return (a + b <= c);
    }

    public void target(){
        coolDownFood();
        if (hasMainTarget()) {
            checkCollideMainTarget();
            if (hasLocalTarget()) {
                checkCollideLocalTarget();
            } else {
                createLocalTargetDirectedToMain();
            }
        } else {
            if (hasLocalTarget()) {
                if (!isTargetFood() && getFoodSearchCoolDown() < 10){
                    checkFood();
                }
                checkCollideLocalTarget();
            } else {
                getRandomLocalTarget();
                checkFood();
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
        int anAngle, tX, tY;
        do{
            if (randomAttemptTracker < getTurnAngle() * 2){
                anAngle = rand.nextInt(getTurnAngle() * 2) + getLastAngle() - getTurnAngle();
                randomAttemptTracker++;
            } else{
                // Too many attempts at picking an angle within the turn range were made
                anAngle = rand.nextInt(360);
            }
            double angle = Math.toRadians(anAngle);
            tX = (int) ((getImage().getCenterX() + getImage().getTranslateX()) + getPathDistance() * Math.cos(angle));
            tY = (int) ((getImage().getCenterY() + getImage().getTranslateY()) + getPathDistance() * Math.sin(angle));
        } while(!isValidTarget(tX, tY));
        setLocalTarget(new Target(tX, tY));
        setLastAngle(anAngle);
    }

    public void getRandomLocalTarget360(){
        Random rand = new Random();
        int anAngle = rand.nextInt(360);
        double angleRad = Math.toRadians(anAngle);
        int tX = (int) ((getImage().getCenterX() + getImage().getTranslateX()) + getPathDistance() * Math.cos(angleRad));
        int tY = (int) ((getImage().getCenterY() + getImage().getTranslateY()) + getPathDistance() * Math.sin(angleRad));
        setLocalTarget(new Target(tX, tY));
        setLastAngle(anAngle);
    }

    public boolean isValidTarget(int x, int y){
        if(x < Main.SIZE_X && x >= 0 && y < Main.SIZE_Y && y >= 0){
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

    public void checkCollideLocalTarget(){
        if (checkCollide(getImage(), getLocalTarget().getCircle())){
            if (isTargetFood()){
                eatFood();
            }
            removeLocalTarget();
        }
    }

    public void checkCollideMainTarget(){
        if (checkCollide(getImage(), getMainTarget().getCircle())){
            removeMainTarget();
            removeLocalTarget();
        }
    }

    public void eatFood(){
        setTargetingFood(false);
        for(int i = 0; i < foodList.size(); i++){
            if(getTargetFoodID() == foodList.get(i).getID()){
                getFoodGroupRef().getChildren().remove(i);
                setHunger(getHunger() - foodList.get(i).getCal());
                foodList.remove(i);
                break;
            }
        }
    }

    public void removeLocalTarget(){
        setLocalTargetBool(false);
        setDx(0);
        setDy(0);
    }
    public void removeMainTarget(){
        setHasMainTarget(false);
    }

    public void targetHome(){
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
                    if (getHomeTarget().getY() < 0) {
                        getHomeTarget().setY(0);
                    }
                }
            }
            getHomeLocation().setX(getHomeTarget().getX());
            getHomeLocation().setY(getHomeTarget().getY());
        }
    }

    public boolean checkMove(int posX, int posY){
        for(int i = 0; i < obstacleList.size(); i++){
            if((obstacleList.get(i).getX() - 10 < posX && posX < obstacleList.get(i).getX() + 10) &&
                    (obstacleList.get(i).getY() - 10 < posY && posY < obstacleList.get(i).getY() + 10)){
                return false;
            }
        }
        return true;
    }

    // SELF GET/SET FUNCTIONS
    public void setFoodList(ArrayList<Food> f){
        foodList = f;
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

    public boolean shouldUpdate(){
        return update;
    }
    public void setShouldUpdate(boolean update){
        this.update = update;
    }

    public Rectangle getHungerBar(){
        return hungerBar;
    }
    public void setHungerBar(Rectangle hungerBar){
        this.hungerBar = hungerBar;
    }

    public Rectangle getEnergyBar(){
        return energyBar;
    }
    public void setEnergyBar(Rectangle energyBar){
        this.energyBar = energyBar;
    }

    public Rectangle getBackBar(){
        return backBar;
    }
    public void setBackBar(Rectangle backBar){
        this.backBar = backBar;
    }

    public Rectangle getHomeLocation(){
        return homeLocation;
    }
    public void setHomeLocation(Rectangle homeLocation){
        this.homeLocation = homeLocation;
    }

    public void setHome(Target home){
        setHomeTarget(home);
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
        return ("Name: " + getName() + "\n" +
                "Species: " + getSpecies() + "\n" +
                "Speed: " + getSpeed() + "\n" +
                "Smell Range: " + getSmellRange() + "\n" +
                "Metabolism: " + getMetabolism());
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
        }
    }
}

