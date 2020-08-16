package javautilwrappers;

import java.util.HashMap;
import java.util.Map;

public class BasicMap<K, V> {
    Map<K, V> internalMap;
    
    public BasicMap(K[] keys) {
        internalMap = new HashMap<>();
        for(K key: keys){
            internalMap.put(key, null);
        }
    }
    
    public BasicMap(Map map) {
        internalMap = new HashMap<>(map);
    }
    
    public BasicMap(){
        internalMap = new HashMap<>();
    }
    
    public V put(K key, V value) {
        return internalMap.put(key, value);
    }
    
    public V get(K key) {
        return internalMap.get(key);
    }
    
}
