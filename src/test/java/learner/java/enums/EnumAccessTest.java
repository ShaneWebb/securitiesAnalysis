package learner.java.enums;

import java.util.EnumSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

public class EnumAccessTest {

    public EnumAccessTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void accessTest() {
        MyTestEnumWrapperClass myFruitEnumClass = new MyTestEnumWrapperClass(FruitEnums.class);
        MyTestEnumWrapperClass myVegetableEnumClass = new MyTestEnumWrapperClass(VegetableEnums.class);
        MyTestEnumWrapperClass myInvalidEnumClass = new MyTestEnumWrapperClass(Integer.class);

        assertArrayEquals(FruitEnums.values(), myFruitEnumClass.getEnums());
        assertArrayEquals(VegetableEnums.values(), myVegetableEnumClass.getEnums());
        assertNull(myInvalidEnumClass.getEnums());

    }

    @Test
    @Disabled
    public void enumGenericTest() {
        MyGenericEnumClass<FruitEnums> validInstance
                = new MyGenericEnumClass<>(FruitEnums.values());

        //validInstance.insertOneField(VegetableEnums.CELERY); //Error
        //MyGenericEnumClass<String> invalidInstance = new MyGenericEnumClass(); //Error
        MyGenericEnumClass ambiguousInstance = new MyGenericEnumClass(VegetableEnums.values());
        ambiguousInstance.insertOneField(FruitEnums.APPLE);
    }

    private class MyTestEnumWrapperClass<E extends Enum<E>> {

        E[] enums;

        MyTestEnumWrapperClass(Class<E> inputEnumType) {
            enums = inputEnumType.getEnumConstants();
        }

        E[] getEnums() {
            return enums;
        }

    }

    private class MyGenericEnumClass<E extends Enum<E>> {

        E[] fields;

        MyGenericEnumClass(E[] fields) {
            this.fields = fields;
        }

        public MyGenericEnumClass() {
        }

        void insertOneField(E myEnum) {

        }

    }

}
