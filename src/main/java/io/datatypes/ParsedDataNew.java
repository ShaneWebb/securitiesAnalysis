package io.datatypes;

import javautilwrappers.ListWrapper;

public interface ParsedDataNew<K, V> extends Iterable<V> {
    
    String getName();
    
    ListWrapper<K> getKeys();
    
    V get(K key);
    
}
