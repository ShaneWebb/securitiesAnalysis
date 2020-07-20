package datatypes.printlayout.fields;

public class LayoutFillerField implements LayoutField {
    
    @Override
    public String convert(String border, String filler) {
        return filler;
    }
    
}
