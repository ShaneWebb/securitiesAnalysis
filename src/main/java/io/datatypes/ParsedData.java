package io.datatypes;

import java.util.Date;
import javautilwrappers.MapWrapper;

public interface ParsedData<K,V> {

    @Deprecated
    MapWrapper<String, MapWrapper<K, V>> getData();
    
    String getName();
    
    <T extends Number> void putNum(Date key, T value);
    
    <T extends Number> T getNum(Date key);
    
    void putStr(Date key, String value);    
    
    String getStr(Date key);
    
    void putData(String key, ParsedData value);
    
    ParsedData getData(String key);
    
}
