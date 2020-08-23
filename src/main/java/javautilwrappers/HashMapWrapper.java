package javautilwrappers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HashMapWrapper<K, V> extends AbstractMapWrapper<K, V> {
    
    public HashMapWrapper(K[] keys) {
        internalMap = new HashMap<>();
        for(K key: keys){
            internalMap.put(key, null);
        }
    }
    
    public HashMapWrapper(Map map) {
        internalMap = new HashMap<>(map);
    }
    
    public HashMapWrapper(){
        internalMap = new HashMap<>();
    }
    
    @Override
    public V put(K key, V value) {
        return internalMap.put(key, value);
    }
    
    @Override
    public V get(K key) {
        return internalMap.get(key);
    }

    @Override
    public Collection<V> values() {
        return internalMap.values();
    }
    
    @Override
    public String toString(){
        return internalMap.toString();
    }

    @Override
    public SetWrapper<MapWrapper.Entry<K, V>> entrySet() {
        return null;
    }

    final class Entry<K,V> {
        
    }
    
}
