/*
 * The following is a suite of tests used solely to test Java features, API's,
 * and more. 
*/

package learner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Basic {
    
    public Basic() {
    }

     @Test
     public void firstEverTest() {
         System.out.println("Hello World!");
         fail();
     }
}
