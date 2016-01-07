package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;

public class FoodTree {
    private Circle leafCircle;
    private TreeTrunk treeTrunk;
    private ArrayList<Food> foodList;
    private ArrayList<Water> waterList;
    private Group foodGroup;
    private int foodRate;
    private Random rand = new Random();

    public FoodTree(int x, int y, ArrayList<Food> foodList, Group foodGroup, ArrayList<Water> waterList){
        // Create leaf circle
        setLeafCircle(new Circle(x, y, rand.nextInt(60) + 70));
        getLeafCircle().setFill(Color.rgb(0, 200, 0));
        getLeafCircle().setOpacity(0.15);

        // Create trunk
        setTreeTrunk(new TreeTrunk(x, y, 10));

        // Set water list reference
        setWaterList(waterList);

        // Set food list reference
        setFoodList(foodList);

        // Set food group reference
        setFoodGroup(foodGroup);

        // Set a food production rate
        setFoodRate(2);//rand.nextInt(500) + 900);
    }

    //
    public void update(){
        if (rand.nextInt(getFoodRate()) == 1){
            createFood();
        }
    }

    public void createFood(){
        int xMin = (int)(getLeafCircle().getCenterX() - getLeafCircle().getRadius());
        int yMin = (int)(getLeafCircle().getCenterY() - getLeafCircle().getRadius());

        int xRange = (int)(getLeafCircle().getRadius() * 2);
        int yRange = (int)(getLeafCircle().getRadius() * 2);

        Food f;
        do {
            f = new Fruit(rand.nextInt(xRange) + xMin, rand.nextInt(yRange) + yMin, World.trackFoodID);
        } while (   !Collision.overlapsAccurate(f.getImage(), getLeafCircle()) ||
                    Collision.overlapsAccurate(f.getImage(), getTreeTrunk().getImage()) ||
                    collidesWithWater(f.getImage()));
        World.trackFoodID++;
        getFoodGroup().getChildren().add(f.getImage());
        foodList.add(f);
    }

    public boolean collidesWithWater(Circle c1){
        for(int i = 0; i < waterList.size(); i++){
            if (Collision.overlapsAccurate(c1, waterList.get(i).getCircle())){
                return true;
            }
        }
        return false;
    }

    //
    public Circle getLeafCircle() {
        return leafCircle;
    }

    public void setLeafCircle(Circle leafCircle) {
        this.leafCircle = leafCircle;
    }



    public ArrayList<Food> getFoodList() {
        return foodList;
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

    public ArrayList<Water> getWaterList() {
        return waterList;
    }

    public void setWaterList(ArrayList<Water> waterList) {
        this.waterList = waterList;
    }

    public TreeTrunk getTreeTrunk() {
        return treeTrunk;
    }

    public void setTreeTrunk(TreeTrunk treeTrunk) {
        this.treeTrunk = treeTrunk;
    }
}
