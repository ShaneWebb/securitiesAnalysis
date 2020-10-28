package javautilwrappers;

import java.util.Map;

public interface MapWrapper<K, V> {

    SetWrapper<MapWrapper.Entry<K, V>> entrySet();

    V get(K key);

    V put(K key, V value);
    
    V remove(K key);

    CollectionWrapper<V> values();
    
    Map<K, V> unwrap();
    
    void clear();
    
    void putAll(MapWrapper<? extends K, ? extends V> m);

    public interface Entry<K, V> {

        K getKey();

        V getValue();
        
        Map.Entry<K,V> unwrap();
    }

}
