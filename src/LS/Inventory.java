package LS;

/**
 * Inventory allows for a fixed length and size array to be implemented within Animals and Shelters.
 * Each element within the array must be less than or equal to the slotMax
 */
public class Inventory{
    private int capacity;
    private int size;
    private int [] array;
    private int slotMax;

    /**
     * @param capacity The maximum size of the inventory
     * @param slotMax The maximum size value accepted to the inventory
     */
    public Inventory(int capacity, int slotMax){
        // set all attributes
        this.capacity = capacity;
        this.size = 0;
        this.array = new int [capacity];
        this.slotMax = slotMax;
    }

    /**
     * Add an element to the Inventory if the value does not exceed the slotMax
     * @param value Value to add to the Inventory
     * @return if the element was added successfully
     */
    public boolean add(int value){
        // if there is space within the inventory
        if(size < capacity){
            // if the value is less than or equal to the slot max
            if (value <= slotMax) {
                // add the value to the array
                array[size] = value;
                size++;
                return true;
            }
        }
        return false;
    }

    /**
     * Clear all elements from the Inventory
     */
    public void empty(){
        // remove all elements from the Inventory
        for(int i = 0; i < size; i++){
            remove(i);
        }
        size = 0;
    }

    /**
     * Remove a specific element from the Inventory
     * @param index The index of the element to remove
     */
    public void remove(int index){
        // remove a single element at the given index
        for(int i = index; i < size - 1; i++){
            array[i] = array[i + 1];
        }
        size -= 1;
    }

    /**
     * Retrieve an element of a given index form the Inventory
     * @param index The index of the element to retrieve
     * @return The Element
     */
    public int getElement(int index){
        return array[index];
    }

    public int getSlotMax(){
        return slotMax;
    }

    public int getSize(){
        return this.size;
    }

    public int getCapacity(){
        return capacity;
    }

}
