package LS;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodChain implements Serializable {
    private ArrayList<ArrayList<Boolean>> eatList = new ArrayList<>();
    private ArrayList<ArrayList<Boolean>> huntList = new ArrayList<>();

    public FoodChain(ArrayList<ArrayList<Boolean>> eatList, ArrayList<ArrayList<Boolean>> huntList){
        setEatList(eatList);
        setHuntList(huntList);
    }

    public ArrayList<ArrayList<Boolean>> getEatList() {
        return eatList;
    }

    public void setEatList(ArrayList<ArrayList<Boolean>> eatList) {
        this.eatList = eatList;
    }

    public ArrayList<ArrayList<Boolean>> getHuntList() {
        return huntList;
    }

    public void setHuntList(ArrayList<ArrayList<Boolean>> huntList) {
        this.huntList = huntList;
    }
}
