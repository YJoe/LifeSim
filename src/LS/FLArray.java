package LS;

public class FLArray {
    protected int capacity;
    protected int size;
    protected int [] array;

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
