package learner.java;

import javautilwrappers.BasicHashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UtilWrappersTest {

    @Test
    public void equalsImplTest() {
        BasicHashMap<String, Object> map1 = new BasicHashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        
        BasicHashMap<String, Object> map2 = new BasicHashMap<>();
        map2.put("A", 1);
        map2.put("B", 2);
        assertEquals(map1, map2);
    }
}
