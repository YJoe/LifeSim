package LS;

public class FLArray {
    private int capacity;
    private int size;
    private int [] array;

    public FLArray(int capacity){
        this.capacity = capacity;
        this.size = 0;
        array = new int [capacity];
    }

    public boolean add(int value){
        if(size < capacity){
            array[size] = value;
            size++;
            return true;
        }
        return false;
    }

    public void remove(int index){
        for(int i = index; i < size - 1; i++){
            array[i] = array[i + 1];
        }
        size -= 1;
    }

    public int getSize(){
        return this.size;
    }

    public int getCapacity(){
        return capacity;
    }
}
