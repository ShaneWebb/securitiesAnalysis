package datatypes;

import javautilwrappers.BasicHashMap;

public class Report {
    
    private final Enum[] fields;
    private final BasicHashMap<Enum, Object> data;
    
    public Report() {
        fields = null;
        data = null;
    }
    
    public Report(Class<? extends Enum> fieldsClass) {
        this.fields = fieldsClass.getEnumConstants();
        data = new BasicHashMap<>(this.fields);
    }

    public <T> T getValueOf(Enum field) {
        return (T) data.get(field);
    }
    
    public <T> void setValue(Enum field, T value) {
        data.put(field, value);
    }

    public Enum[] getFields() {
        return fields;
    }
    
}
