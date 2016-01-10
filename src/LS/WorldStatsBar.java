package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WorldStatsBar {
    private Rectangle backBar;
    private Text date;
    private Text animalCount;
    private Text foodCount;
    private Text shelterCount;

    public WorldStatsBar(Group root){

        // create backBar
        setBackBar(new Rectangle(0, Main.SIZE_Y, Main.SIZE_X, 50));
        getBackBar().setFill(Color.rgb(50, 50, 50));

        // create date
        setDate(new Text());
        getDate().setFont(Font.font ("Verdana", 40));
        getDate().setTranslateX(10);
        getDate().setTranslateY(Main.SIZE_Y + 40);
        getDate().setFill(Color.rgb(200, 200, 200));

        // create animal counter
        setAnimalCount(new Text());
        getAnimalCount().setFont(Font.font ("Verdana", 15));
        getAnimalCount().setTranslateX(215);
        getAnimalCount().setTranslateY(Main.SIZE_Y + 21);
        getAnimalCount().setFill(Color.rgb(200, 200, 200));

        // create food counter
        setFoodCount(new Text());
        getFoodCount().setFont(Font.font("Verdana", 15));
        getFoodCount().setTranslateX(215);
        getFoodCount().setTranslateY(Main.SIZE_Y + 40);
        getFoodCount().setFill(Color.rgb(200, 200, 200));

        // create shelter counter
        setShelterCount(new Text());
        getShelterCount().setFont(Font.font("Verdana", 15));
        getShelterCount().setTranslateX(350);
        getShelterCount().setTranslateY(Main.SIZE_Y + 21);
        getShelterCount().setFill(Color.rgb(200, 200, 200));


        // add everything to the root group
        root.getChildren().add(getBackBar());
        root.getChildren().add(getDate());
        root.getChildren().add(getAnimalCount());
        root.getChildren().add(getFoodCount());
        root.getChildren().add(getShelterCount());
    }


    public Text getDate() {
        return date;
    }

    public void setDate(Text date) {
        this.date = date;
    }

    public void setDateString(String date){
        getDate().setText(date);
    }

    public Rectangle getBackBar() {
        return backBar;
    }

    public void setBackBar(Rectangle backBar) {
        this.backBar = backBar;
    }

    public Text getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(Text animalCount) {
        this.animalCount = animalCount;
    }

    public void setAnimalCountString(int animalCount) {
        getAnimalCount().setText("Animals: " + animalCount);
    }

    public Text getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(Text foodCount) {
        this.foodCount = foodCount;
    }

    public void setFoodCountString(int foodCount){
        getFoodCount().setText("Food " + foodCount);
    }

    public Text getShelterCount() {
        return shelterCount;
    }

    public void setShelterCount(Text shelterCount) {
        this.shelterCount = shelterCount;
    }

    public void setShelterCountString(int shelterCount){
        getShelterCount().setText("Shelters " + shelterCount);
    }
}
