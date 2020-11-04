package process.datatypes;

import javautilwrappers.MapWrapper;

public class ParsedFile implements ParsedData<Integer, MapWrapper<String, Object>> {

    private final MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> data;

    public ParsedFile(MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> data) {
        this.data = data;
    }
    
    @Override
    public MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> getData() {
        return this.data;
    }
    
}
