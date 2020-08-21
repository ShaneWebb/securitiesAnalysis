package learner.java;

import javautilwrappers.BasicMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UtilWrappersTest {

    @Test
    public void equalsImplTest() {
        BasicMap<String, Object> map1 = new BasicMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        
        BasicMap<String, Object> map2 = new BasicMap<>();
        map2.put("A", 1);
        map2.put("B", 2);
        assertEquals(map1, map2);
    }
}
