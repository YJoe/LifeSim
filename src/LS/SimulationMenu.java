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

    public void togglePaused(){
        isPaused = !isPaused;
    }
    public void setPaused(boolean isPaused){
        this.isPaused = isPaused;
    }
    public boolean isPaused(){
        return isPaused;
    }

    // Load file functions
    public void loadConfiguration(File filePath) {
        setConfiguration(Serialize.deserialize(filePath));
        createWorld();
    }

    public void newConfiguration(){
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
        TextField antsHomeBox = new TextField();
        grid.add(antsText, 0, 2);
        grid.add(antsBox, 1, 2);
        grid.add(antsHomeBox, 2, 2);

        // Lizards
        Label lizardsText = new Label("Lizards: ");
        TextField lizardsBox = new TextField();
        TextField lizardsHomeBox = new TextField();
        grid.add(lizardsText, 0, 3);
        grid.add(lizardsBox, 1, 3);
        grid.add(lizardsHomeBox, 2, 3);

        // Bears
        Label bearsText = new Label("Bears: ");
        TextField bearsBox = new TextField();
        TextField bearsHomeBox = new TextField();
        grid.add(bearsText, 0, 4);
        grid.add(bearsBox, 1, 4);
        grid.add(bearsHomeBox, 2, 4);

        // Eagles
        Label eaglesText = new Label("Eagles: ");
        TextField eaglesBox = new TextField();
        TextField eaglesHomeBox = new TextField();
        grid.add(eaglesText, 0, 5);
        grid.add(eaglesBox, 1, 5);
        grid.add(eaglesHomeBox, 2, 5);

        // Pre-killed meat
        Label meatText = new Label("Meat: ");
        TextField meatBox = new TextField();
        grid.add(meatText, 0, 6);
        grid.add(meatBox, 1, 6);

        // Trees
        Label treeText = new Label("Trees: ");
        TextField treeBox = new TextField();
        grid.add(treeText, 0, 7);
        grid.add(treeBox, 1, 7);

        // Rocks
        Label rocksText = new Label("Rocks: ");
        TextField rocksBox = new TextField();
        grid.add(rocksText, 0, 8);
        grid.add(rocksBox, 1, 8);

        // Water
        Label waterText = new Label("Ponds: ");
        TextField waterBox = new TextField();
        grid.add(waterText, 0, 9);
        grid.add(waterBox, 1, 9);


        // Submit button
        Button submit = new Button("Submit");
        HBox hBox = new HBox(10);
        submit.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(submit);
        grid.add(hBox, 2, 10);

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
                            meat, trees, rocks, pools);
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

        Scene scene = new Scene(grid, 700, 400);
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
}
