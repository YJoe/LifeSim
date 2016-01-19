package LS;

public class Inventory{
    private int capacity;
    private int size;
    private int [] array;
    private int slotMax;

    public Inventory(int capacity, int slotMax){
        this.capacity = capacity;
        this.size = 0;
        this.array = new int [capacity];
        this.slotMax = slotMax;
    }

    public boolean add(int value){
        if(size < capacity){
            if (value <= slotMax) {
                array[size] = value;
                size++;
                return true;
            }
        }
        return false;
    }

    public void empty(){
        for(int i = 0; i < size; i++){
            remove(i);
        }
        size = 0;
    }

    public void remove(int index){
        for(int i = index; i < size - 1; i++){
            array[i] = array[i + 1];
        }
        size -= 1;
    }

    public int getSlotMax(){
        return slotMax;
    }

    public int getElement(int index){
        return array[index];
    }

    public int getSize(){
        return this.size;
    }

    public int getCapacity(){
        return capacity;
    }

}
