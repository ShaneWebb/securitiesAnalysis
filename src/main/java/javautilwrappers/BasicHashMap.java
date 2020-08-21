package javautilwrappers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BasicHashMap<K, V> {
    Map<K, V> internalHashMap;
    
    public BasicHashMap(K[] keys) {
        internalHashMap = new HashMap<>();
        for(K key: keys){
            internalHashMap.put(key, null);
        }
    }
    
    public BasicHashMap(Map map) {
        internalHashMap = new HashMap<>(map);
    }
    
    public BasicHashMap(){
        internalHashMap = new HashMap<>();
    }
    
    public V put(K key, V value) {
        return internalHashMap.put(key, value);
    }
    
    public V get(K key) {
        return internalHashMap.get(key);
    }

    public Collection<V> values() {
        return internalHashMap.values();
    }

    @Override
    public int hashCode() {
        return internalHashMap.hashCode();
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
        final BasicHashMap<?, ?> other = (BasicHashMap<?, ?>) obj;
        if (!Objects.equals(this.internalHashMap, other.internalHashMap)) {
            return false;
        }
        return true;
    }

    
    
}
