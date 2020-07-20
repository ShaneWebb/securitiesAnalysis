package learner;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
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
        assertEquals("Map retrieval should work.","Hello",myMap.get(1));
        assertEquals("Map retrieval should work.","World",myMap.get(2));
        assertEquals("Should return null as key 3 does not exist",null,myMap.get(3));
    }
    
}
