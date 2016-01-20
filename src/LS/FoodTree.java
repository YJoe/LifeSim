package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;

/**
 * FoodTree allows for a renewable food source within a World, generating fruit within the treeLeaf area
 */
public class FoodTree {
    private Circle leafCircle;
    private Obstacle treeTrunk;
    private ArrayList<FoodTree> treeList;
    private ArrayList<Food> foodList;
    private ArrayList<Water> waterList;
    private Group foodGroup;
    private int foodRate;
    private Random rand = new Random();

    /**
     * @param x X coordinate
     * @param y Y coordinate
     * @param foodList World foodList reference so that food can be added to the world
     * @param foodGroup World foodGroup reference so that food can be added visually to the world
     * @param waterList World waterList so that fruit can be ensured not to generate within the pools of water
     * @param trees World treeList so that fruit can not be generated within the bark of another FoodTree
     */
    public FoodTree(int x, int y, ArrayList<Food> foodList, Group foodGroup, ArrayList<Water> waterList, ArrayList<FoodTree> trees){
        // Create leaf circle
        setLeafCircle(new Circle(x, y, rand.nextInt(60) + 70));
        getLeafCircle().setFill(Color.rgb(0, 200, 0));
        getLeafCircle().setOpacity(0.15);

        // Create trunk
        setTreeTrunk(new Obstacle("TreeTrunk", x, y, 10, Color.rgb(100, 70, 30)));

        // Set water list reference
        setWaterList(waterList);

        // Set food list reference
        setFoodList(foodList);

        // Set foodTree list reference
        setTreeList(trees);

        // Set food group reference
        setFoodGroup(foodGroup);

        // Set a food production rate
        setFoodRate(rand.nextInt(300) + 400);
    }

    /**
     * Provide a chance for the tree to generate food dependent on the foodRate of the FoodTree
     * i.e there is a 1/foodRate chance that a single fruit will spawn per update
     */
    public void update(){
        // Create food if the random condition is met
        if (rand.nextInt(getFoodRate()) == 1){
            createFood();
        }
    }

    /**
     * Add fruit in a random location within the area of the FoodTree
     */
    public void createFood(){
        // define the area in which to pick random values
        int xMin = (int)(getLeafCircle().getCenterX() - getLeafCircle().getRadius());
        int yMin = (int)(getLeafCircle().getCenterY() - getLeafCircle().getRadius());

        // define the range in which to pick a random value
        int xRange = (int)(getLeafCircle().getRadius() * 2);
        int yRange = (int)(getLeafCircle().getRadius() * 2);

        // create a food object
        Food f;
        // run a do while loop in which the terminating condition is a valid food position
        do {
            f = new Food("Fruit", rand.nextInt(xRange) + xMin, rand.nextInt(yRange) + yMin, World.trackFoodID, rand.nextInt(2) + 2, Color.rgb(255, 0, 0));
        } while (   !Collision.overlapsAccurate(f.getImage(), getLeafCircle()) ||
                    Collision.overlapsAccurate(f.getImage(), getTreeTrunk().getImage()) ||
                    collidesWithWater(f.getImage()) || !isInScreen(f.getImage()) ||
                    isCollidingWithTrunk(f.getImage()));
        World.trackFoodID++;
        // add food to the correct lists
        getFoodGroup().getChildren().add(f.getImage());
        foodList.add(f);
    }

    /**
     * Check if a Circle collides with any of the Water obstacles within the waterList
     * @param c1 The Circle to check
     * @return If the Circle collides with any Water obstacle
     */
    public boolean collidesWithWater(Circle c1){
        // loop for all water
        for(int i = 0; i < waterList.size(); i++){
            if (Collision.overlapsAccurate(c1, waterList.get(i).getCircle())){
                // return true if the circle collides with any Water object
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a Circle is within the bounds of the screen
     * @param c1 The Circle to check
     * @return If the Circle is within the screen
     */
    public boolean isInScreen(Circle c1){
        // return if the circle is within the screen
        return (c1.getCenterX() < Main.SIZE_X && c1.getCenterX() > 0 &&
                c1.getCenterY() < Main.SIZE_Y && c1.getCenterY() > 25);
    }

    /**
     * Check if a Circle is colliding with any tree trunk within the World
     * @param c1 The Circle to check
     * @return If the Circle is colliding with any tree trunk
     */
    public boolean isCollidingWithTrunk(Circle c1){
        // loop for all trees
        for(FoodTree foodTree : treeList){
            if (Collision.overlapsEfficient(c1, foodTree.getTreeTrunk().getImage())){
                if (Collision.overlapsAccurate(c1, foodTree.getTreeTrunk().getImage())){
                    // return true if the circle overlaps any trunk
                    return true;
                }
            }
        }
        return false;
    }

    public Circle getLeafCircle() {
        return leafCircle;
    }

    public void setLeafCircle(Circle leafCircle) {
        this.leafCircle = leafCircle;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    public Group getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(Group foodGroup) {
        this.foodGroup = foodGroup;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setWaterList(ArrayList<Water> waterList) {
        this.waterList = waterList;
    }

    public Obstacle getTreeTrunk() {
        return treeTrunk;
    }

    public void setTreeTrunk(Obstacle treeTrunk) {
        this.treeTrunk = treeTrunk;
    }

    public void setTreeList(ArrayList<FoodTree> treeList) {
        this.treeList = treeList;
    }
}
