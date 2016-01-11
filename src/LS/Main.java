package LS;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public static int SIZE_X = 1000, SIZE_Y = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create root group
        Group root = new Group();
        Scene scene = new Scene(root, SIZE_X, SIZE_Y + 50, Color.rgb(255, 255, 255));

        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Open Resource File");
        //fileChooser.showOpenDialog(primaryStage);

        // Create world
        int animalCount = 200;
        int foodCount = 0;
        int foodTrees = 50;
        int shelterCount = 1;
        int obstacleCount = 0;
        int poolCount = 1;
        World world = new World(root, animalCount, foodTrees, foodCount, shelterCount, obstacleCount, poolCount);

        //world.toggleSmellCircles();
        //world.toggleTargetSquares();
        //world.toggleHomeSquares();
        //world.toggleStatBars();
        //world.toggleShelterStatBars();
        world.toggleAnimalLabels();

        // Create menu system object
        SimulationMenu menu = new SimulationMenu(primaryStage);
        menu.togglePaused();
        root.getChildren().add(menu.getMenuBar());

        // Create key press handler for scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.SPACE) {
                    menu.togglePaused();
                }else{
                    if (ke.getCode() == KeyCode.DIGIT1){
                        world.toggleSmellCircles();
                    }else {
                        if (ke.getCode() == KeyCode.DIGIT2) {
                            world.toggleTargetSquares();
                        } else {
                            if (ke.getCode() == KeyCode.DIGIT3) {
                                world.toggleHomeSquares();
                            } else {
                                if (ke.getCode() == KeyCode.DIGIT4) {
                                    world.toggleStatBars();
                                } else {
                                    if (ke.getCode() == KeyCode.DIGIT5) {
                                        world.toggleAnimalLabels();
                                    } else {
                                        if (ke.getCode() == KeyCode.DIGIT6) {
                                            world.toggleAnimals();
                                        } else {
                                            if (ke.getCode() == KeyCode.DIGIT7) {
                                                System.out.println("_______________");
                                                for(int i = 0; i < world.getFoodList().size(); i++){
                                                    System.out.println(world.getFoodList().get(i).getID());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        // Create keyFrame
        KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if (!menu.isPaused()) {
                    world.update();
                }
            }
        });

        // Some other stuff
        TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}