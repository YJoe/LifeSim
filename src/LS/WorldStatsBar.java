package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.*;

public class WorldStatsBar {
    private Group group = new Group();
    private Rectangle backBar;
    private Text date;

    public WorldStatsBar(){

        // create backBar
        setBackBar(new Rectangle(0, Main.SIZE_Y, Main.SIZE_X, 50));
        getBackBar().setFill(Color.rgb(50, 50, 50));

        // create date
        setDate(new Text());
        getDate().setFont(Font.font ("Verdana", 30));
        getDate().setTranslateX(185);
        getDate().setTranslateY(Main.SIZE_Y + 35);
        getDate().setFill(Color.rgb(200, 200, 200));

        // add everything to the root group
        group.getChildren().add(getBackBar());
        group.getChildren().add(getDate());
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
