package learner.mockito;

import javautilwrappers.HashMapWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void verifyArgs() throws Exception {
        SomeClass myClass = new SomeClass(someProcess);
        HashMapWrapper<String, Object> args = new HashMapWrapper<>();
        args.put("Hello", "Kitty");

        myClass.myExamplemethod(args);
        verify(someProcess).execute(args);

    }

    public class SomeClass {

        private SupportedProcess supportedProcess;

        SomeClass(SupportedProcess supportedProcess) {
            this.supportedProcess = supportedProcess;
        }

        public void myExamplemethod(HashMapWrapper<String, Object> map) throws Exception {
            supportedProcess.execute(map);
        }
    }

}
