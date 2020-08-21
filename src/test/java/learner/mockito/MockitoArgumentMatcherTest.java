package learner.mockito;

import javautilwrappers.BasicHashMap;
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
        BasicHashMap<String, Object> args = new BasicHashMap<>();
        args.put("Hello", "Kitty");

        myClass.myExamplemethod(args);
        verify(someProcess).setArgs(args);

    }

    public class SomeClass {

        private SupportedProcess supportedProcess;

        SomeClass(SupportedProcess supportedProcess) {
            this.supportedProcess = supportedProcess;
        }

        public void myExamplemethod(BasicHashMap<String, Object> map) {
            supportedProcess.setArgs(map);
        }
    }

}
