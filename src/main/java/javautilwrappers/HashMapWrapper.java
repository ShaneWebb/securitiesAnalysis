package javautilwrappers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapWrapper<K, V> extends AbstractMapWrapper<K, V> {
    
    public HashMapWrapper(K[] keys) {
        internalMap = new HashMap<>();
        for(K key: keys){
            internalMap.put(key, null);
        }
    }
    
    public HashMapWrapper(){
        internalMap = new HashMap<>();
    }
    
    public HashMapWrapper(Map<? extends K, ? extends V> map) {
        internalMap = new HashMap<>(map);
    }
    
    public HashMapWrapper(MapWrapper<? extends K, ? extends V> map) {
        internalMap = new HashMap<>(map.unwrap());
    }
    
}
