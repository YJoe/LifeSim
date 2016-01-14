package LS;

public class FoodChain {
    private boolean [][] eatList = new boolean[4][5];
    private boolean [][] huntList = new boolean[4][4];

    public FoodChain(boolean [][] eatList, boolean [][] huntList){
        setEatList(eatList);
        setHuntList(huntList);
    }

    public boolean[][] getEatList() {
        return eatList;
    }

    public void setEatList(boolean[][] eatList) {
        this.eatList = eatList;
    }

    public boolean[][] getHuntList() {
        return huntList;
    }

    public void setHuntList(boolean[][] huntList) {
        this.huntList = huntList;
    }
}
