
package learner.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

//Calling a non-overrided method from a subclass that itself calls methods
//which are overrided will alter the result as expected.
public class MethodOverrideTest {

     public MethodOverrideTest() {
    }

    @Test
    @Disabled
    public void overrideTest() {
        MySuperClass mySuperClass = new MySuperClass();
        MySuperClass mySubClass = new MySubClass();
        
        int expectedNonOverridedResult = 2;
        int nonOverridedResult = mySuperClass.getSumOfTwoMethods();
        
        int expectedOverridedResult = 4;
        int overridedResult = mySubClass.getSumOfTwoMethods();
        
        assertEquals(expectedNonOverridedResult, nonOverridedResult);
        assertEquals(expectedOverridedResult, overridedResult);
        
    }
    
    public class MySuperClass {
        
        public int getSumOfTwoMethods() {
            int resultFirst = getFirst();
            int resultSecond = getSecond();
            return resultFirst + resultSecond;
            
        }
        
        public int getFirst() {
            return 1;
        }
        
        public int getSecond() {
            return 1;
        }
        
    }
    
    public class MySubClass extends MySuperClass {
        
        @Override
        public int getFirst() {
            return 2;
        }
        
        @Override
        public int getSecond() {
            return 2;
        }
    }

}