package javautilwrappers;

import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BasicArrayListTest {
    
    public BasicArrayListTest() {
    }

    @Test
    @Disabled
    public void testAdd() {
        Object item = null;
        BasicArrayList instance = new BasicArrayList();
        boolean expResult = false;
        boolean result = instance.add(item);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testRemove() {
        Object item = null;
        BasicArrayList instance = new BasicArrayList();
        boolean expResult = false;
        boolean result = instance.remove(item);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testIterator() {
        BasicArrayList instance = new BasicArrayList();
        Iterator expResult = null;
        Iterator result = instance.iterator();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
