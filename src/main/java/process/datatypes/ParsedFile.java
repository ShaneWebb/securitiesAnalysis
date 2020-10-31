package process.datatypes;

import javautilwrappers.MapWrapper;

public class ParsedFile implements ParsedData<Integer, String> {

    private final MapWrapper<String, MapWrapper<Integer, String>> data;

    public ParsedFile(MapWrapper<String, MapWrapper<Integer, String>> data) {
        this.data = data;
    }
    
    @Override
    public MapWrapper<String, MapWrapper<Integer, String>> getData() {
        return this.data;
    }
    
}
