
package learner.java;

import javautilwrappers.BasicArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

//Test how inner classes override imports, if at all. Note: they do not.
public class InnerClassTest {

     public InnerClassTest() {
    }

     @Test
     @Disabled
     public void InnerTest() {
     
        InnerSuperClass innerSuperClass = new InnerSuperClass();
        InnerSubClass innerSubClass = new InnerSubClass();

        int nonOverriddenReturn = innerSuperClass.basicArrayListUser();
        int overriddenReturn = innerSubClass.basicArrayListUser();
        
        int expectedNonOverridden = 1;
        int expectedOverridden = 1; //Inner class will not "override" an import.
        
        assertEquals(expectedNonOverridden, nonOverriddenReturn);
        assertEquals(expectedOverridden, overriddenReturn);
     
     }
     
     private class InnerSuperClass {

         public int basicArrayListUser() {
             BasicArrayList<Integer> localArrayList = new BasicArrayList<>();
             localArrayList.add(1);
             return localArrayList.get(0);
         }
         
     }
     
     private class InnerSubClass extends InnerSuperClass {
          
         private class BasicArrayList<T>{
             public boolean add(T item) {
                 return false;
             }
             public int get(int index) {
                 return 2;
             }
         }
        
        @Override
        public int basicArrayListUser() {
            //The superclass method still uses the import, even here.
            return super.basicArrayListUser();
         }
         
     }

}