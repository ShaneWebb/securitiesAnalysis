package javautilwrappers;

import java.util.HashMap;
import java.util.Map;

public class BasicMap<T, U> {
    Map<T, U> internalMap;
    
    public BasicMap(){
        internalMap = new HashMap<>();
    }
    
    public void put(T key, U value) {
        internalMap.put(key, value);
    }
    
    public U get(T key) {
        return internalMap.get(key);
    }
    
}
