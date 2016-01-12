package LS;

public class Configuration {
    private int ants;
    private int lizards;
    private int foodCount;
    private int foodTrees;
    private int shelterCount;
    private int obstacleCount;
    private int poolCount;

    public Configuration(int ants, int lizards, int foodCount, int foodTrees, int shelterCount, int obstacleCount,  int poolCount){
        setAnts(ants);
        setLizards(lizards);
        setFoodCount(foodCount);
        setFoodTrees(foodTrees);
        setShelterCount(shelterCount);
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

    public int getShelterCount() {
        return shelterCount;
    }

    public void setShelterCount(int shelterCount) {
        this.shelterCount = shelterCount;
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
}
