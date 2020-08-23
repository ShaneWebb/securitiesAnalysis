package datatypes;

import javautilwrappers.HashMapWrapper;

public class Report {
    
    private final Enum[] fields;
    private final HashMapWrapper<Enum, Object> data;
    
    public Report() {
        fields = null;
        data = null;
    }
    
    public Report(Class<? extends Enum> fieldsClass) {
        this.fields = fieldsClass.getEnumConstants();
        data = new HashMapWrapper<>(this.fields);
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
