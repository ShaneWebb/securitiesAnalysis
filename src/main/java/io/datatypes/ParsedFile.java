package io.datatypes;

import java.util.Iterator;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;

public class ParsedFile<K, V> implements ParsedData<Integer, MapWrapper<String, Object>>,
        ParsedDataNew<K, V> {

    private String name;
    private MapWrapper<K, V> internalMap;

    public ParsedFile(String name) {
        this.name = name;
        this.internalMap = new HashMapWrapper<>();
    }
    
    public void put(K key, V value) {
        this.internalMap.put(key, value);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ListWrapper<K> getKeys() {

        ListWrapper<K> keyList = new ArrayListWrapper<>();
        for (MapWrapper.Entry<K, V> entry : internalMap.entrySet()) {
            keyList.add(entry.getKey());
        }
        return keyList;
    }

    @Override
    public V get(K key) {
        return internalMap.get(key);
    }

    @Override
    public Iterator<V> iterator() {
        
        ListWrapper<V> valList = new ArrayListWrapper<>();
        for (MapWrapper.Entry<K, V> entry : internalMap.entrySet()) {
            valList.add(entry.getValue());
        }
        return valList.iterator();
    }

//<editor-fold defaultstate="collapsed" desc="Old interface">
    private MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> data;

    public ParsedFile(MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> data) {
        this.data = data;
    }

    @Override
    public MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> getData() {
        return this.data;
    }
//</editor-fold>

}
