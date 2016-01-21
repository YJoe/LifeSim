package LS;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Configuration implements java.io.serializable as it is used within the construction of
 * World objects and needs to be saved and loaded by the user
 */
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

    /**
     * @param ants Amount of Ants to create when constructing a World
     * @param lizards Amount of Lizards to create when constructing a World
     * @param bears Amount of Bears to create when constructing a World
     * @param eagles Amount of Eagles to create when constructing a World
     * @param antHillCount Amount of AntHills to create when constructing a World
     * @param rockShelterCount Amount of RockShelters to create when constructing a World
     * @param caves Amount of Caves to create when constructing a World
     * @param nests Amount of Nests to create when constructing a World
     * @param foodCount Amount of Food to create when constructing a World
     * @param foodTrees Amount of FoodTrees to create when constructing a World
     * @param obstacleCount Amount of Obstacles to create when constructing a World
     * @param poolCount Amount of Pools of Water to create when constructing a World
     * @param eatList multidimensional list holding the eating rules for Animals to follow
     * @param huntList multidimensional list holding the hunting rules for Animals to follow
     */
    public Configuration(int ants, int lizards, int bears, int eagles,
                         int antHillCount, int rockShelterCount, int caves, int nests,
                         int foodCount, int foodTrees, int obstacleCount,  int poolCount,
                         ArrayList<ArrayList<Boolean>> eatList, ArrayList<ArrayList<Boolean>> huntList){
        // Set all attributes
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

    /**
     * @return Ant count
     */
    public int getAnts() {
        return ants;
    }

    /**
     * @param ants Amount of Ants
     */
    public void setAnts(int ants) {
        this.ants = ants;
    }

    /**
     * @return Lizard count
     */
    public int getLizards() {
        return lizards;
    }

    /**
     * @param lizards Amount of Lizards
     */
    public void setLizards(int lizards) {
        this.lizards = lizards;
    }

    /**
     * @return Food Count
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * @param foodCount Amount of Food
     */
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * @return FoodTree count
     */
    public int getFoodTrees() {
        return foodTrees;
    }

    /**
     * @param foodTrees Amount of FoodTrees
     */
    public void setFoodTrees(int foodTrees) {
        this.foodTrees = foodTrees;
    }

    /**
     * @return Obstacle count
     */
    public int getObstacleCount() {
        return obstacleCount;
    }

    /**
     * @param obstacleCount Amount of Obstacles
     */
    public void setObstacleCount(int obstacleCount) {
        this.obstacleCount = obstacleCount;
    }

    /**
     * @return Pool count
     */
    public int getPoolCount() {
        return poolCount;
    }

    /**
     * @param poolCount Amount of Pools
     */
    public void setPoolCount(int poolCount) {
        this.poolCount = poolCount;
    }

    /**
     * @return AntHill count
     */
    public int getAntHillCount() {
        return antHillCount;
    }

    /**
     * @param antHillCount Amount of AntHills
     */
    public void setAntHillCount(int antHillCount) {
        this.antHillCount = antHillCount;
    }

    /**
     * @return RockShelter count
     */
    public int getRockShelterCount() {
        return rockShelterCount;
    }

    /**
     * @param rockShelterCount Amount of RockShelters
     */
    public void setRockShelterCount(int rockShelterCount) {
        this.rockShelterCount = rockShelterCount;
    }

    /**
     * @return Bear count
     */
    public int getBears() {
        return bears;
    }

    /**
     * @param bears Amount of Bears
     */
    public void setBears(int bears) {
        this.bears = bears;
    }

    /**
     * @return Eagle count
     */
    public int getEagles() {
        return eagles;
    }

    /**
     * @param eagles Amount of Eagles
     */
    public void setEagles(int eagles) {
        this.eagles = eagles;
    }

    /**
     * @return Cave count
     */
    public int getCaves() {
        return caves;
    }

    /**
     * @param caves Amount of Caves
     */
    public void setCaves(int caves) {
        this.caves = caves;
    }

    /**
     * @return Nest count
     */
    public int getNests() {
        return nests;
    }

    /**
     * @param nests Amount of Nests
     */
    public void setNests(int nests) {
        this.nests = nests;
    }

    /**
     * @return EatList rules
     */
    public ArrayList<ArrayList<Boolean>> getEatList() {
        return eatList;
    }

    /**
     * @param eatList EatList rules
     */
    public void setEatList(ArrayList<ArrayList<Boolean>> eatList) {
        this.eatList = eatList;
    }

    /**
     * @return HuntList rules
     */
    public ArrayList<ArrayList<Boolean>> getHuntList() {
        return huntList;
    }

    /**
     * @param huntList HuntList rules
     */
    public void setHuntList(ArrayList<ArrayList<Boolean>> huntList) {
        this.huntList = huntList;
    }
}
