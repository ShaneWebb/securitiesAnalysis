package learner.java.enums;

public enum FruitEnums implements SpecialEnumType {
    APPLE, ORANGE, PEAR;
    private int value;
    
    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
}
