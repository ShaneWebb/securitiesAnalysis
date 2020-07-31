package datatypes;

import datatypes.printlayout.Layout;
import javautilwrappers.BasicMap;

public class Report {
    
    private final Enum[] supportedFields;
    private final BasicMap<Enum, Object> data;
    
    public Report(Class<? extends Enum> fieldsClass) {
        this.supportedFields = fieldsClass.getEnumConstants();
        data = new BasicMap<>(this.supportedFields);
    }

    public <T> T getValueOf(Enum field) {
        return (T) data.get(field);
    }
    
    public <T> void setValue(Enum field, T value) {
        data.put(field, value);
    }

    public Enum[] getFields() {
        return supportedFields;
    }
    
}
