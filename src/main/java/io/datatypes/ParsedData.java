package io.datatypes;

import javautilwrappers.MapWrapper;

public interface ParsedData<K,V> {

    MapWrapper<String, MapWrapper<K, V>> getData();
    
}
