package LS;

public class Inventory extends FLArray{
    private int slotMax;

    public Inventory(int capacity, int slotMax){
        super(capacity);
        this.slotMax = slotMax;
    }

    @Override
    public boolean add(int value){
        if(size < capacity){
            if (value < slotMax) {
                array[size] = value;
                size++;
                return true;
            }
        }
        return false;
    }

    public int getSlotMax(){
        return slotMax;
    }

}
