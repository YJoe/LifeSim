package LS;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {
    public static int SIZE_X = 1000, SIZE_Y = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create root group
        Group root = new Group();
        Scene scene = new Scene(root, SIZE_X, SIZE_Y, Color.rgb(255, 255, 255));

        // Create world
        World world = new World(root, 1, 1, 1);

        world.toggleSmellCircles();
        world.toggleTargetSquares();
        world.toggleHomeSquares();
        world.toggleStatBars();

        // Create menu system object
        SimulationMenu menu = new SimulationMenu(primaryStage);
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