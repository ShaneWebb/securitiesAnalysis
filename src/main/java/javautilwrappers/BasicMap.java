package javautilwrappers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public Collection<V> values() {
        return internalMap.values();
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasicMap<?, ?> other = (BasicMap<?, ?>) obj;
        if (!Objects.equals(this.internalMap, other.internalMap)) {
            return false;
        }
        return true;
    }

    
    
}
