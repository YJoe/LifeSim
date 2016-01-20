package LS;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main extends javafx.application.Application providing the Graphical User Interface (GUI) for the LifeSimulator application
 * Main consists of a static main function and an override of javafx.application.Application's start function
 */
public class Main extends Application {
    public static int SIZE_X = 1000, SIZE_Y = 600;

    /**
     * The main function launching the arguments passed
     * @param args arguments to pass on to the launch function
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creating the GUI through creating a root Group from which all nodes will link the Scene is set and is
     * passed to the parameter primaryStage
     * @param primaryStage the primaryStage in which the GUI will be visible
     */
    @Override
    public void start(Stage primaryStage) {
        // Create root group
        Group root = new Group();
        Scene scene = new Scene(root, SIZE_X, SIZE_Y + 50, Color.rgb(255, 255, 255));

        // Create menu system object
        SimulationMenu menu = new SimulationMenu(primaryStage, root);
        menu.getWorldStatsBar();
        menu.togglePaused();

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
                                                menu.createWorld();
                                            } else {
                                                if (ke.getCode() == KeyCode.N){
                                                    menu.newConfiguration();
                                                } else {
                                                    if (ke.getCode() == KeyCode.O){
                                                        menu.openConfiguration();
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
            }
        });

        // Create keyFrame
        KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if (menu.getCurrentWorld() != null) {
                    if (!menu.isPaused()) {
                        menu.getCurrentWorld().update();
                        menu.getWorldStatsBar().setDateString(menu.getCurrentWorld().getDateString());
                    }
                } else {
                    menu.getRoot().getChildren().clear();
                    menu.getRoot().getChildren().add(menu.getWorldStatsBar().getGroup());
                    Text notLoaded = new Text("NO WORLD LOADED");
                    notLoaded.setTranslateX(10);
                    notLoaded.setTranslateY(Main.SIZE_Y + 38);
                    notLoaded.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
                    notLoaded.setFill(Color.rgb(200, 200, 200));
                    menu.getRoot().getChildren().add(notLoaded);
                    menu.getRoot().getChildren().add(menu.getMenuBar());
                }
            }
        });

        // Some other stuff
        TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}