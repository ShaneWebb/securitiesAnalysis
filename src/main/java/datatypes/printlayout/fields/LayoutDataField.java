package datatypes.printlayout.fields;

public class LayoutDataField implements LayoutField {
    
    String internalString;
    
    public LayoutDataField(String internalString) {
        this.internalString = internalString;
    }
    
    @Override //Data item does not use borders or fillers.
    public String convert(String border, String filler) {
        return internalString;
    }
    
}
