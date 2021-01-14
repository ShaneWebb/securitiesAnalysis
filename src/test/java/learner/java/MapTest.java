package learner.java;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.*;
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
                
        Map<String, Integer> someMap = new HashMap();
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
    
    @Test
    public void typeTest() {
        Map myMap = new HashMap();
        myMap.put(1, "SomeStr");
        myMap.put(1.0, "AnotherStr");
        assertEquals(myMap.size(), 2);
        assertEquals(myMap.get(1), "SomeStr");
        assertEquals(myMap.get(1.0), "AnotherStr");
    }
    
    @Test
    public void rawTypes() {
        Map someRawMap = new HashMap();
        Map<String, String> someMap = someRawMap;
        //someMap.put(1, 1); //Error, does not work.
        someRawMap.put(1, 1);
        
        //Allowed despite being Integers.
        someMap = someRawMap;
        
        //Effectively bypassed the String type restriction.
        assertEquals(someMap.get(1),1);   
    }
    
    @Test
    public void rawTypes2() {
        Map<String, String> someMap = new HashMap<>();
        someMap.put("Hello", "World");
        //someMap.put(2,2); //Error
        
        Map someRawMap = someMap;
        someRawMap.put(1, 1);
        //someMap.put(2, 2); //Still an error.
        
        //Due to raw types, was able to force Integers into a String map, despite
        //the checked version someMap not allowing it. someRawMap acts as a
        //loophole.
        assertEquals(someMap.get(1),1);
    }

}
