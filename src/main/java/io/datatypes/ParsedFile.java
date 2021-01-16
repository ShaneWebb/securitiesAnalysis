package io.datatypes;

import java.util.Date;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

public class ParsedFile<K, V> implements ParsedData<Integer, MapWrapper<String, Object>> {

    private String name;
    private MapWrapper<K, V> internalMap;
    private MapWrapper<Date, Number> numsMap;
    private MapWrapper<Date, String> strMap;
    private MapWrapper<String, ParsedData> dataMap;

    public ParsedFile(String name) {
        this.name = name;
        this.internalMap = new HashMapWrapper<>();
        this.numsMap = new HashMapWrapper<>();
        this.strMap = new HashMapWrapper<>();
        this.dataMap = new HashMapWrapper<>();
    }
    
    public void put(K key, V value) {
        this.internalMap.put(key, value);
    }

    private MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> data;

    public ParsedFile(MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> data) {
        this.data = data;
    }

    @Override
    public MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> getData() {
        return this.data;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T extends Number> void putNum(Date key, T value) {
        this.numsMap.put(key, value);
    }

    @Override
    public <T extends Number> T getNum(Date key) {
        return (T) this.numsMap.get(key);
    }

    @Override
    public void putStr(Date key, String value) {
        this.strMap.put(key, value);
    }

    @Override
    public String getStr(Date key) {
        return this.strMap.get(key);
    }

    @Override
    public void putData(String key, ParsedData value) {
        this.dataMap.put(key, value);
    }

    @Override
    public ParsedData getData(String key) {
        return this.dataMap.get(key);
    }

}
