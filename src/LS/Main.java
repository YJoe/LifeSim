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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application {
    public static int SIZE_X = 1000, SIZE_Y = 600;
    //public static int SIZE_X = 300, SIZE_Y = 200;


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

        // Create menu system object
        SimulationMenu menu = new SimulationMenu(primaryStage);
        menu.togglePaused();

        menu.setConfiguration(new Configuration(0, 10, 0, 5, 2, 1, 0, 1));
        menu.createWorld(root);

        // Create key press handler for scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.SPACE) {
                    menu.togglePaused();
                }else{
                    if (ke.getCode() == KeyCode.DIGIT1){
                        menu.getCurrentWorld().toggleSmellCircles();
                    }else {
                        if (ke.getCode() == KeyCode.DIGIT2) {
                            menu.getCurrentWorld().toggleTargetSquares();
                        } else {
                            if (ke.getCode() == KeyCode.DIGIT3) {
                                menu.getCurrentWorld().toggleHomeSquares();
                            } else {
                                if (ke.getCode() == KeyCode.DIGIT4) {
                                    menu.getCurrentWorld().toggleStatBars();
                                } else {
                                    if (ke.getCode() == KeyCode.DIGIT5) {
                                        menu.getCurrentWorld().toggleAnimalLabels();
                                    } else {
                                        if (ke.getCode() == KeyCode.DIGIT6) {
                                            menu.getCurrentWorld().toggleAnimals();
                                        }else {
                                            if (ke.getCode() == KeyCode.R) {
                                                menu.createWorld(root);
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
                    menu.getCurrentWorld().update();
                }
            }
        });

        // Some other stuff
        TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}