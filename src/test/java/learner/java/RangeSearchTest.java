package learner.java;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RangeSearchTest {

    @Test
    public void exampleRangeSearch() {
        assertEquals(0, rangeSearch(0));
        assertEquals(1, rangeSearch(6));
        assertEquals(2, rangeSearch(101));
    }
    
    public int rangeSearch(int key) {
        
        class Range {
            public int upper, value;
            public Range(int upper, int value) {
                this.upper = upper;
                this.value = value;
            }
        }

        NavigableMap<Integer, Range> map = new TreeMap<>();
        map.put(0, new Range(3, 0));       // 0..3     => 0
        map.put(5, new Range(10, 1));      // 5..10    => 1
        map.put(100, new Range(200, 2));   // 100..200 => 2

        // To do a lookup for some value in 'key'
        Map.Entry<Integer, Range> entry = map.floorEntry(key);
        if (entry == null) {
            return 0;
        } else if (key <= entry.getValue().upper) {
            return entry.getValue().value;
        } else {
            return 0;
        }
    }

}
