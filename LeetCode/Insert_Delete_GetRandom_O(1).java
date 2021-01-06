class RandomizedSet {
    int numberOfElements;
    int[] array;
    HashMap<Integer, Integer> map;
    Random random;
    
    /** Initialize your data structure here. */
    public RandomizedSet() {
        numberOfElements = 0;
        array = new int[100001];
        map = new HashMap<>();
        random = new Random();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        
        Integer index = map.get(val);
        if (index == null) {
            array[numberOfElements] = val;
            map.put(val, numberOfElements);
            numberOfElements++;
            return true;
        } else {
            return false;
        }
        
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        Integer index = map.get(val);
        if (index == null) {
            return false;
        } else {
            // System.out.println("Value found.");
            numberOfElements--;
            int temp = array[index];
            array[index] = array[numberOfElements];
            array[numberOfElements] = temp;
            map.replace(array[index], index);
            map.remove(val);
            return true;
        }
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        
        int index = random.nextInt(numberOfElements);
        int val = array[index];
        return val;
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
 