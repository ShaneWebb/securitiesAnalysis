package process.datatypes;

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

}
