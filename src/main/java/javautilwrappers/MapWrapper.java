package javautilwrappers;

import java.util.Collection;

public interface MapWrapper<K, V> {

    SetWrapper<MapWrapper.Entry<K, V>> entrySet();

    V get(K key);

    V put(K key, V value);

    Collection<V> values();

    public interface Entry<K, V> {

        K getKey();

        V getValue();
    }

}
