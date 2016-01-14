package LS;

import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class SimulationMenu {
    private MenuBar menuBar;
    private Group root;
    private Configuration configuration;
    private World currentWorld;
    private boolean isPaused = false;

    public SimulationMenu(Stage primaryStage, Group root){
        //Top Menu Bar
        menuBar = new MenuBar();
        menuBar.setOpacity(1);
        setRoot(root);

        // File
        javafx.scene.control.Menu file = new javafx.scene.control.Menu("File");
        MenuItem fileNewConfig = new MenuItem("New Configuration");
        MenuItem fileOpenConfig = new MenuItem("Open Configuration");
        MenuItem fileSave = new MenuItem("Save");
        MenuItem fileSaveAs = new MenuItem("Save As");
        MenuItem fileExit = new MenuItem("Exit");
        file.getItems().add(fileNewConfig);
        file.getItems().add(fileOpenConfig);
        file.getItems().add(fileSave);
        file.getItems().add(fileSaveAs);
        file.getItems().add(fileExit);
        fileNewConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                newConfiguration();
            }
        });
        fileOpenConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // Create a new stage
                Stage stage = new Stage();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Simulation Configuration File");
                File existDirectory = new File(System.getProperty("user.dir") + "/SavedWorlds");
                fileChooser.setInitialDirectory(existDirectory);
                File file = fileChooser.showOpenDialog(stage);

                loadConfiguration(file);
            }
        });
        fileSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                saveConfiguration();
            }
        });
        fileSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
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
        });
        fileExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Exit Clicked");
            }
        });

        // Edit
        javafx.scene.control.Menu edit = new javafx.scene.control.Menu("Edit");
        MenuItem modifyLifeForm = new MenuItem("Modify Life Form");
        MenuItem removeLifeForm = new MenuItem("Remove Life Form");
        MenuItem addLifeForm = new MenuItem("Add Life Form");
        edit.getItems().add(modifyLifeForm);
        edit.getItems().add(removeLifeForm);
        edit.getItems().add(addLifeForm);
        modifyLifeForm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("modify life form clicked");
            }
        });
        removeLifeForm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("remove life form clicked");
            }
        });
        addLifeForm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("add life form clicked");
            }
        });

        // View
        javafx.scene.control.Menu view = new javafx.scene.control.Menu("View");
        MenuItem displayConfig = new MenuItem("Display Configuration");
        MenuItem editConfig = new MenuItem("Edit Configuration");
        MenuItem displayLifeForms = new MenuItem("Display Life Form Stats");
        MenuItem displayMapInfo = new MenuItem("Display Map Stats");
        view.getItems().add(displayConfig);
        view.getItems().add(editConfig);
        view.getItems().add(displayMapInfo);
        displayConfig.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("was clicked");
            }
        });
        editConfig.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("was  clicked");
            }
        });
        displayLifeForms.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("was clicked");
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

        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    }

    // Load file functions
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
                boolean eatList[][] = new boolean[5][4];
                boolean huntList[][] = new boolean[4][4];

                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 4; j++){
                        eatList[i][j] = eatsBoxes.get(i).get(j).isSelected();
                    }
                }
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        huntList[i][j] = huntsBoxes.get(i).get(j).isSelected();
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

    // Save file functions
    public void saveConfiguration(){
        Serialize.serialize(getConfiguration(), "MyWorld");
    }
    public void saveConfiguration(String name){
        Serialize.serialize(getConfiguration(), name);
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

}
