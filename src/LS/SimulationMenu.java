package LS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SimulationMenu {
    private MenuBar menuBar;
    private Group root;
    private WorldStatsBar worldStatsBar;
    private Configuration configuration;
    private World currentWorld;
    private Group buttonGroup = new Group();
    private Button reset, view, play;
    private boolean isPaused = false;

    public SimulationMenu(Stage primaryStage, Group root){
        //Top Menu Bar
        menuBar = new MenuBar();
        menuBar.setOpacity(1);
        setRoot(root);
        setWorldStatsBar(new WorldStatsBar());
        root.getChildren().add(getWorldStatsBar().getGroup());
        root.getChildren().add(getButtonGroup());

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
        edit.getItems().add(modifyLifeForm);
        edit.getItems().add(removeLifeForm);
        edit.getItems().add(addLifeForm);
        edit.getItems().add(editConfig);
        modifyLifeForm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                //modifyAnimal();
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
        view.getItems().add(displayConfig);
        view.getItems().add(displayLifeForms);
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
                System.out.println("was clicked");
            }
        });

        // Simulate
        javafx.scene.control.Menu simulate = new javafx.scene.control.Menu("Simulate");
        MenuItem run = new MenuItem("Run");
        MenuItem pausePlay = new MenuItem("Pause/Play");
        MenuItem restart = new MenuItem("Restart");
        MenuItem toggleMap = new MenuItem("Toggle Map");
        simulate.getItems().add(run);
        simulate.getItems().add(pausePlay);
        simulate.getItems().add(restart);
        simulate.getItems().add(toggleMap);
        run.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("was clicked");
            }
        });
        pausePlay.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                isPaused = !isPaused;
            }
        });
        restart.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("was  clicked");
            }
        });
        toggleMap.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("was clicked");
            }
        });

        menuBar.getMenus().add(file);
        menuBar.getMenus().add(edit);
        menuBar.getMenus().add(view);
        menuBar.getMenus().add(simulate);

        getButtonGroup().getChildren().add(getPlay());
        getButtonGroup().getChildren().add(getView());
        getButtonGroup().getChildren().add(getReset());

        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    }

    public void errorWindow(String message){
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Text errorText = new Text("ERROR:");
        errorText.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        errorText.setFill(Color.RED);
        grid.add(errorText, 0, 0);

        Text messageText = new Text(message);
        messageText.setFont(new Font("Verdana", 15));
        grid.add(messageText, 1, 0);

        Button okay = new Button("Okay");
        grid.add(okay, 1, 1);
        okay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        Scene scene = new Scene(grid, 250, 100);
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Load file functions
    public void openConfiguration(){
        // Create a new stage
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Simulation Configuration File");
        File existDirectory = new File(System.getProperty("user.dir") + "/LifeSim/SavedWorlds");
        fileChooser.setInitialDirectory(existDirectory);
        File file = fileChooser.showOpenDialog(stage);

        loadConfiguration(file);
    }

    public void loadConfiguration(File filePath) {
        setConfiguration(Serialize.deserialize(filePath));
        createWorld();
    }

    public void newConfiguration(){
        FoodChain foodChain = null;
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

        Text foodChainText = new Text("Food Chain");
        foodChainText.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        grid.add(foodChainText, 10, 0, 5, 1);

        Text key = new Text("Key: \nA = Ants\nL = Lizards\nB = Bears\nE = Eagles");
        grid.add(key, 20, 5, 1, 4);

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

        ArrayList<ArrayList<CheckBox>> eatsBoxes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            eatsBoxes.add(new ArrayList<>());
            for (int j = 0; j < 4; j++){
                eatsBoxes.get(i).add(new CheckBox());
                grid.add(eatsBoxes.get(i).get(j), i + 12, j + 7);
            }
        }

        defaults.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){

                for(int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        if ((i == 0 && j == 1) ||
                                (i == 1 && j == 3))
                            huntsBoxes.get(i).get(j).setSelected(true);
                        else
                            huntsBoxes.get(i).get(j).setSelected(false);
                    }
                }
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
                FoodChain foodChain = new FoodChain(eatList, huntList);

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
                            meat, trees, rocks, pools, foodChain);
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

        Scene scene = new Scene(grid, 800, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void viewConfiguration(){
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

        Text ants = new Text("Animals: " + getConfiguration().getAnts());
        Text antHill = new Text("AntHills: " + getConfiguration().getAntHillCount());
        Text lizards = new Text("Lizards: " + getConfiguration().getLizards());
        Text rockShelters = new Text("RockHome: " + getConfiguration().getRockShelterCount());
        Text bears = new Text("Bears: " + getConfiguration().getBears());
        Text caves = new Text("BearCaves: " + getConfiguration().getCaves());
        Text eagles = new Text("Eagles: " + getConfiguration().getEagles());
        Text nests = new Text("Nests: " + getConfiguration().getNests());

        ArrayList<Text> info = new ArrayList<>();
        info.add(ants); info.add(antHill); info.add(lizards);
        info.add(rockShelters); info.add(bears); info.add(caves);
        info.add(eagles); info.add(nests);

        for (int i = 0; i < info.size(); i++){
            info.get(i).setFont(smallText);
            grid.add(info.get(i), 0, i + 2);
        }

        ArrayList<ArrayList<CheckBox>> huntElements = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            huntElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                huntElements.get(i).add(new CheckBox());
                huntElements.get(i).get(j).setSelected(getConfiguration().getFoodChain().getHuntList().get(i).get(j));
                grid.add(huntElements.get(i).get(j), i + 6, j + 3);
            }
        }

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

        ArrayList<ArrayList<CheckBox>> eatElements = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            eatElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                eatElements.get(i).add(new CheckBox());
                eatElements.get(i).get(j).setSelected(getConfiguration().getFoodChain().getEatList().get(i).get(j));
                grid.add(eatElements.get(i).get(j), i + 6 , j + 10);
            }
        }

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

        Scene scene = new Scene(grid, 300, 340);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void view(){
        Stage stage = new Stage();
        Group viewRoot = new Group();

        // create options
        Button smellRange = new Button("Smell Range");
        smellRange.setTranslateX(5); smellRange.setTranslateY(5);
        smellRange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleSmellCircles();
            }
        });
        viewRoot.getChildren().add(smellRange);

        Button stats = new Button("Animal Stats");
        stats.setTranslateX(5); stats.setTranslateY(35);
        stats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleStatBars();
            }
        });
        viewRoot.getChildren().add(stats);

        Button shelterStats = new Button("Shelter Stats");
        shelterStats.setTranslateX(5); shelterStats.setTranslateY(65);
        shelterStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleShelterStatBars();
            }
        });
        viewRoot.getChildren().add(shelterStats);

        Button targets = new Button("Targets");
        targets.setTranslateX(5); targets.setTranslateY(95);
        targets.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getCurrentWorld().toggleTargetSquares();
            }
        });
        viewRoot.getChildren().add(targets);

        Scene scene = new Scene(viewRoot, 70, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Save file functions
    public void saveConfiguration(){
        Serialize.serialize(getConfiguration(), "MyWorld");
    }

    public void saveConfigurationAs(){
        Stage stage = new Stage();
        stage.setTitle("Save");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Label saveAs = new Label("Save as");
        grid.add(saveAs, 0, 1);
        TextField textBox = new TextField();
        grid.add(textBox, 1, 1);

        Button btn = new Button("Create");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                    saveConfiguration(textBox.getText());
                    stage.close();
                }
                catch(NumberFormatException e1){
                    Label invalidlabel = new Label("Incorrect information entered");
                    invalidlabel.setFont(Font.font("Verdana", 15));
                    invalidlabel.setTextFill(Color.RED);
                    grid.add(invalidlabel, 0, 9);
                }
            }
        });
        Scene scene = new Scene(grid, 250, 90);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void saveConfiguration(String name){
        Serialize.serialize(getConfiguration(), name);
    }

    // edit
    public void addAnimal(){
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);
        Scene scene = new Scene(grid, 180, 410);

        Text title = new Text("Add an animal");
        title.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));

        Text speciesText = new Text("Species: ");
        ComboBox<String> species = new ComboBox<>(
                FXCollections.observableArrayList("Ant", "Lizard", "Bear", "Eagle"));
        species.getSelectionModel().select(0);
        species.setPrefWidth(150);

        Text genderText = new Text("Gender: ");
        ComboBox<String> gender = new ComboBox<>(
                FXCollections.observableArrayList("M", "F"));
        gender.getSelectionModel().select(0);
        gender.setPrefWidth(150);

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
                    System.out.println("sozza m9");
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

    public void removeAnimal(){
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

        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id;
                boolean found = false;
                try {
                    id = Integer.parseInt(field.getText());
                }
                catch(NumberFormatException n){
                    id = -1;
                }
                for(int i = 0; i < getCurrentWorld().getAnimalList().size(); i++) {
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

        Scene scene = new Scene(grid, 220, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void viewAnimalStats(){
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Text title = new Text("Animal Stats");
        title.setFont(new Font("Verdana", 20));
        grid.add(title, 0, 0, 5, 1);

        TextField field = new TextField("Animal ID");
        grid.add(field, 0, 1);

        Button go = new Button("View");
        grid.add(go, 1, 1);

        Text text = new Text();
        text.setFont(new Font("Verdana", 10));
        grid.add(text, 0, 4, 4, 10);

        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = 0;
                boolean found = false;
                try {
                    id = Integer.parseInt(field.getText());
                }
                catch(NumberFormatException n){
                    id = -1;
                }
                for(int i = 0; i < getCurrentWorld().getAnimalList().size(); i++) {
                    if (getCurrentWorld().getAnimalList().get(i).getID() == id) {
                        text.setText(getCurrentWorld().getAnimalList().get(i).statistics());
                        text.setFill(Color.BLACK);
                        System.out.println(getCurrentWorld().getAnimalList().get(i).getName());
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

    public void editFoodChain(){
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

        ArrayList<ArrayList<CheckBox>> huntElements = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            huntElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                huntElements.get(i).add(new CheckBox());
                huntElements.get(i).get(j).setSelected(getConfiguration().getFoodChain().getHuntList().get(i).get(j));
                grid.add(huntElements.get(i).get(j), i + 1, j + 3);
            }
        }

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

        ArrayList<ArrayList<CheckBox>> eatElements = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            eatElements.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                eatElements.get(i).add(new CheckBox());
                eatElements.get(i).get(j).setSelected(getConfiguration().getFoodChain().getEatList().get(i).get(j));
                grid.add(eatElements.get(i).get(j), i + 1 , j + 10);
            }
        }

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

        Button restore = new Button("Restore");
        grid.add(restore, 1, 17, 3, 1);

        restore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        huntElements.get(i).get(j).setSelected(getConfiguration().getFoodChain().getHuntList().get(i).get(j));
                    }
                }
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 4; j++){
                        eatElements.get(i).get(j).setSelected(getConfiguration().getFoodChain().getEatList().get(i).get(j));
                    }
                }
            }
        });

        Button set = new Button("Set");
        grid.add(set, 4, 17, 3, 1);

        set.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                getConfiguration().setFoodChain(new FoodChain(foodChainEatList, foodChainHuntList));
            }
        });

        Scene scene = new Scene(grid, 200, 330);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

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
        getRoot().getChildren().add(getWorldStatsBar().getGroup());
        getRoot().getChildren().add(getButtonGroup());
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public Group getRoot(){
        return root;
    }

    public void togglePaused(){
        isPaused = !isPaused;
    }

    public void setPaused(boolean isPaused){
        this.isPaused = isPaused;
    }

    public boolean isPaused(){
        return isPaused;
    }

    public WorldStatsBar getWorldStatsBar() {
        return worldStatsBar;
    }

    public void setWorldStatsBar(WorldStatsBar worldStatsBar) {
        this.worldStatsBar = worldStatsBar;
    }

    public Button getReset() {
        return reset;
    }

    public void setReset(Button reset) {
        this.reset = reset;
    }

    public Button getView() {
        return view;
    }

    public void setView(Button view) {
        this.view = view;
    }

    public Button getPlay() {
        return play;
    }

    public void setPlay(Button play) {
        this.play = play;
    }

    public Group getButtonGroup() {
        return buttonGroup;
    }

    public void setButtonGroup(Group buttonGroup) {
        this.buttonGroup = buttonGroup;
    }
}
