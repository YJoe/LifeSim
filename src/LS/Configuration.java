package LS;

import java.io.Serializable;

public class Configuration implements Serializable{
    private int ants;
    private int lizards;
    private int bears;
    private int eagles;
    private int foodCount;
    private int foodTrees;
    private int antHillCount;
    private int rockShelterCount;
    private int obstacleCount;
    private int poolCount;

    public Configuration(int ants, int lizards, int bears, int eagles, int foodCount, int foodTrees, int antHillCount, int rockShelterCount, int obstacleCount,  int poolCount){
        setAnts(ants);
        setLizards(lizards);
        setBears(bears);
        setEagles(eagles);
        setFoodCount(foodCount);
        setFoodTrees(foodTrees);
        setAntHillCount(antHillCount);
        setRockShelterCount(rockShelterCount);
        setObstacleCount(obstacleCount);
        setPoolCount(poolCount);
    }

    public int getAnts() {
        return ants;
    }

    public void setAnts(int ants) {
        this.ants = ants;
    }

    public int getLizards() {
        return lizards;
    }

    public void setLizards(int lizards) {
        this.lizards = lizards;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public int getFoodTrees() {
        return foodTrees;
    }

    public void setFoodTrees(int foodTrees) {
        this.foodTrees = foodTrees;
    }

    public int getObstacleCount() {
        return obstacleCount;
    }

    public void setObstacleCount(int obstacleCount) {
        this.obstacleCount = obstacleCount;
    }

    public int getPoolCount() {
        return poolCount;
    }

    public void setPoolCount(int poolCount) {
        this.poolCount = poolCount;
    }

    public int getAntHillCount() {
        return antHillCount;
    }

    public void setAntHillCount(int antHillCount) {
        this.antHillCount = antHillCount;
    }

    public int getRockShelterCount() {
        return rockShelterCount;
    }

    public void setRockShelterCount(int rockShelterCount) {
        this.rockShelterCount = rockShelterCount;
    }

    public int getBears() {
        return bears;
    }

    public void setBears(int bears) {
        this.bears = bears;
    }

    public int getEagles() {
        return eagles;
    }

    public void setEagles(int eagles) {
        this.eagles = eagles;
    }
}
