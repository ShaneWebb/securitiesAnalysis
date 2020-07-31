package learner.java.classtype;

import learner.java.enums.FruitEnums;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

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

}
