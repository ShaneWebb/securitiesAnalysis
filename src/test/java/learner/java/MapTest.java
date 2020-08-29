package learner.java;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
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
        myMap.put(1, "Hello");
        myMap.put(2, "World");
        assertEquals("Hello", myMap.get(1), "Map retrieval should work.");
        assertEquals("World", myMap.get(2), "Map retrieval should work.");
        assertEquals(null, myMap.get(3), "Should return null as key 3 does not exist");
    }

    @Test
    public void treeMapTest() {

        Comparator<String> comparator = (String o1, String o2) -> {
                String[] o1delimited = o1.split(" ");
                String[] o2delimited = o2.split(" ");
                Integer i1 = Integer.parseInt(o1delimited[0]);
                Integer i2 = Integer.parseInt(o2delimited[0]);
                return i1.compareTo(i2);
            };
                
        Map<String, Integer> someMap = new HashMap<>();
        someMap.put("100 - 210", 3);
        someMap.put("10 - 20", 1);
        someMap.put("50 - 60", 2);
        
        SortedSet<String> keys = new TreeSet<>(comparator);
        
        keys.addAll(someMap.keySet());

        for (String key : keys) {
            Integer value = someMap.get(key);
            System.out.println(value);
        }
    }

}
