package learner.java;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class MapTest {
    
    public MapTest() {
    }

    @Test
    //@Disabled
    public void hashMapTest() {
        Map<Integer, String> myMap = new HashMap<>();
        myMap.put(1,"Hello");
        myMap.put(2, "World");
        assertEquals("Hello", myMap.get(1), "Map retrieval should work.");
        assertEquals("World", myMap.get(2), "Map retrieval should work.");
        assertEquals(null,myMap.get(3), "Should return null as key 3 does not exist");
    }
    
}
