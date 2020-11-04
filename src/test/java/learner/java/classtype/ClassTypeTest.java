package learner.java.classtype;

import learner.java.enums.FruitEnums;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ClassTypeTest {

    public ClassTypeTest() {
    }

    @Test
    //@Disabled
    public void classTypeGenericTest() {
        MyClassWrapper<Class<? extends Enum>> newWrapper = new MyClassWrapper<>(FruitEnums.class);
        newWrapper.getMyClassField();
    }
    
    public class MyClassWrapper <E extends Class<?>> {
        E myClassField;
        MyClassWrapper(E myClassField) {
            this.myClassField = myClassField;
        }

        public E getMyClassField() {
            return myClassField;
        }
       
    }
    
    @Test
    @Disabled
    public void classCastingTest() {
        double myDouble = 1.111;
        int someCastTest = Integer.class.cast(myDouble);
        assertEquals(1,someCastTest);
    }

}
