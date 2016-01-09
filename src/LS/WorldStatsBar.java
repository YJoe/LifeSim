package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WorldStatsBar {
    private Rectangle backBar;
    private Text date;

    public WorldStatsBar(Group root){

        // create backBar
        setBackBar(new Rectangle(0, Main.SIZE_Y, Main.SIZE_X, 50));
        getBackBar().setFill(Color.rgb(50, 50, 50));

        // create date
        setDate(new Text("Y0 D0"));
        getDate().setFont(Font.font ("Verdana", 40));
        getDate().setTranslateX(10);
        getDate().setTranslateY(Main.SIZE_Y + 40);
        getDate().setFill(Color.rgb(200, 200, 200));

        // add everything to the root group
        root.getChildren().add(getBackBar());
        root.getChildren().add(getDate());
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
}
