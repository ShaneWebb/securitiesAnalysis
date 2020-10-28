package main;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import process.ProgramManager;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class MainTest {

    private AutoCloseable closeable;
    private Main instance;
    private String inputCommand;

    @Mock
    private ProgramManager mockProgramManager;

    public MainTest() {
    }
    
    class TestingFactoryMain implements Supplier<Main> {

        @Override
        public Main get() {
            Main instance = new Main(
                mockProgramManager,
                new Scanner(System.in));
            
            return instance;
        }
        
    }

    @BeforeEach
    public void openMocks() {
        
        inputCommand = "Example Command (Does not run)";
        InputStream in = new ByteArrayInputStream(inputCommand.getBytes());
        System.setIn(in);
        
        closeable = MockitoAnnotations.openMocks(this);
        instance = Main.createFrom(new TestingFactoryMain());

    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    public void programStateTest() {
        
        instance.run();
        verify(mockProgramManager).startAllProcesses();
        verify(mockProgramManager).runUserInputCommand(inputCommand);
        verify(mockProgramManager).stopAllProcesses();

    }

}
