package io.datatypes;

import java.util.Date;
import javautilwrappers.MapWrapper;

public class ParsedDatabase implements ParsedData {
    private final MapWrapper<String, MapWrapper<String, Object>> data;

    public ParsedDatabase(MapWrapper<String, MapWrapper<String, Object>> data) {
        this.data = data;
    }

    @Override
    public MapWrapper<String, MapWrapper<String, Object>> getData() {
        return data;
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putNum(Date key, Number value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Number getNum(Date key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putStr(Date key, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getStr(Date key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putData(String key, ParsedData value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParsedData getData(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
