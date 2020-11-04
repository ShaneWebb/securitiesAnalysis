package learner.java.enums;

import java.util.EnumMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EnumMapTest {
    
    @Test
    public void enumMapExample() {
        EnumMap<VegetableEnums, Double> myMap = new EnumMap<>(VegetableEnums.class);
        myMap.put(VegetableEnums.CELERY, 1.0);
        assertEquals(1.0, myMap.get(VegetableEnums.CELERY));
    }
    
}
