package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Ant provides an extension to the abstract class Animal providing Animal with Ant specific values and functions
 */
public class Ant extends Animal{
    private Random rand = new Random();
    private float baseSpeed = (float)(0.1), baseMetabolism = (float)(0.002);
    private int baseSize = 1, baseTurnAngle = 30, baseStrength = 3;
    private Color bodyColour = Color.rgb(50, 50, 50);
    private Color smellColour = Color.rgb(0, 100, 100);
    private int maxAge = rand.nextInt(5) + 5, breedAge = 1, speedChangeAge = (maxAge/2) + rand.nextInt(4) - 2;

    /**
     * @param x X coordinate
     * @param y Y coordinate
     * @param id Unique identifier for  the Animal
     * @param dayBorn The day (0 - 365) in which the Animal was born
     * @param yearBorn The year in which the Animal was born
     * @param foodGroup Food group reference, a node of the root node
     * @param animalGroup Animal group reference, a node of the root node
     * @param waterGroup Water group reference, a node of the root node
     * @param worldRef World object in which the Animal lives
     * @param animalList List of all Animals also living in the world
     * @param foodList List of all Food within the world
     * @param waterList List of all Water within the world
     * @param obstacleList List of all Obstacles within the world
     * @param shelterList List of all Shelters within the world
     * @param animalSmellRef Animal smellCircle group reference, a node of the root node
     * @param animalStatsRef Animal statBar group reference, a node of the root node
     * @param animalLabelRef Animal label group reference, a node of the root node
     * @param animalTargetRef Animal target group reference, a node of the root node
     * @param animalHomeLocationRef Animal home group reference, a node of the root node
     * @param configuration Configuration in which the animal follows hunting and eating rules of
     */
    public Ant(int x, int y, int id, int dayBorn, int yearBorn, Group foodGroup, Group animalGroup, Group waterGroup,
               World worldRef, ArrayList<Animal> animalList, ArrayList<Food> foodList, ArrayList<Water> waterList,
               ArrayList<Obstacle> obstacleList, ArrayList<Shelter> shelterList, Group animalSmellRef,
               Group animalStatsRef, Group animalLabelRef, Group animalTargetRef, Group animalHomeLocationRef,
               Configuration configuration){

        super("Ant", 'A', id, dayBorn, yearBorn, 2000, x, y, foodGroup, animalGroup, waterGroup, worldRef, animalList,
                foodList, waterList, obstacleList, shelterList, animalSmellRef, animalStatsRef, animalLabelRef,
                animalTargetRef, animalHomeLocationRef, configuration);

        String [] names_m = {"Antdrew", "Anty", "Antain", "Antanas", "Antar", "Anturas", "Antavas"};
        String [] names_f = {"Anttoinette", "Antalia", "Anta", "Anthia", "Antalia", "Antandra", "Antia", "Antheemia"};
        giveName(names_m, names_f);

        setBreedAge(breedAge);
        setSpeedChangeAge(speedChangeAge);
        setMaxAge(maxAge);

        // Create smell attributes
        setSmellRange(rand.nextInt(30) + 50 - 15);
        setSmellCircle(new Circle(x, y, getSmellRange()));
        getSmellCircle().setFill(smellColour);
        getSmellCircle().setOpacity(0.3);
        setPathDistance(getSmellRange());

        // Create body attributes
        setSize(baseSize + (float)(rand.nextInt(5) * 0.2));
        setImage(new Circle(x, y, getSize()));
        getImage().setFill(bodyColour);

        // Set a random speed
        setSpeed(baseSpeed + (rand.nextInt(10) * 0.1));
        setOriginalSpeed(getSpeed());

        // Set a random turning angle
        setTurnAngle(baseTurnAngle + (rand.nextInt(20)));

        // Set a random metabolism
        setMetabolism((float)(baseMetabolism + (rand.nextInt(4) * 0.0005)));

        // Set strength
        setStrength(baseStrength + rand.nextInt(2));

        // Create food inventory
        setFoodInventory(new Inventory(getStrength()/2 + rand.nextInt(2), getStrength() + (rand.nextInt(2))));

        // Create water inventory
        setWaterInventory(new Inventory(getStrength()/2 + rand.nextInt(2), getStrength() + (rand.nextInt(2))));
    }

    /**
     * @param x X coordinate
     * @param y Y coordinate
     * @param id Unique identifier for  the Animal
     * @param dayBorn The day (0 - 365) in which the Animal was born
     * @param yearBorn The year in which the Animal was born
     * @param foodGroup Food group reference, a node of the root node
     * @param animalGroup Animal group reference, a node of the root node
     * @param waterGroup Water group reference, a node of the root node
     * @param worldRef World object in which the Animal lives
     * @param animalList List of all Animals also living in the world
     * @param foodList List of all Food within the world
     * @param waterList List of all Water within the world
     * @param obstacleList List of all Obstacles within the world
     * @param shelterList List of all Shelters within the world
     * @param animalSmellRef Animal smellCircle group reference, a node of the root node
     * @param animalStatsRef Animal statBar group reference, a node of the root node
     * @param animalLabelRef Animal label group reference, a node of the root node
     * @param animalTargetRef Animal target group reference, a node of the root node
     * @param animalHomeLocationRef Animal home group reference, a node of the root node
     * @param configuration Configuration in which the animal follows hunting and eating rules of
     * @param smell The range in which the Animal percievies its world
     * @param size The size of the Animals body
     * @param speed The speed at which the Animal can move
     * @param metabolism The rate at which hunger and thirst grow
     * @param strength Strength of the Animal used to determine the winner of fights and size of an
     *                 Animal's inventory space
     * @param gender The gender of the Animal
     * @param name The name of the Animal
     */
    public Ant(int x, int y, char gender, String name, double speed, float metabolism, int strength, int smell,
               int size, int id, int dayBorn, int yearBorn,  Group foodGroup, Group animalGroup, Group waterGroup,
               World worldRef, ArrayList<Animal> animalList, ArrayList<Food> foodList, ArrayList<Water> waterList,
               ArrayList<Obstacle> obstacleList, ArrayList<Shelter> shelterList, Group animalSmellRef,
               Group animalStatsRef, Group animalLabelRef, Group animalTargetRef, Group animalHomeLocationRef,
               Configuration configuration){

        super("Ant", 'A', id, dayBorn, yearBorn, 2000, x, y, gender, name, speed, metabolism, strength, smell, size,
                foodGroup, animalGroup, waterGroup, worldRef, animalList, foodList, waterList, obstacleList,
                shelterList, animalSmellRef, animalStatsRef, animalLabelRef, animalTargetRef, animalHomeLocationRef,
                configuration, Color.rgb(50, 50, 50));
    }

    @Override
    public void checkShelters(){
        for(int i = 0; i < getShelterList().size(); i++){
            if(getShelterList().get(i).getType().equals("AntHill")) {
                if (Collision.overlapsEfficient(getSmellCircle(), getShelterList().get(i).getImage())) {
                    if (Collision.overlapsAccurate(getSmellCircle(), getShelterList().get(i).getImage())) {
                        setHome(new Target(getShelterList().get(i).getX(), getShelterList().get(i).getY()), getShelterList().get(i).getID());
                        break;
                    }
                }
            }
        }
    }
}
