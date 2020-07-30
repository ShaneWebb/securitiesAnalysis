package datatypes;

import datatypes.printlayout.Layout;
import javautilwrappers.BasicMap;

public class Report {
    
    private final String[] supportedFields;
    private BasicMap<String, Object> data;
    
    public Report(String ... supportedFields) {
        data = new BasicMap<>();
        this.supportedFields = supportedFields;
    }

    public <T> T getValueOf(String field) {
        return (T) data.get(field);
    }
    
    public <T> void setValue(String field, T value) {
        data.put(field, value);
    }

    public String[] getFields() {
        return supportedFields;
    }
    
}
