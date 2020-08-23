package learner.java;

import javautilwrappers.HashMapWrapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UtilWrappersTest {

    @Test
    public void equalsImplTest() {
        HashMapWrapper<String, Object> map1 = new HashMapWrapper<>();
        map1.put("A", 1);
        map1.put("B", 2);
        
        HashMapWrapper<String, Object> map2 = new HashMapWrapper<>();
        map2.put("A", 1);
        map2.put("B", 2);
        assertEquals(map1, map2);
    }
}
