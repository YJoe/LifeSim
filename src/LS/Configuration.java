package LS;

import java.io.Serializable;
import java.util.ArrayList;

public class Configuration implements Serializable{
    private int ants;
    private int lizards;
    private int bears;
    private int eagles;
    private int antHillCount;
    private int rockShelterCount;
    private int caves;
    private int nests;
    private int foodCount;
    private int foodTrees;
    private int obstacleCount;
    private int poolCount;
    private ArrayList<ArrayList<Boolean>> eatList = new ArrayList<>();
    private ArrayList<ArrayList<Boolean>> huntList = new ArrayList<>();

    public Configuration(int ants, int lizards, int bears, int eagles,
                         int antHillCount, int rockShelterCount, int caves, int nests,
                         int foodCount, int foodTrees, int obstacleCount,  int poolCount,
                         ArrayList<ArrayList<Boolean>> eatList, ArrayList<ArrayList<Boolean>> huntList){
        setAnts(ants);
        setLizards(lizards);
        setBears(bears);
        setEagles(eagles);
        setAntHillCount(antHillCount);
        setRockShelterCount(rockShelterCount);
        setCaves(caves);
        setNests(nests);
        setFoodCount(foodCount);
        setFoodTrees(foodTrees);
        setObstacleCount(obstacleCount);
        setPoolCount(poolCount);
        setEatList(eatList);
        setHuntList(huntList);
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

    public int getCaves() {
        return caves;
    }

    public void setCaves(int caves) {
        this.caves = caves;
    }

    public int getNests() {
        return nests;
    }

    public void setNests(int nests) {
        this.nests = nests;
    }

    public ArrayList<ArrayList<Boolean>> getEatList() {
        return eatList;
    }

    public void setEatList(ArrayList<ArrayList<Boolean>> eatList) {
        this.eatList = eatList;
    }

    public ArrayList<ArrayList<Boolean>> getHuntList() {
        return huntList;
    }

    public void setHuntList(ArrayList<ArrayList<Boolean>> huntList) {
        this.huntList = huntList;
    }
}
