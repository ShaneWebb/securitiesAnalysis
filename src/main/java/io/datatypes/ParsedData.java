package io.datatypes;

import javautilwrappers.MapWrapper;

public interface ParsedData<K,V> {

    @Deprecated
    MapWrapper<String, MapWrapper<K, V>> getData();
    
}
