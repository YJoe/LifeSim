package LS;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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

        //world.toggleSmellCircles();
        //world.toggleTargetSquare();
        //world.toggleHomeSquares();

        // Create menu system object
        SimulationMenu menu = new SimulationMenu(primaryStage);
        root.getChildren().add(menu.getMenuBar());

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