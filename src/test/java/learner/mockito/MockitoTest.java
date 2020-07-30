package learner.mockito;

import javautilwrappers.BasicArrayList;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;

public class MockitoTest {

    public MockitoTest() {
    }

    @Test
    public void mockTest() {
        BasicArrayList<String> mockedList = mock(BasicArrayList.class);
        mockedList.add("one");
        mockedList.remove("one");
        
        verify(mockedList).add("one");
        verify(mockedList).remove("one");
    }
    
    @Test
    public void myFinalMethodTest() {
        MockitoTest myFinalMethodTest = mock(MockitoTest.class);
        
        myFinalMethodTest.myFinalMethod();
        verify(myFinalMethodTest).myFinalMethod();
    }
    
    public void myFinalMethod() {
        
    }

}
