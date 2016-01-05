package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;

public class FoodTree {
    private Circle leafCircle, trunkCircle;
    private ArrayList<Food> foodList;
    private Group foodGroup;
    private int trackID;
    private Random rand = new Random();

    public FoodTree(int x, int y, ArrayList<Food> foodList, int trackID, Group foodGroup){
        // Create leaf circle
        setLeafCircle(new Circle(x, y, 100));
        getLeafCircle().setFill(Color.rgb(0, 200, 0));
        getLeafCircle().setOpacity(0.3);

        // Create trunk circle
        setTrunkCircle(new Circle(x, y, 10));
        getTrunkCircle().setFill(Color.rgb(100, 50, 0));

        // Set food list reference
        setFoodList(foodList);

        // Set food group reference
        setFoodGroup(foodGroup);

        // Set ID tracked reference
        setTrackID(trackID);
    }

    //
    public void update(){
        if (rand.nextInt(500) == 1){
            createFood();
        }
    }

    public void createFood(){
        // TODO: stop food trees from spawning meat... its weird. Make an apple class or something
        int xMin = (int)(getLeafCircle().getCenterX() - getLeafCircle().getRadius());
        int yMin = (int)(getLeafCircle().getCenterY() - getLeafCircle().getRadius());

        int xRange = (int)(getLeafCircle().getRadius() * 2);
        int yRange = (int)(getLeafCircle().getRadius() * 2);

        Food f;
        do {
            f = new Meat(rand.nextInt(xRange) + xMin, rand.nextInt(yRange) + yMin, getTrackID(), 4);
        } while (!Collision.overlapsAccurate(f.getImage(), getLeafCircle()) || Collision.overlapsAccurate(f.getImage(), getTrunkCircle()));
        setTrackID(getTrackID() + 1);
        getFoodGroup().getChildren().add(f.getImage());
        foodList.add(f);
    }

    //
    public Circle getLeafCircle() {
        return leafCircle;
    }

    public void setLeafCircle(Circle leafCircle) {
        this.leafCircle = leafCircle;
    }

    public Circle getTrunkCircle() {
        return trunkCircle;
    }

    public void setTrunkCircle(Circle trunkCircle) {
        this.trunkCircle = trunkCircle;
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

    public int getTrackID() {
        return trackID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }
}
