package LS;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SimulationMenu {
    private MenuBar menuBar;
    private Configuration configuration;
    private World currentWorld;
    private boolean isPaused = false;

    public SimulationMenu(Stage primaryStage){
        //Top Menu Bar
        menuBar = new MenuBar();
        menuBar.setOpacity(0.8);
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
                System.out.println("NewConfig Clicked");
            }
        });
        fileOpenConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("OpenConfig Clicked");
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

                Button btn = new Button("Submit");
                HBox hbBtn = new HBox(10);
                hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
                hbBtn.getChildren().add(btn);
                grid.add(hbBtn, 1, 2);

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

    public void saveConfiguration(){
        Serialize.serialize(getConfiguration(), "MyWorld");
    }
    public void saveConfiguration(String name){
        Serialize.serialize(getConfiguration(), name);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void createWorld(Group root){
        // clear everything from the root
        root.getChildren().clear();

        // start the simulation in the paused state
        setPaused(true);

        // create the new world with the current configuration
        setCurrentWorld(new World(root, getConfiguration()));
        getCurrentWorld().toggleAnimalLabels();
        getCurrentWorld().toggleTargetSquares();
        getCurrentWorld().toggleHomeSquares();

        // add the menu bar back in
        root.getChildren().add(getMenuBar());
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
}
