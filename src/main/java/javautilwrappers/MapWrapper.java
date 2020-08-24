package javautilwrappers;

import java.util.Map;

public interface MapWrapper<K, V> {

    SetWrapper<MapWrapper.Entry<K, V>> entrySet();

    V get(K key);

    V put(K key, V value);

    CollectionWrapper<V> values();
    
    Map<K, V> unwrap();

    public interface Entry<K, V> {

        K getKey();

        V getValue();
        
        Map.Entry<K,V> unwrap();
    }

}
