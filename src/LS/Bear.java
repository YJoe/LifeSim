package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Bear provides an extension to the abstract class Animal providing Animal with Ant specific values and functions
 */
public class Bear extends Animal{
    private Random rand = new Random();
    private float baseSpeed = (float)(0.2), baseMetabolism = (float)(0.00001);
    private int baseSize = 20, baseTurnAngle = 30, baseStrength = 5, baseMemory = 10;
    private Color bodyColour = Color.rgb(200, 100, 0);
    private Color smellColour = Color.rgb(0, 100, 100);
    private int maxAge = rand.nextInt(15) + 15, breedAge = 2, speedChangeAge = (maxAge/2) + rand.nextInt(4) - 2;

    /**
     * @param x X coordinate
     * @param y Y coordinate
     * @param id Unique identifier for  the Animal
     * @param dayBorn The day (0 - 365) in which the Animal was born
     * @param yearBorn The year in which the Animal was born
     * @param world the World object reference
     */
    public Bear(int x, int y, int id, int dayBorn, int yearBorn, World world){

        super("Bear", 'B', id, dayBorn, yearBorn, 4000, x, y, world);

        String [] names_m = {"Beary", "Barry", "Barnaby", "Pooh"};
        String [] names_f = {"Bearnadette", "Beth", "Berlinda", "Bertha"};
        giveName(names_m, names_f);

        setBreedAge(breedAge);
        setSpeedChangeAge(speedChangeAge);
        setMaxAge(maxAge);

        // Create smell attributes
        setSmellRange(rand.nextInt(50) + 100 - 15);
        setSmellCircle(new Circle(x, y, getSmellRange()));
        getSmellCircle().setFill(smellColour);
        getSmellCircle().setOpacity(0.3);
        setPathDistance(getSmellRange());

        // Create body attributes
        setSize(baseSize + rand.nextInt(10));
        setImage(new Circle(x, y, getSize()));
        getImage().setFill(bodyColour);

        // Set a random speed
        setSpeed(baseSpeed + (rand.nextInt(3) * 0.1));
        setOriginalSpeed(getSpeed());

        // Set a random turning angle
        setTurnAngle(baseTurnAngle + (rand.nextInt(20)));

        // Set a random metabolism
        setMetabolism((float)(baseMetabolism + (rand.nextInt(4) * 0.00005)));

        // Set strength
        setStrength(baseStrength + rand.nextInt(10));

        // Create food inventory
        setFoodInventory(new Inventory(getStrength()/2 + rand.nextInt(2), getStrength()/2 + (rand.nextInt(2))));

        // Create water inventory
        setWaterInventory(new Inventory(getStrength()/2 + rand.nextInt(2), getStrength()/2 + (rand.nextInt(2))));
    }

    /**
     * @param x X coordinate
     * @param y Y coordinate
     * @param id Unique identifier for  the Animal
     * @param dayBorn The day (0 - 365) in which the Animal was born
     * @param yearBorn The year in which the Animal was born
     * @param smell The range in which the Animal percievies its world
     * @param size The size of the Animals body
     * @param speed The speed at which the Animal can move
     * @param metabolism The rate at which hunger and thirst grow
     * @param strength Strength of the Animal used to determine the winner of fights and size of an
     *                 Animal's inventory space
     * @param gender The gender of the Animal
     * @param name The name of the Animal
     * @param world the World object reference
     */
    public Bear(int x, int y, char gender, String name, double speed, float metabolism, int strength, int smell,
                 int size, int id, int dayBorn, int yearBorn, World world){

        super("Bear", 'B', id, dayBorn, yearBorn, 4000, x, y, gender, name, speed, metabolism, strength, smell, size,
                Color.rgb(200, 100, 0), world);
    }
    /**
     * Override function for check shelters specific to the Bear
     */
    @Override
    public void checkShelters(){
        // loop for all shelters and check if a shelter of the correct type was found
        for(int i = 0; i < getWorldRef().getShelterList().size(); i++){
            if(getWorldRef().getShelterList().get(i).getType().equals("Cave")) {
                if (Collision.overlapsEfficient(getSmellCircle(), getWorldRef().getShelterList().get(i).getImage())) {
                    if (Collision.overlapsAccurate(getSmellCircle(), getWorldRef().getShelterList().get(i).getImage())) {
                        setHome(new Target(getWorldRef().getShelterList().get(i).getX(), getWorldRef().getShelterList().get(i).getY()), getWorldRef().getShelterList().get(i).getID());
                        break;
                    }
                }
            }
        }
    }
}
