package learner.mockito;

import javautilwrappers.BasicMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import process.SupportedProcess;

public class MockitoArgumentMatcherTest {

    private AutoCloseable closeable;
    
    @Mock
    private SupportedProcess someProcess;
    
    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }
    
    @Test
    public void verifyArgs() {
        SomeClass myClass = new SomeClass(someProcess);
        BasicMap<String, Object> args = new BasicMap<>();
        args.put("Hello", "Kitty");
        
        myClass.myExamplemethod(args);
        verify(someProcess).setArgs(args);
        
//        myClass.myExamplemethod(args);
//        BasicMap<String, Object> emptyArgs = new BasicMap<>();
//        verify(someProcess).setArgs(eq(emptyArgs));
        
    }
    
    public class SomeClass {
        
        private SupportedProcess supportedProcess;
        
        SomeClass(SupportedProcess supportedProcess) {
            this.supportedProcess = supportedProcess;
        }
        
        public void myExamplemethod(BasicMap<String, Object> map) {
            supportedProcess.setArgs(map);
        }
    }

}
