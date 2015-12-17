package LS;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Animal {
    private Circle image, smellCircle, targetCircle;
    private Rectangle targetLocation;
    private String species, name;
    private char symbol, gender;
    private int id, x, y, energy, smellRange, targetX, targetY, turnAngle, pathDistance;
    private int lastAngle = new Random().nextInt(360);
    private int randomAttemptTracker = 0;
    private double speed;
    private double dx;
    private double dy;
    private boolean targetbool;

    // Constructor
    public Animal(String speciesIn, String nameIn, char symbolIn, int idIn, int xIn, int yIn,
                  int energyIn, int smellIn, int sizeIn, double speedIn, int turnAngleIn){
        species = speciesIn;
        name = nameIn;
        symbol = symbolIn;
        id = idIn;
        x = xIn;
        y = yIn;
        energy = energyIn;
        speed = speedIn;
        image = new Circle(x, y, sizeIn);
        smellCircle = new Circle(x, y, smellIn);
        smellCircle.setFill(Color.rgb(0, 100, 100));
        smellCircle.setOpacity(0.5);
        Rectangle r = new Rectangle(0, 0, 5, 5);
        r.setFill(Color.rgb(255, 0, 0));
        setTargetLocation(r);
        setSmellRange(smellIn);
        turnAngle = turnAngleIn;
        pathDistance = smellIn;
        Random rand = new Random();
        if (rand.nextInt(2) == 1)
            gender = 'M';
        else
            gender = 'F';
    }

    // Main functions
    public void update(){
        target();
        move();
        getTargetLocation().setTranslateX(getTargetCircle().getCenterX() + getTargetCircle().getTranslateX());
        getTargetLocation().setTranslateY(getTargetCircle().getCenterY() + getTargetCircle().getTranslateY());
    }

    public void move(){
        // body
        this.getImage().setTranslateX(this.getImage().getTranslateX() + this.getDx());
        this.getImage().setTranslateY(this.getImage().getTranslateY() + this.getDy());
        // smell
        this.getSmellCircle().setTranslateX(this.getSmellCircle().getTranslateX() + this.getDx());
        this.getSmellCircle().setTranslateY(this.getSmellCircle().getTranslateY() + this.getDy());
    }

    public void target(){
        if (hasTarget()){
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

    public double getAngleTo(int tX,int tY){
        // The following function returns the angle from the centre
        // of the animal to the center of the target circle
        double thisX = getImage().getCenterX() + getImage().getTranslateX(),
                thisY = getImage().getCenterY() + getImage().getTranslateY();
        double theta = Math.atan2(tY - thisY, tX - thisX);
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public void directToTarget(){
        double angle = getAngleTo((int)(getTargetCircle().getCenterX() + getTargetCircle().getTranslateX()),
                (int)(getTargetCircle().getCenterY() + getTargetCircle().getTranslateY()));
        setDx((Math.cos(angle) * getSpeed()));
        setDy((Math.sin(angle) * getSpeed()));
    }

    public void checkCollideTarget(){
        int x1pos = (int) (this.getImage().getCenterX() + this.getImage().getTranslateX());
        int y1pos = (int) (this.getImage().getCenterY() + this.getImage().getTranslateY());

        int x2pos = (int) (getTargetCircle().getCenterX() + getTargetCircle().getTranslateX());
        int y2pos = (int) (getTargetCircle().getCenterY() + getTargetCircle().getTranslateY());

        //      a         b             c
        // (x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2
        float a = (float) Math.pow((x1pos - x2pos), 2);
        float b = (float) Math.pow((y1pos - y2pos), 2);
        float c = (float) Math.pow(getImage().getRadius() + getTargetCircle().getRadius(), 2);

        if (a + b <= c){
            removeTarget();
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


    // SELF GET/SET FUNCTIONS
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

    public boolean hasTarget() {
        return targetbool;
    }
    public void setTargetBool(boolean t){
        targetbool = t;
    }

    public int getTurnAngle(){
        return turnAngle;
    }
    public void setTurnAngle(int t){
        turnAngle = t;
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



    // INFORMATIONAL
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

