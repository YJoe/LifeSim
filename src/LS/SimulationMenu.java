package LS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/**
 * SimulationMenu holds the MenuBar and ToolBar options and controls the execution of the world
 */
public class SimulationMenu {
    private MenuBar menuBar;
    private Group root;
    private Rectangle backBar;
    private Text date;
    private Configuration configuration;
    private World currentWorld;
    private Group buttonGroup = new Group();
    private Button reset, view, play;
    private boolean isPaused = false;

    /**
     * Con structor of the Simulation menu
     * @param primaryStage The primary stage of the GU, used here to set the length of the MenuBar
     * @param root The root node in which all other nodes will be attached
     */
    public SimulationMenu(Stage primaryStage, Group root){
        //Top Menu Bar
        menuBar = new MenuBar();
        menuBar.setOpacity(1);
        setRoot(root);

        // create buttons
        setReset(new Button("Reset"));
        getReset().setTranslateX(10);
        getReset().setTranslateY(Main.SIZE_Y + 13);
        getReset().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createWorld();
            }
        });
        setView(new Button("View"));
        getView().setTranslateX(70);
        getView().setTranslateY(Main.SIZE_Y + 13);
        getView().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view();
            }
        });
        setPlay(new Button("Play"));
        getPlay().setTranslateX(126);
        getPlay().setTranslateY(Main.SIZE_Y + 13);
        getPlay().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                togglePaused();
                if (isPaused()){
                    getPlay().setText("Play");
                } else{
                    getPlay().setText("Pause");
                }
            }
        });

        // create backBar
        setBackBar(new Rectangle(0, Main.SIZE_Y, Main.SIZE_X, 50));
        getBackBar().setFill(Color.rgb(50, 50, 50));

        // create date
        setDate(new Text());
        getDate().setFont(Font.font ("Verdana", 30));
        getDate().setTranslateX(185);
        getDate().setTranslateY(Main.SIZE_Y + 35);
        getDate().setFill(Color.rgb(200, 200, 200));

        getRoot().getChildren().add(getBackBar());
        getRoot().getChildren().add(getDate());

        // File
        javafx.scene.control.Menu file = new javafx.scene.control.Menu("File");
        MenuItem fileNewConfig = new MenuItem("New Configuration");
        MenuItem fileOpenConfig = new MenuItem("Open Configuration");
        MenuItem fileSave = new MenuItem("Save");
        MenuItem fileSaveAs = new MenuItem("Save As");
        file.getItems().add(fileNewConfig);
        file.getItems().add(fileOpenConfig);
        file.getItems().add(fileSave);
        file.getItems().add(fileSaveAs);
        fileNewConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                newConfiguration();
            }
        });
        fileOpenConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                openConfiguration();
            }
        });
        fileSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (getCurrentWorld() != null) {
                    saveConfiguration();
                }
                 else {
                    errorWindow("No world loaded");
                }
            }
        });
        fileSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(getCurrentWorld() != null){
                    saveConfigurationAs();
                }
                else{
                    errorWindow("No world loaded");
                }
            }
        });

        // Edit
        javafx.scene.control.Menu edit = new javafx.scene.control.Menu("Edit");
        MenuItem modifyLifeForm = new MenuItem("Edit Life Form");
        MenuItem removeLifeForm = new MenuItem("Remove Life Form");
        MenuItem addLifeForm = new MenuItem("Add Life Form");
        MenuItem editConfig = new MenuItem("Edit Configuration");
        edit.getItems().add(editConfig);
        edit.getItems().add(addLifeForm);
        edit.getItems().add(modifyLifeForm);
        edit.getItems().add(removeLifeForm);
        modifyLifeForm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (getCurrentWorld() != null) {
                    editAnimal();
                }
                else {
                    errorWindow("No world loaded");
                }
            }
        });
        removeLifeForm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (getCurrentWorld() != null) {
                    removeAnimal();
                }
                else {
                    errorWindow("No world loaded");
                }
            }
        });
        addLifeForm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (getCurrentWorld() != null) {
                    addAnimal();
                }
                else {
                    errorWindow("No world loaded");
                }
            }
        });
        editConfig.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (getCurrentWorld() != null) {
                    editFoodChain();
                }
                else {
                    errorWindow("No world loaded");
                }
            }
        });

        // View
        javafx.scene.control.Menu view = new javafx.scene.control.Menu("View");
        MenuItem displayConfig = new MenuItem("Display Configuration");
        MenuItem displayLifeForms = new MenuItem("Display Life Form Stats");
        MenuItem displayMapInfo = new MenuItem("Display Map Stats");
        view.getItems().add(displayLifeForms);
        view.getItems().add(displayConfig);
        view.getItems().add(displayMapInfo);
        displayConfig.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (getCurrentWorld() != null) {
                    viewConfiguration();
                }
                else {
                    errorWindow("No world loaded");
                }
            }
        });
        displayLifeForms.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (getCurrentWorld() != null) {
                    viewAnimalStats();
                }
                else {
                    errorWindow("No world loaded");
                }
            }
        });
        displayMapInfo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
            }
        });

        // Graph
        Menu graph = new Menu("Graph");
        MenuItem population = new MenuItem("Population");
        graph.getItems().add(population);
        population.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(getCurrentWorld() != null) {
                    populationGraph();
                } else {
                    errorWindow("No world loaded");
                }
            }
        });

        // Simulate
        javafx.scene.control.Menu simulate = new javafx.scene.control.Menu("Simulate");
        MenuItem pausePlay = new MenuItem("Pause/Play");
        MenuItem restart = new MenuItem("Restart");
        simulate.getItems().add(pausePlay);
        simulate.getItems().add(restart);
        pausePlay.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                isPaused = !isPaused;
            }
        });
        restart.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (getCurrentWorld() != null) {
                    createWorld();
                }
                else{
                    errorWindow("No world loaded");
                }
            }
        });

        // Help
        Menu help = new Menu("Help");
        MenuItem key = new MenuItem("Key");
        MenuItem info = new MenuItem("Info");
        help.getItems().add(key);
        help.getItems().add(info);
        key.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create a stage and grid pane
                Stage stage = new Stage();
                Group root = new Group();

                // define the heading
                Text head = new Text("\nKey");
                head.setFont(Font.font("Verdana", 20));
                head.setTranslateX(10);
                head.setTranslateY(10);
                root.getChildren().add(head);

                Text antText = new Text("Ant");
                Text lizardText = new Text("Lizard");
                Text eagleText = new Text("Eagle");
                Text bearText = new Text("Bear");
                antText.setTranslateX(50);lizardText.setTranslateX(50);eagleText.setTranslateX(50);bearText.setTranslateX(50);
                antText.setTranslateY(55);lizardText.setTranslateY(105);eagleText.setTranslateY(155);bearText.setTranslateY(205);

                Text rockText = new Text("Rock");
                Text treeText = new Text("Tree");
                Text waterText = new Text("Water");
                Text antHillText = new Text("AntHill");
                Text lizardRockText = new Text("RockShelter");
                Text eagleNestText = new Text("Nest");
                Text bearCaveText = new Text("Cave");

                rockText.setTranslateX(50); treeText.setTranslateX(50); waterText.setTranslateX(50); antHillText.setTranslateX(250);
                lizardRockText.setTranslateX(250); eagleNestText.setTranslateX(250); bearCaveText.setTranslateX(250);

                rockText.setTranslateY(255); treeText.setTranslateY(305); waterText.setTranslateY(405); antHillText.setTranslateY(55);
                lizardRockText.setTranslateY(105); eagleNestText.setTranslateY(155); bearCaveText.setTranslateY(205);

                root.getChildren().addAll(rockText, treeText, waterText, antHillText, lizardRockText, eagleNestText, bearCaveText);

                // Animal images
                root.getChildren().addAll(antText, lizardText, eagleText, bearText);
                Circle antImage = new Circle(200, 50, 2); antImage.setFill(Color.rgb(50, 50, 50));
                Circle lizardImage = new Circle(200, 100, 5); lizardImage.setFill(Color.rgb(0, 150, 40));
                Circle eagleImage = new Circle(200, 150, 9); eagleImage.setFill(Color.rgb(200, 200, 200));
                Circle bearImage = new Circle(200, 200, 25); bearImage.setFill(Color.rgb(200, 100, 0));
                root.getChildren().addAll(antImage, lizardImage, eagleImage, bearImage);

                // World images
                Circle rockImage = new Circle(200, 250, 10); rockImage.setFill(Color.rgb(150, 150, 150));
                Circle treeBarkImage = new Circle(200, 300, 5); treeBarkImage.setFill(Color.rgb(100, 70, 30));
                Circle treeLeafImage = new Circle(200, 300, 40); treeLeafImage.setFill(Color.rgb(0, 200, 0)); treeLeafImage.setOpacity(0.15);
                Circle waterOuter = new Circle(200, 400, 50); waterOuter.setFill(Color.rgb(120, 120, 255));
                Circle waterInner = new Circle(200, 400, 40); waterInner.setFill(Color.rgb(50, 50, 200));
                root.getChildren().addAll(rockImage, treeBarkImage, treeLeafImage, waterOuter, waterInner);

                // Shelter images
                Circle antHillImage = new Circle(400, 55 , 20); antHillImage.setFill(Color.rgb(220, 200, 160));
                Circle lizardRockImage = new Circle(400, 105, 20); lizardRockImage.setFill(Color.rgb(100, 100, 100));
                Circle bearCaveImage = new Circle(400, 155, 20); bearCaveImage.setFill(Color.rgb(150, 150, 170));
                Circle eagleNestImage = new Circle(400, 205, 20); eagleNestImage.setFill(Color.rgb(130, 100, 60));
                root.getChildren().addAll(antHillImage, lizardRockImage, eagleNestImage, bearCaveImage);

                // define the dismissing button
                Button okay = new Button("Close");

                okay.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.close();
                    }
                });

                // add the grid to the scene
                Scene scene = new Scene(root, 500, 500);
                stage.setScene(scene);
                stage.showAndWait();
            }
        });
        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create a stage and grid pane
                Stage stage = new Stage();
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(5);
                grid.setVgap(5);
                grid.setPadding(new Insets(5, 5, 5, 5));

                // define the heading
                Text head = new Text("Info");
                head.setFont(Font.font("Verdana", 20));
                grid.add(head, 0, 0);

                // define the text
                Text text = new Text("LifeSim\nCreated by Joe Pauley\nGitHub: YJoe\nRepository: https://github.com/YJoe/LifeSim.git");
                text.setFont(Font.font("Verdana", 10));
                grid.add(text, 0, 1, 2, 5);

                // define the dismissing button
                Button okay = new Button("Close");
                grid.add(okay, 3, 6);
                okay.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.close();
                    }
                });

                // add the grid to the scene
                Scene scene = new Scene(grid, 350, 150);
                stage.setScene(scene);
                stage.showAndWait();
            }
        });

        // Add all menu elements to the MenuBar
        menuBar.getMenus().add(file);
        menuBar.getMenus().add(edit);
        menuBar.getMenus().add(view);
        menuBar.getMenus().add(simulate);
        menuBar.getMenus().add(graph);
        menuBar.getMenus().add(help);

        // Add the buttons to the button group
        getButtonGroup().getChildren().add(getPlay());
        getButtonGroup().getChildren().add(getView());
        getButtonGroup().getChildren().add(getReset());

        // set the width of the menu bar to stay the same size as the screen width
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    }

    /**
     * A basic Window, loaded when the user attempts an invalid action. The window holds an error message
     * to display to the user
     * @param message The message to display, describing to some extent where the user went wrong
     */
    public void errorWindow(String message){
        // Create a stage and grid pane
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        // define th error text
        Text errorText = new Text("ERROR:");
        errorText.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        errorText.setFill(Color.RED);
        grid.add(errorText, 0, 0);

        // define the message to display
        Text messageText = new Text(message);
        messageText.setFont(new Font("Verdana", 15));
        grid.add(messageText, 1, 0);

        // define the dismissing button
        Button okay = new Button("Okay");
        grid.add(okay, 1, 1);
        okay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        // add the grid to the scene
        Scene scene = new Scene(grid, 250, 100);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Open file explorer and load a configuration to the application
     */
    public void openConfiguration(){
        // Create a new stage
        Stage stage = new Stage();

        // open FileChooser and load the file selected by the user
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Simulation Configuration File");
        // set the directory to the Saved worlds folder
        File existDirectory = new File(System.getProperty("user.dir") + "/LifeSim/SavedWorlds");
        fileChooser.setInitialDirectory(existDirectory);

        // set the filePath to the return of the fileChooser
        File file = fileChooser.showOpenDialog(stage);

        // Load the configuration
        loadConfiguration(file);
    }

    /**
     * Set the Configuration to the loaded file path and create a new world from it
     * @param filePath The path to the desired file
     */
    public void loadConfiguration(File filePath) {
        setConfiguration(deserialize(filePath));
        createWorld();
    }

    /**
     * Create a new Configuration, prompting the user to input the desired entity quantities
     * and the desired food chain
     */
    public void newConfiguration(){
        // Define a stage in which all elements generated below will be displayed
        Stage stage = new Stage();
        stage.setTitle("New Configuration");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Text sceneTitle = new Text("New Configuration");
        sceneTitle.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Text column1 = new Text("Amount");
        grid.add(column1, 1, 1);

        Text column2 = new Text("Homes");
        grid.add(column2, 2, 1);

        // Ants
        Label antsText = new Label("Ants: ");
        TextField antsBox = new TextField();
        antsBox.setText("0");
        TextField antsHomeBox = new TextField();
        antsHomeBox.setText("0");
        grid.add(antsText, 0, 2);
        grid.add(antsBox, 1, 2);
        grid.add(antsHomeBox, 2, 2);

        // Lizards
        Label lizardsText = new Label("Lizards: ");
        TextField lizardsBox = new TextField();
        lizardsBox.setText("0");
        TextField lizardsHomeBox = new TextField();
        lizardsHomeBox.setText("0");
        grid.add(lizardsText, 0, 3);
        grid.add(lizardsBox, 1, 3);
        grid.add(lizardsHomeBox, 2, 3);

        // Bears
        Label bearsText = new Label("Bears: ");
        TextField bearsBox = new TextField();
        bearsBox.setText("0");
        TextField bearsHomeBox = new TextField();
        bearsHomeBox.setText("0");
        grid.add(bearsText, 0, 4);
        grid.add(bearsBox, 1, 4);
        grid.add(bearsHomeBox, 2, 4);

        // Eagles
        Label eaglesText = new Label("Eagles: ");
        TextField eaglesBox = new TextField();
        eaglesBox.setText("0");
        TextField eaglesHomeBox = new TextField();
        eaglesHomeBox.setText("0");
        grid.add(eaglesText, 0, 5);
        grid.add(eaglesBox, 1, 5);
        grid.add(eaglesHomeBox, 2, 5);

        // Pre-killed meat
        Label meatText = new Label("Meat: ");
        TextField meatBox = new TextField();
        meatBox.setText("0");
        grid.add(meatText, 0, 6);
        grid.add(meatBox, 1, 6);

        // Trees
        Label treeText = new Label("Trees: ");
        TextField treeBox = new TextField();
        treeBox.setText("0");
        grid.add(treeText, 0, 7);
        grid.add(treeBox, 1, 7);

        // Rocks
        Label rocksText = new Label("Rocks: ");
        TextField rocksBox = new TextField();
        rocksBox.setText("0");
        grid.add(rocksText, 0, 8);
        grid.add(rocksBox, 1, 8);

        // Water
        Label waterText = new Label("Ponds: ");
        TextField waterBox = new TextField();
        waterBox.setText("0");
        grid.add(waterText, 0, 9);
        grid.add(waterBox, 1, 9);

        // Submit button
        Button submit = new Button("Create world");
        HBox hBox = new HBox(10);
        submit.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(submit);
        grid.add(hBox, 10, 15, 7, 1);

        // Load Defaults
        Button defaults = new Button("Default Food Chain");
        HBox hBox1 = new HBox(10);
        hBox.getChildren().add(defaults);
        grid.add(hBox1, 14, 15, 7, 1);

        // FoodChain text
        Text foodChainText = new Text("Food Chain");
        foodChainText.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        grid.add(foodChainText, 10, 0, 5, 1);

        Text key = new Text("Key: \nA = Ants\nL = Lizards\nB = Bears\nE = Eagles\nF = Fruit");
        grid.add(key, 20, 4, 1, 4);

        Text huntList = new Text("Hunt List");
        grid.add(huntList, 10, 1);

        Text hAnts = new Text("A");
        grid.add(hAnts, 12, 1);
        Text hLizards = new Text("L");
        grid.add(hLizards, 13, 1);
        Text hBears = new Text("B");
        grid.add(hBears, 14, 1);
        Text hEagles = new Text("E");
        grid.add(hEagles, 15, 1);

        Text ants = new Text("Ants");
        grid.add(ants, 11, 2);
        Text lizards = new Text("Lizards");
        grid.add(lizards, 11, 3);
        Text bears = new Text("Bears");
        grid.add(bears, 11, 4);
        Text eagles = new Text("Eagles");
        grid.add(eagles, 11, 5);

        // Add all checkBoxes to the grid
        ArrayList<ArrayList<CheckBox>> huntsBoxes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            huntsBoxes.add(new ArrayList<>());
            for (int j = 0; j < 4; j++){
                huntsBoxes.get(i).add(new CheckBox());
                grid.add(huntsBoxes.get(i).get(j), i + 12, j + 2);
            }
        }

        Text eatList = new Text("Eat List");
        grid.add(eatList, 10, 6);

        Text ants2 = new Text("A");
        grid.add(ants2, 12, 6);
        Text lizards2 = new Text("L");
        grid.add(lizards2, 13, 6);
        Text bears2 = new Text("B");
        grid.add(bears2, 14, 6);
        Text eagles2 = new Text("E");
        grid.add(eagles2, 15, 6);
        Text fruit = new Text("F");
        grid.add(fruit, 16, 6);

        Text eAnts = new Text("Ants");
        grid.add(eAnts, 11, 7);
        Text eLizards = new Text("Lizards");
        grid.add(eLizards, 11, 8);
        Text eBears = new Text("Bears");
        grid.add(eBears, 11, 9);
        Text eEagles = new Text("Eagles");
        grid.add(eEagles, 11, 10);

        // Add all checkBoxes to the grid
        ArrayList<ArrayList<CheckBox>> eatsBoxes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            eatsBoxes.add(new ArrayList<>());
            for (int j = 0; j < 4; j++){
                eatsBoxes.get(i).add(new CheckBox());
                grid.add(eatsBoxes.get(i).get(j), i + 12, j + 7);
            }
        }

        Button current = new Button("Current");
        if(getCurrentWorld() != null) {
            grid.add(current, 0, 15, 3, 1);
        }

        // Set action for current button
        current.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // set all text boxes to the current configuration stats
                antsBox.setText(getConfiguration().getAnts() + "");
                antsHomeBox.setText(getConfiguration().getAntHillCount() + "");
                lizardsBox.setText(getConfiguration().getLizards() + "");
                lizardsHomeBox.setText(getConfiguration().getRockShelterCount() + "");
                bearsBox.setText(getConfiguration().getBears() + "");
                bearsHomeBox.setText(getConfiguration().getCaves() + "");
                eaglesBox.setText(getConfiguration().getEagles() + "");
                eaglesHomeBox.setText(getConfiguration().getNests() + "");
                treeBox.setText(getConfiguration().getFoodTrees() + "");
                meatBox.setText(getConfiguration().getFoodCount() + "");
                rocksBox.setText(getConfiguration().getObstacleCount() + "");
                waterBox.setText(getConfiguration().getPoolCount() + "");

                // Load the checkBoxes to the current configuration
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 4; j++){
                        eatsBoxes.get(i).get(j).setSelected(getConfiguration().getEatList().get(i).get(j));
                    }
                }
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        huntsBoxes.get(i).get(j).setSelected(getConfiguration().getHuntList().get(i).get(j));
                    }
                }
            }
        });

        // Set action for default button
        defaults.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                // load the default configuration
                for(int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        if ((i == 0 && j == 1) ||
                                (i == 1 && j == 3))
                            huntsBoxes.get(i).get(j).setSelected(true);
                        else
                            huntsBoxes.get(i).get(j).setSelected(false);
                    }
                }
                // load the default configuration
                for(int i = 0; i < 5; i++){
                    for (int j = 0; j < 4; j++){
                        if ((i != 0 && j == 0) ||
                                (i != 1 && j == 1) ||
                                (i < 1 || i  > 3) && j == 2 ||
                                (i > 0 && i < 3) && j == 3)
                            eatsBoxes.get(i).get(j).setSelected(true);
                        else
                            eatsBoxes.get(i).get(j).setSelected(false);
                    }
                }
            }
        });

        // Set action for submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int ants, aHomes;
                int lizards, lHomes;
                int bears, bHomes;
                int eagles, eHomes;
                int meat;
                int trees;
                int rocks;
                int pools;
                ArrayList<ArrayList<Boolean>> eatList = new ArrayList<>();
                ArrayList<ArrayList<Boolean>> huntList = new ArrayList<>();

                for(int i = 0; i < 5; i++){
                    eatList.add(new ArrayList<>());
                    for(int j = 0; j < 4; j++){
                        eatList.get(i).add(eatsBoxes.get(i).get(j).isSelected());
                    }
                }
                for(int i = 0; i < 4; i++){
                    huntList.add(new ArrayList<>());
                    for(int j = 0; j < 4; j++){
                        huntList.get(i).add(huntsBoxes.get(i).get(j).isSelected());
                    }
                }

                // Store all variables into the variables defined above

                Configuration configuration;

                try {
                    ants = Integer.parseInt(antsBox.getText());
                    aHomes = Integer.parseInt(antsHomeBox.getText());
                    lizards = Integer.parseInt(lizardsBox.getText());
                    lHomes = Integer.parseInt(lizardsHomeBox.getText());
                    bears = Integer.parseInt(bearsBox.getText());
                    bHomes = Integer.parseInt(bearsHomeBox.getText());
                    eagles = Integer.parseInt(eaglesBox.getText());
                    eHomes = Integer.parseInt(eaglesHomeBox.getText());

                    meat = Integer.parseInt(meatBox.getText());
                    trees = Integer.parseInt(treeBox.getText());
                    rocks = Integer.parseInt(rocksBox.getText());
                    pools = Integer.parseInt(waterBox.getText());

                    configuration = new Configuration(ants, lizards, bears, eagles,
                            aHomes, lHomes, bHomes, eHomes,
                            meat, trees, rocks, pools, eatList, huntList);
                    // Create a configuration and reload the world
                    setConfiguration(configuration);
                    createWorld();
                    stage.close();
                }
                catch(NumberFormatException n){
                    // Display error
                    Label invalidLabel = new Label("Invalid");
                    invalidLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    invalidLabel.setTextFill(Color.RED);
                    grid.add(invalidLabel, 0, 10);

                    // clear fields
                    antsBox.setText("");
                    lizardsBox.setText("");
                    bearsBox.setText("");
                    eaglesBox.setText("");
                    n.printStackTrace();
                }
            }
        });

        // Set the scene and pass the grid
        Scene scene = new Scene(grid, 800, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Open a window displaying the current Configuration
     */
    public void viewConfiguration(){
        // create a stage and grid pane used to display to the user the entire configuration
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Text title = new Text("Current Configuration");
        title.setFont(new Font("Verdana", 20));
        grid.add(title, 0, 0, 10, 1);

        Font headings = new Font("Verdana", 15);
        Font smallText = new Font("Verdana", 10);

        Text entities = new Text("Entities");
        entities.setFont(headings);
        grid.add(entities, 0, 1, 2, 1);

        Text huntList = new Text("Hunt List");
        huntList.setFont(headings);
        grid.add(huntList, 5, 1, 4, 1);

        Text eatList = new Text("Eat List");
        eatList.setFont(headings);
        grid.add(eatList, 5, 8, 4, 1);

        // Set the animal text ot the relevant information
        Text ants = new Text("Animals: " + getConfiguration().getAnts());
        Text antHill = new Text("AntHills: " + getConfiguration().getAntHillCount());
        Text lizards = new Text("Lizards: " + getConfiguration().getLizards());
        Text rockShelters = new Text("RockHome: " + getConfiguration().getRockShelterCount());
        Text bears = new Text("Bears: " + getConfiguration().getBears());
        Text caves = new Text("BearCaves: " + getConfiguration().getCaves());
        Text eagles = new Text("Eagles: " + getConfiguration().getEagles());
        Text nests = new Text("Nests: " + getConfiguration().getNests());
        Text pools = new Text("Pools: " + getConfiguration().getPoolCount());

        // Define array lists to hold all info
        ArrayList<Text> info = new ArrayList<>();
        info.add(ants); info.add(antHill); info.add(lizards);
        info.add(rockShelters); info.add(bears); info.add(caves);
        info.add(eagles); info.add(nests); info.add(pools);

        // Add all text elements to the grid pane
        for (int i = 0; i < info.size(); i++){
            info.get(i).setFont(smallText);
            grid.add(info.get(i), 0, i + 2);
        }

        // Add all hunt elements to the grid pane
        ArrayList<ArrayList<CheckBox>> huntElements = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            huntElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                huntElements.get(i).add(new CheckBox());
                huntElements.get(i).get(j).setSelected(getConfiguration().getHuntList().get(i).get(j));
                grid.add(huntElements.get(i).get(j), i + 6, j + 3);
            }
        }

        // Create the labels denoting the section of the checkboxes is where
        ArrayList<Text> huntListText= new ArrayList<>();
        huntListText.add(new Text("A"));
        huntListText.add(new Text("L"));
        huntListText.add(new Text("B"));
        huntListText.add(new Text("E"));
        for(int i = 0; i < huntListText.size(); i++){
            grid.add(huntListText.get(i), i + 6, 2);
        }
        huntListText.add(new Text("Ants"));
        huntListText.add(new Text("Lizards"));
        huntListText.add(new Text("Bears"));
        huntListText.add(new Text("Eagles"));
        for(int i = 4; i < huntListText.size(); i++){
            grid.add(huntListText.get(i), 5, i - 1);
        }

        // add all eat elements to the grid pane
        ArrayList<ArrayList<CheckBox>> eatElements = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            eatElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                eatElements.get(i).add(new CheckBox());
                eatElements.get(i).get(j).setSelected(getConfiguration().getEatList().get(i).get(j));
                grid.add(eatElements.get(i).get(j), i + 6 , j + 10);
            }
        }

        // Create the labels denoting the section of the checkboxes is where
        ArrayList<Text> eatListText= new ArrayList<>();
        eatListText.add(new Text("A"));
        eatListText.add(new Text("L"));
        eatListText.add(new Text("B"));
        eatListText.add(new Text("E"));
        eatListText.add(new Text("F"));
        for(int i = 0; i < eatListText.size(); i++){
            grid.add(eatListText.get(i), i + 6, 9);
        }
        eatListText.add(new Text("Ants"));
        eatListText.add(new Text("Lizards"));
        eatListText.add(new Text("Bears"));
        eatListText.add(new Text("Eagles"));
        for(int i = 5; i < eatListText.size(); i++){
            grid.add(eatListText.get(i), 5, i + 5);
        }

        // Set the scene and pass the grid pane
        Scene scene = new Scene(grid, 300, 340);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Display a window of buttons used to toggle the visibility of world elements
     */
    public void view(){
        Stage stage = new Stage();
        Group viewRoot = new Group();

        // button width
        int bW = 100;

        // create options
        Button smellRange = new Button("Smell Range");
        smellRange.setPrefWidth(bW);
        smellRange.setTranslateX(5); smellRange.setTranslateY(5);
        smellRange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleSmellCircles();
            }
        });
        viewRoot.getChildren().add(smellRange);

        Button stats = new Button("Animal Stats");
        stats.setPrefWidth(bW);
        stats.setTranslateX(5); stats.setTranslateY(35);
        stats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleStatBars();
            }
        });
        viewRoot.getChildren().add(stats);

        Button shelterStats = new Button("Shelter Stats");
        shelterStats.setPrefWidth(bW);
        shelterStats.setTranslateX(5); shelterStats.setTranslateY(65);
        shelterStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleShelterStatBars();
            }
        });
        viewRoot.getChildren().add(shelterStats);

        Button targets = new Button("Targets");
        targets.setPrefWidth(bW);
        targets.setTranslateX(5); targets.setTranslateY(95);
        targets.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleTargetSquares();
            }
        });
        viewRoot.getChildren().add(targets);

        Button animals = new Button("Animals");
        animals.setPrefWidth(bW);
        animals.setTranslateX(5); animals.setTranslateY(125);
        animals.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleAnimals();
            }
        });
        viewRoot.getChildren().add(animals);

        Button animalLabels = new Button("Animal Text");
        animalLabels.setPrefWidth(bW);
        animalLabels.setTranslateX(5); animalLabels.setTranslateY(155);
        animalLabels.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleAnimalLabels();
            }
        });
        viewRoot.getChildren().add(animalLabels);

        Scene scene = new Scene(viewRoot, 70, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Serialize the current configuration with the name "MyWorld" as a default
     */
    public void saveConfiguration(){
        serialize(getConfiguration(), "MyWorld");
    }

    /**
     * Serialize the current configuration with with a user defined file name
     */
    public void saveConfigurationAs(){
        // Create the stage and grid pane
        Stage stage = new Stage();
        stage.setTitle("Save");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        // define the text to display
        Label saveAs = new Label("Save as");
        grid.add(saveAs, 0, 1);
        TextField textBox = new TextField();
        grid.add(textBox, 1, 1);

        // define the create button
        Button btn = new Button("Create");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);

        // set the action of the create button
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Try to serialize the configuration in the file path
                try{
                    serialize(getConfiguration(), textBox.getText());
                    stage.close();
                }
                catch(NumberFormatException e1){
                    // Display an error message
                    Label invalidLabel = new Label("Incorrect information entered");
                    invalidLabel.setFont(Font.font("Verdana", 15));
                    invalidLabel.setTextFill(Color.RED);
                    grid.add(invalidLabel, 0, 9);
                }
            }
        });

        // set the scene passing the grid
        Scene scene = new Scene(grid, 250, 90);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Display a PieChart presenting the current World population data, with an update button allowing the user to
     * update the PieChart without reopening the window
     */
    public void populationGraph(){
        // Use JavaFX's Pie chart functionality to display the current population of the world
        // Set the stage and create a grid pane
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));
        Scene scene = new Scene(grid);
        stage.setWidth(500);
        stage.setHeight(500);

        int ants = 0, lizards = 0, bears = 0, eagles = 0;

        // Count the amount of Animals in given types
        for(int i = 0; i < getCurrentWorld().getAnimalList().size(); i++){
            if (getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Ant")){
                ants++;
            } else {
                if(getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Lizard")){
                    lizards++;
                } else {
                    if(getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Bear")){
                        bears++;
                    } else{
                        if(getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Eagle")){
                            eagles++;
                        }
                    }
                }
            }
        }

        // Create a list to pass to the PieChart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Ants: " + ants, ants),
                        new PieChart.Data("Lizards: " + lizards, lizards),
                        new PieChart.Data("Bears: " + bears, bears),
                        new PieChart.Data("Eagles: " + eagles, eagles));
        // Create a PieCart passing the data collected
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Population");

        // Create a button to let the user update the stats
        Button update = new Button("Update");
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int ants = 0, lizards = 0, bears = 0, eagles = 0;
                for(int i = 0; i < getCurrentWorld().getAnimalList().size(); i++){
                    if (getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Ant")){
                        ants++;
                    } else {
                        if(getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Lizard")){
                            lizards++;
                        } else {
                            if(getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Bear")){
                                bears++;
                            } else{
                                if(getCurrentWorld().getAnimalList().get(i).getSpecies().equals("Eagle")){
                                    eagles++;
                                }
                            }
                        }
                    }
                }

                chart.getData().get(0).setName("Ants: " + ants);
                chart.getData().get(0).setPieValue(ants);
                chart.getData().get(1).setName("Lizards: " + lizards);
                chart.getData().get(1).setPieValue(lizards);
                chart.getData().get(2).setName("Bears: " + bears);
                chart.getData().get(2).setPieValue(bears);
                chart.getData().get(3).setName("Eagles: " + eagles);
                chart.getData().get(3).setPieValue(eagles);
            }
        });

        // add elements to the grid
        grid.add(chart, 0, 0);
        grid.add(update, 0, 1);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Prompt the user with a window to add an Animal to the current World
     */
    public void addAnimal(){
        // Create a stage and grid pane
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);
        Scene scene = new Scene(grid, 180, 410);

        Text title = new Text("Add an animal");
        title.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));

        // Define the combo box detailing the type of Animal
        Text speciesText = new Text("Species: ");
        ComboBox<String> species = new ComboBox<>(
                FXCollections.observableArrayList("Ant", "Lizard", "Bear", "Eagle"));
        species.getSelectionModel().select(0);
        species.setPrefWidth(150);

        // Define the Gender comboBox
        Text genderText = new Text("Gender: ");
        ComboBox<String> gender = new ComboBox<>(
                FXCollections.observableArrayList("M", "F"));
        gender.getSelectionModel().select(0);
        gender.setPrefWidth(150);

        // Define all text and fields for the input of information
        Text nameText = new Text("Name: ");
        TextField name = new TextField();
        Text xText = new Text("X: ");
        TextField x = new TextField();
        Text yText = new Text("Y: ");
        TextField y = new TextField();
        Text speedText = new Text("Speed: ");
        TextField speed = new TextField();
        Text metabolismText = new Text("Metabolism: ");
        TextField metabolism = new TextField();
        Text strengthText = new Text("Strength: ");
        TextField strength = new TextField();
        Text smellText = new Text("Smell Range: ");
        TextField smellRange = new TextField();
        Text sizeText = new Text("Size: ");
        TextField size = new TextField();

        // define the buttons to display to GUI and set on action for each
        Button add = new Button("Add Animal");
        grid.add(add, 1, 11);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                char genderPass;
                String namePass;
                int xPass;
                int yPass;
                double speedPass;
                float metabolismPass;
                int strengthPass;
                int smellPass;
                int sizePass;

                try{
                    genderPass = gender.getSelectionModel().getSelectedItem().charAt(0);
                    namePass = name.getText();
                    xPass = Integer.parseInt(x.getText());
                    yPass = Integer.parseInt(y.getText());
                    speedPass = Double.parseDouble(speed.getText());
                    metabolismPass = Float.parseFloat(metabolism.getText());
                    strengthPass = Integer.parseInt(strength.getText());
                    smellPass = Integer.parseInt(smellRange.getText());
                    sizePass = Integer.parseInt(size.getText());

                    getCurrentWorld().addAnimal(species.getSelectionModel().getSelectedItem(), xPass, yPass, genderPass,
                            namePass, speedPass, metabolismPass, strengthPass, smellPass, sizePass);

                    stage.close();
                }
                catch(NumberFormatException n){
                    n.printStackTrace();
                }
            }
        });

        Button suggest = new Button("Suggest");
        grid.add(suggest, 0, 11);
        suggest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name.setText("Joe");
                x.setText("0");
                y.setText("0");
                speed.setText("0.8");
                metabolism.setText("0.001");
                strength.setText("1");
                smellRange.setText("80");
                size.setText("1");
            }
        });

        Text extraInfo = new Text("(A single blank field will load\n the default of the animal)");
        grid.add(extraInfo, 0, 12, 3, 2);

        // Add all elements to the grid
        grid.add(title, 0, 0, 2, 1);
        grid.add(speciesText,0, 1);
        grid.add(species, 1, 1);
        grid.add(genderText, 0, 2);
        grid.add(gender, 1, 2);
        grid.add(nameText,0,3);
        grid.add(name, 1, 3);
        grid.add(xText,0,4);
        grid.add(x, 1, 4);
        grid.add(yText,0,5);
        grid.add(y, 1, 5);
        grid.add(speedText,0,6);
        grid.add(speed, 1, 6);
        grid.add(metabolismText,0,7);
        grid.add(metabolism, 1, 7);
        grid.add(strengthText,0,8);
        grid.add(strength, 1, 8);
        grid.add(smellText,0,9);
        grid.add(smellRange, 1, 9);
        grid.add(sizeText,0,10);
        grid.add(size, 1, 10);

        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Prompt the user with a window to enter an ID and remove the given Animal from the current World,
     * if there is no animal an error message will be shown
     */
    public void removeAnimal(){
        // Create a stage and grid in which each element will be added to
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Text title = new Text("Remove Animal");
        title.setFont(new Font("Verdana", 20));
        grid.add(title, 0, 0, 5, 1);

        TextField field = new TextField("Animal ID");
        grid.add(field, 0, 1);

        Button remove = new Button("Remove");
        grid.add(remove, 1, 1);

        Text text = new Text();
        text.setFont(new Font("Verdana", 10));
        grid.add(text, 0, 4, 4, 10);

        // set action for remove
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id;
                boolean found = false;
                // Try to find the animal index by ID
                try {
                    id = Integer.parseInt(field.getText());
                }
                catch(NumberFormatException n){
                    id = -1;
                }
                for(int i = 0; i < getCurrentWorld().getAnimalList().size(); i++) {
                    // if it was found then kill the selected Animal
                    if (getCurrentWorld().getAnimalList().get(i).getID() == id) {
                        getCurrentWorld().getAnimalList().get(i).setEnergy(-10);
                        text.setText("Animal " + i + " will be killed in the next update");
                        text.setFill(Color.BLACK);
                        found = true;
                        break;
                    }
                }
                if (!found){
                    text.setText("Animal " + id + " not found");
                    text.setFill(Color.RED);
                }
            }
        });

        Scene scene = new Scene(grid, 240, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Prompt the user with a window allowing then to enter the ID of an Animal and edit its attributes, if
     * there is no animal an error message will be shown
     */
    public void editAnimal(){
        // Animal editor
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Text title = new Text("Edit Animal");
        title.setFont(new Font("Verdana", 20));
        grid.add(title, 0, 0, 5, 1);

        TextField field = new TextField("Animal ID");
        grid.add(field, 1, 1);

        // Field info
        Font smallText = new Font("Verdana", 10);
        ArrayList<Text> textList = new ArrayList<>();
        textList.add(new Text("Name: "));
        textList.add(new Text("Gender: "));
        textList.add(new Text("Speed: "));
        textList.add(new Text("Smell Range: "));
        textList.add(new Text("Metabolism: "));
        textList.add(new Text("Strength"));
        for(int i = 0; i < textList.size(); i++){
            textList.get(i).setFont(smallText);
            grid.add(textList.get(i), 0, i + 2);
        }

        // Field
        ArrayList<TextField> textFields = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            textFields.add(new TextField());
            grid.add(textFields.get(i), 1 , i + 2);
        }

        // Buttons
        Button find = new Button("Find");
        grid.add(find, 0, 1);
        find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id;
                try {
                    id = Integer.parseInt(field.getText());
                }
                catch(NumberFormatException n){
                    errorWindow("Animal not found");
                    id = -1;
                }
                for(int i = 0; i < getCurrentWorld().getAnimalList().size(); i++) {
                    if (getCurrentWorld().getAnimalList().get(i).getID() == id) {
                        textFields.get(0).setText(getCurrentWorld().getAnimalList().get(i).getName());
                        textFields.get(1).setText(getCurrentWorld().getAnimalList().get(i).getGender() + "");
                        textFields.get(2).setText(String.format("%.1g", getCurrentWorld().getAnimalList().get(i).getSpeed()));
                        textFields.get(3).setText(getCurrentWorld().getAnimalList().get(i).getSmellRange() + "");
                        textFields.get(4).setText(String.format("%.1g", getCurrentWorld().getAnimalList().get(i).getMetabolism()));
                        textFields.get(5).setText(getCurrentWorld().getAnimalList().get(i).getStrength() + "");
                        break;
                    }
                }
            }
        });

        Button save = new Button("Save");
        grid.add(save, 0, 9);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int index = Integer.parseInt(field.getText());
                    getCurrentWorld().getAnimalList().get(index).setName(textFields.get(0).getText());
                    String c = textFields.get(1).getText();
                    getCurrentWorld().getAnimalList().get(index).setGender(c.charAt(0));
                    getCurrentWorld().getAnimalList().get(index).setSpeed(Double.parseDouble(textFields.get(2).getText()));
                    getCurrentWorld().getAnimalList().get(index).setOriginalSpeed(Double.parseDouble(textFields.get(2).getText()));
                    getCurrentWorld().getAnimalList().get(index).setSmellRange(Integer.parseInt(textFields.get(3).getText()));
                    getCurrentWorld().getAnimalList().get(index).setPathDistance(Integer.parseInt(textFields.get(3).getText()));
                    getCurrentWorld().getAnimalList().get(index).getSmellCircle().setRadius(Integer.parseInt(textFields.get(3).getText()));
                    getCurrentWorld().getAnimalList().get(index).setMetabolism(Float.parseFloat(textFields.get(4).getText()));
                    getCurrentWorld().getAnimalList().get(index).setStrength(Integer.parseInt(textFields.get(5).getText()));
                }
                catch(IndexOutOfBoundsException i){
                    errorWindow("Animal not found");
                }
            }
        });

        Scene scene = new Scene(grid, 280, 300);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Prompt the user with a window in which an ID will be entered, then displaying the current attributes of
     * the Animal, if there is no animal an error message will be shown
     */
    public void viewAnimalStats(){
        // Create a stage and grid pane of in which to display the information
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        // Define elements to add to the grid pane
        Text title = new Text("Animal Stats");
        title.setFont(new Font("Verdana", 20));
        grid.add(title, 0, 0, 5, 1);

        TextField field = new TextField("Animal ID");
        grid.add(field, 0, 1);

        // Define the button to view the information
        Button go = new Button("View");
        grid.add(go, 1, 1);

        Text text = new Text();
        text.setFont(new Font("Verdana", 10));
        grid.add(text, 0, 4, 4, 10);

        // Set action of view button
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id;
                // try to parse the integer to the id int
                boolean found = false;
                try {
                    id = Integer.parseInt(field.getText());
                }
                catch(NumberFormatException n){
                    id = -1;
                }
                for(int i = 0; i < getCurrentWorld().getAnimalList().size(); i++) {
                    // if the ID was found then display the information for such Animal
                    if (getCurrentWorld().getAnimalList().get(i).getID() == id) {
                        text.setText(getCurrentWorld().getAnimalList().get(i).statistics());
                        text.setFill(Color.BLACK);
                        found = true;
                        break;
                    }
                }
                if (!found){
                    text.setText("Animal " + id + " not found");
                    text.setFill(Color.RED);
                }
            }
        });

        Scene scene = new Scene(grid, 220, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Prompt the user with a window allowing them to change the current foodChain of the current World
     */
    public void editFoodChain(){
        // Create a scene and grid pane in which to display the current food chain on
        // and allow the user to edit and set the configuration
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Font headings = new Font("Verdana", 15);
        Font smallText = new Font("Verdana", 10);

        Text huntList = new Text("Hunt List");
        huntList.setFont(headings);
        grid.add(huntList, 0, 1, 4, 1);

        Text eatList = new Text("Eat List");
        eatList.setFont(headings);
        grid.add(eatList, 0, 8, 4, 1);

        // Create an array list of lists holding check boxes
        ArrayList<ArrayList<CheckBox>> huntElements = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            // Add another list
            huntElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                // Add a checkBox to the list
                huntElements.get(i).add(new CheckBox());
                huntElements.get(i).get(j).setSelected(getConfiguration().getHuntList().get(i).get(j));
                // Add the element to the grid
                grid.add(huntElements.get(i).get(j), i + 1, j + 3);
            }
        }

        // Define the grid labels and add them to the gridPane
        ArrayList<Text> huntListText= new ArrayList<>();
        huntListText.add(new Text("A"));
        huntListText.add(new Text("L"));
        huntListText.add(new Text("B"));
        huntListText.add(new Text("E"));
        for(int i = 0; i < huntListText.size(); i++){
            huntListText.get(i).setFont(smallText);
            grid.add(huntListText.get(i), i + 1, 2);
        }
        huntListText.add(new Text("Ants"));
        huntListText.add(new Text("Lizards"));
        huntListText.add(new Text("Bears"));
        huntListText.add(new Text("Eagles"));
        for(int i = 4; i < huntListText.size(); i++){
            huntListText.get(i).setFont(smallText);
            grid.add(huntListText.get(i), 0, i - 1);
        }

        // Create an array list of lists holding check boxes
        ArrayList<ArrayList<CheckBox>> eatElements = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            // Add another list
            eatElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                // Add a checkBox to the list
                eatElements.get(i).add(new CheckBox());
                eatElements.get(i).get(j).setSelected(getConfiguration().getEatList().get(i).get(j));
                grid.add(eatElements.get(i).get(j), i + 1 , j + 10);
            }
        }

        // Define the grid labels and add them to the gridPane
        ArrayList<Text> eatListText= new ArrayList<>();
        eatListText.add(new Text("A"));
        eatListText.add(new Text("L"));
        eatListText.add(new Text("B"));
        eatListText.add(new Text("E"));
        eatListText.add(new Text("F"));
        for(int i = 0; i < eatListText.size(); i++){
            eatListText.get(i).setFont(smallText);
            grid.add(eatListText.get(i), i + 1, 9);
        }
        eatListText.add(new Text("Ants"));
        eatListText.add(new Text("Lizards"));
        eatListText.add(new Text("Bears"));
        eatListText.add(new Text("Eagles"));
        for(int i = 5; i < eatListText.size(); i++){
            eatListText.get(i).setFont(smallText);
            grid.add(eatListText.get(i), 0, i + 5);
        }

        // Define buttons
        Button restore = new Button("Restore");
        grid.add(restore, 1, 17, 3, 1);

        // Set on action for restore
        restore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // assign the configuration eat and hunt elements to the relative checkBoxes
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        huntElements.get(i).get(j).setSelected(getConfiguration().getHuntList().get(i).get(j));
                    }
                }
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 4; j++){
                        eatElements.get(i).get(j).setSelected(getConfiguration().getEatList().get(i).get(j));
                    }
                }
            }
        });

        Button set = new Button("Set");
        grid.add(set, 4, 17, 3, 1);

        // set on action for set
        set.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Set the configuration to the detailed statistics input by the user
                ArrayList<ArrayList<Boolean>> foodChainEatList = new ArrayList<>();
                ArrayList<ArrayList<Boolean>> foodChainHuntList = new ArrayList<>();

                for(int i = 0; i < 5; i++){
                    foodChainEatList.add(new ArrayList<>());
                    for(int j = 0; j < 4; j++){
                        foodChainEatList.get(i).add(eatElements.get(i).get(j).isSelected());
                    }
                }
                for(int i = 0; i < 4; i++){
                    foodChainHuntList.add(new ArrayList<>());
                    for(int j = 0; j < 4; j++){
                        foodChainHuntList.get(i).add(huntElements.get(i).get(j).isSelected());
                    }
                }
                getConfiguration().setEatList(foodChainEatList);
                getConfiguration().setHuntList(foodChainHuntList);
            }
        });

        Scene scene = new Scene(grid, 200, 330);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Create a new world from the current Configuration and set it to the current World clear the root and
     * add everything back to the root after
     */
    public void createWorld(){
        // clear everything from the root
        getRoot().getChildren().clear();

        // start the simulation in the paused state
        setPaused(true);

        // create the new world with the current configuration
        setCurrentWorld(new World(getRoot(), getConfiguration()));
        getCurrentWorld().toggleAnimalLabels();
        getCurrentWorld().toggleTargetSquares();
        getCurrentWorld().toggleHomeSquares();

        // add the menu bar back in
        getRoot().getChildren().add(getMenuBar());
        getRoot().getChildren().add(getBackBar());
        getRoot().getChildren().add(getDate());
        getRoot().getChildren().add(getButtonGroup());
    }

    /**
     * Serialise the given Configuration with the given name to the deined file path
     * @param configuration Configuration to save
     * @param fileName Name of the configuration to save under
     */
    public static void serialize(Configuration configuration, String fileName){
        // Try catch to check that the file saved correctly
        try{
            // Open the output path
            FileOutputStream fileOut = new FileOutputStream("LifeSim/SavedWorlds/" + fileName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            // Write the object
            out.writeObject(configuration);
            // Close the stream
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Deserialize a given file path returning a Configuration object
     * @param file File path to load
     * @return A Configuration object
     */
    public static Configuration deserialize(File file){
        // Create a configuration file
        Configuration configuration;
        // Try and load the file to the Configuration object
        try {
            // Create the input stream
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // Store the object
            configuration = (Configuration) in.readObject();
            // Close the input stream
            in.close();
            fileIn.close();
        }
        catch(IOException i){
            i.printStackTrace();
            return null;
        }
        catch(ClassNotFoundException c){
            System.out.println("Configuration class not found");
            c.printStackTrace();
            return null;
        }
        return configuration;
    }

    /**
     * @return MenuBar node
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * @return MenuBar node
     */
    public World getCurrentWorld() {
        return currentWorld;
    }

    /**
     * @param currentWorld Active World
     */
    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    /**
     * @return Loaded configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration Loaded configuration
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * @param root Root node reference
     */
    public void setRoot(Group root) {
        this.root = root;
    }

    /**
     * @return Root node reference
     */
    public Group getRoot(){
        return root;
    }

    /**
     * Toggle the paused variable
     */
    public void togglePaused(){
        isPaused = !isPaused;
    }

    /**
     * @param isPaused If the World should run
     */
    public void setPaused(boolean isPaused){
        this.isPaused = isPaused;
    }

    /**
     * @return If the World should run
     */
    public boolean isPaused(){
        return isPaused;
    }

    /**
     * @return Reset Button
     */
    public Button getReset() {
        return reset;
    }

    /**
     * @param reset Reset Button
     */
    public void setReset(Button reset) {
        this.reset = reset;
    }

    /**
     * @return View Button
     */
    public Button getView() {
        return view;
    }

    /**
     * @param view View Button
     */
    public void setView(Button view) {
        this.view = view;
    }

    /**
     * @return Play/Pause Button
     */
    public Button getPlay() {
        return play;
    }

    /**
     * @param play Play/Pause Button
     */
    public void setPlay(Button play) {
        this.play = play;
    }

    /**
     * @return Group holding Button nodes
     */
    public Group getButtonGroup() {
        return buttonGroup;
    }

    /**
     * @return Date node
     */
    public Text getDate() {
        return date;
    }

    /**
     * @param date Date node
     */
    public void setDate(Text date) {
        this.date = date;
    }

    /**
     * @return Back bar in which buttons are displayed
     */
    public Rectangle getBackBar() {
        return backBar;
    }

    /**
     * @param backBar Back bar
     */
    public void setBackBar(Rectangle backBar) {
        this.backBar = backBar;
    }
}
