package javautilwrappers;

import java.util.HashMap;
import java.util.Map;

public class BasicMap {
    Map<Integer, String> internalMap;
    
    public BasicMap(){
        internalMap = new HashMap<>();
    }
    
    public void put(int key, String value) {
        internalMap.put(key, value);
    }
    
    public String get(int key) {
        return internalMap.get(key);
    }
    
}
