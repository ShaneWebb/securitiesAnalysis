package javautilwrappers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashMapWrapperTest {

    public HashMapWrapperTest() {
    }

    @Test
    public void mapPutTest() {
        MapWrapper<String, Object> map = new HashMapWrapper<>();
        map.put("key", 1);
        assertEquals(1, map.put("key", 2));

    }

}
