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
    private Group foodGroupRef, animalGroupRef;
    private String species, name;
    private char symbol, gender;
    private float size, metabolism, hunger = 0;
    private int id, x, y, energy, smellRange, targetX, targetY, turnAngle, pathDistance;
    private int homeX, homeY, memory, memoryBiasX, memoryBiasY;
    private int lastAngle = new Random().nextInt(360), randomAttemptTracker = 0, targetFoodID;
    private int statBarHeight = 4, statBarWidth = 50, statBarSpacing = 2;
    private double speed, dx, dy;
    private boolean targetBool, targetingFood;
    private ArrayList<Food> foodList = new ArrayList<>();

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
        Rectangle h = new Rectangle(Main.SIZE_X/2, Main.SIZE_Y/2, 5, 5);
        h.setFill(Color.rgb(0, 0, 255));
        setHomeLocation(h);
        setHomeX((int)h.getX());
        setHomeY((int)h.getY());

        // Create memory direction bias
        Random rand = new Random();
        setMemoryBiasX(rand.nextInt(2));
        setMemoryBiasY(rand.nextInt(2));

        // As all other attributes are determined by random variables based on the animal subclass, other
        // values are set in the constructor of said subclass after the super constructor is called
    }

    // Main functions
    public void update(){
        target();
        move();
        forget();
        getTargetLocation().setTranslateX(getTargetCircle().getCenterX() + getTargetCircle().getTranslateX());
        getTargetLocation().setTranslateY(getTargetCircle().getCenterY() + getTargetCircle().getTranslateY());
    }

    public void move(){
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
            int x1pos = (int) (this.getSmellCircle().getCenterX() + this.getSmellCircle().getTranslateX());
            int y1pos = (int) (this.getSmellCircle().getCenterY() + this.getSmellCircle().getTranslateY());

            int x2pos = (int) (foodList.get(i).getImage().getCenterX() + foodList.get(i).getImage().getTranslateX());
            int y2pos = (int) (foodList.get(i).getImage().getCenterY() + foodList.get(i).getImage().getTranslateY());

            int a = Math.abs(x1pos - x2pos);
            int b = Math.abs(y1pos - y2pos);
            int c =  (int)(this.getSmellCircle().getRadius() + foodList.get(i).getImage().getRadius());

            // |(x2-x1)| + |(y1-y2)| <= (r1+r2)
            if (a + b <= c){
                setTargetingFood(true);
                setTargetFoodID(foodList.get(i).getID());
                setTarget(foodList.get(i).getImage());
            }
        }
    }

    public void target(){
        if (hasTarget()){
            if (!isTargetFood()){
                checkFood();
            }
            directToTarget();
            checkCollideTarget();
        }
        else{
            getRandomTarget();
        }
    }

    public void getRandomTarget(){
        Random rand = new Random();
        randomAttemptTracker = 0;
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
        setTarget(new Circle(tX, tY, 1));
        setLastAngle(anAngle);
    }

    public static boolean isValidTarget(int x, int y){
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

    public void directToTarget(){
        double targetX = (getTargetCircle().getCenterX() + getTargetCircle().getTranslateX());
        double targetY = (getTargetCircle().getCenterY() + getTargetCircle().getTranslateY());
        double angle = getAngleTo(targetX, targetY);
        setDx((Math.cos(angle) * getSpeed()));
        setDy((Math.sin(angle) * getSpeed()));
    }

    public void checkCollideTarget(){
        int x1pos = (int) (this.getImage().getCenterX() + this.getImage().getTranslateX());
        int y1pos = (int) (this.getImage().getCenterY() + this.getImage().getTranslateY());

        int x2pos = (int) (getTargetCircle().getCenterX() + getTargetCircle().getTranslateX());
        int y2pos = (int) (getTargetCircle().getCenterY() + getTargetCircle().getTranslateY());

        //      a          b           c
        // |(x2-x1)| + |(y1-y2)| <= (r1+r2)
        float a = (float) Math.abs(x1pos - x2pos);
        float b = (float) Math.abs(y1pos - y2pos);
        float c = (float) Math.abs(getImage().getRadius() + getTargetCircle().getRadius());

        if (a + b <= c){
            if (isTargetFood()){
                eatFood();
            }
            removeTarget();
        }
    }

    public void eatFood(){
        setTargetingFood(false);
        for(int i = 0; i < foodList.size(); i++){
            if(getTargetFoodID() == foodList.get(i).getID()){
                foodGroupRef.getChildren().remove(i);
                setHunger(getHunger() - foodList.get(i).getCal());
                foodList.remove(i);
                break;
            }
        }
    }

    public void removeTarget(){
        setTargetBool(false);
        setDx(0);
        setDy(0);
    }

    public void setTarget(Circle c){
        this.targetCircle = c;
        setTargetBool(true);
    }

    public void forget(){
        Random rand = new Random();

        // A higher memory provides more chances to avoid forgetting
        if(rand.nextInt(getMemory()) == 1) {
            // Distort home values using the memoryBiasX
            if (memoryBiasX == 1){
                if (rand.nextInt(3) == 1)
                    setHomeX(getHomeX() - 1);
                else
                    setHomeX(getHomeX() + 1);
            } else {
                if (rand.nextInt(3) == 1)
                    setHomeX(getHomeX() + 1);
                else
                    setHomeX(getHomeX() - 1);
            }

            // Distort home values using the memoryBiasY
            if (memoryBiasY == 1) {
                if (rand.nextInt(3) == 1)
                    setHomeY(getHomeY() - 1);
                else
                    setHomeY(getHomeY() + 1);
            } else {
                if (rand.nextInt(3) == 1)
                    setHomeY(getHomeY() + 1);
                else
                    setHomeY(getHomeY() - 1);
            }

            // Ensure the coordinates aren't going off screen
            if(getHomeX() > Main.SIZE_X){
                setHomeX(Main.SIZE_X);
            } else {
                if (getHomeX() < 0) {
                    setHomeX(0);
                }
            }

            if (getHomeY() > Main.SIZE_Y){
                setHomeY(Main.SIZE_Y);
            }else{
                if(getHomeY() < 0) {
                    setHomeY(0);
                }
            }

        }

        getHomeLocation().setX(getHomeX());
        getHomeLocation().setY(getHomeY());

    }

    // SELF GET/SET FUNCTIONS
    public void setFoodList(ArrayList<Food> f){
        foodList = f;
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
    public Circle getTargetCircle() {
        return targetCircle;
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

    public int getTargetX() {
        return targetX;
    }
    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public Rectangle getTargetLocation() {
        return targetLocation;
    }
    public void setTargetLocation(Rectangle targetLocation) {
        this.targetLocation = targetLocation;
    }

    public boolean hasTarget() {
        return targetBool;
    }
    public void setTargetBool(boolean t){
        targetBool = t;
    }

    public boolean isTargetFood(){
        return targetingFood;
    }
    public void setTargetingFood(boolean t){
        targetingFood = t;
    }

    public int getTargetFoodID(){
        return targetFoodID;
    }
    public void setTargetFoodID(int f){
        targetFoodID = f;
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

}

