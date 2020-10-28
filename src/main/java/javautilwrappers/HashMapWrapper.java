package javautilwrappers;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void clear() {
        internalMap.clear();
    }

    @Override
    public void putAll(MapWrapper<? extends K, ? extends V> map) {
        internalMap.putAll(map.unwrap());
    }
    
}
