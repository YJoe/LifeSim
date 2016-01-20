package LS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * StatsBar is a configurable visualisation of any amount of stats of an entity
 * specifically within LifeSim StatsBar is used within Animals and Shelter in order to
 * represent thirst, hunger, energy, food inventory and water inventory.
 */
public class StatsBar {
    private int statBarWidth = 50;
    private int statBarHeight = 4;
    private int statBarSpacing = 2;
    private Group statsBarGroup = new Group();
    private Rectangle backBar;
    private ArrayList<Rectangle> bars = new ArrayList<>();

    /**
     * @param x X coordinate
     * @param y Y coordinate
     * @param barCount The amount of statistics to monitor
     */
    public StatsBar(int x, int y, int barCount){
        // Create a backBar to display the other information on
        setBackBar(new Rectangle(x, y, getStatBarWidth() + 4, (getStatBarHeight() * barCount) + (getStatBarSpacing() * (barCount + 1))));
        getBackBar().setFill(Color.rgb(50, 50, 50));
        getBackBar().setX(getBackBar().getX() - (getStatBarWidth() /2) - 2);
        getBackBar().setY(getBackBar().getY() + 8);
        // Add it to the root group
        statsBarGroup.getChildren().add(getBackBar());

        // create all rectangles with sizes and spacing
        for(int i = 0; i < barCount; i++){
            bars.add(new Rectangle(x + getStatBarSpacing(), y + (getStatBarSpacing() * (i + 1)) + (getStatBarHeight() * i), getStatBarWidth(), getStatBarHeight()));
            bars.get(bars.size() - 1).setX(bars.get(bars.size() - 1).getX() - (getStatBarWidth() /2) - 2);
            bars.get(bars.size() - 1).setY(bars.get(bars.size() - 1).getY() + 8);
            bars.get(bars.size() - 1).setFill(Color.rgb(200, 200, 200));
            statsBarGroup.getChildren().add(bars.get(bars.size() - 1));
        }
    }

    public Rectangle getBar(int index){
        return bars.get(index);
    }

    public Group getGroup(){
        return statsBarGroup;
    }

    public Rectangle getBackBar() {
        return backBar;
    }

    public void setBackBar(Rectangle backBar) {
        this.backBar = backBar;
    }

    public int getStatBarWidth() {
        return statBarWidth;
    }

    public void setStatBarWidth(int statBarWidth) {
        this.statBarWidth = statBarWidth;
    }

    public int getStatBarHeight() {
        return statBarHeight;
    }

    public void setStatBarHeight(int statBarHeight) {
        this.statBarHeight = statBarHeight;
    }

    public int getStatBarSpacing() {
        return statBarSpacing;
    }

    public void setStatBarSpacing(int statBarSpacing) {
        this.statBarSpacing = statBarSpacing;
    }
}
