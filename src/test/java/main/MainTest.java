package main;

import process.ProgramManager;
import datatypes.Report;
import datatypes.EnvironmentVariables;
import io.database.audit.Auditor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import view.PrettyPrint;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class MainTest {

    private AutoCloseable closeable;
    private Main instance;

    @Mock
    private Auditor mockAuditor;
    @Mock
    private ProgramManager mockProgramManager;
    @Mock
    private Report mockReport;
    @Mock
    private Report mockProgramStatus;
    @Mock
    private PrettyPrint mockPrinter;

    public MainTest() {
    }
    
    class TestingFactoryMain implements Supplier<Main> {

        @Override
        public Main get() {
            Main instance = new Main(
                mockAuditor,
                mockProgramManager,
                mockPrinter);
            
            return instance;
        }
        
    }

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        instance = Main.createFrom(new TestingFactoryMain());

        when(mockAuditor.audit()).thenReturn(mockReport);
        when(mockProgramManager.getProgramActiveStatus()).thenReturn(false);
        when(mockProgramManager.getReports()).thenReturn(mockProgramStatus);

    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    public void programStateTest() {

        instance.run();
        verify(mockAuditor).audit();
        verify(mockProgramManager).startAllProcesses();
        verify(mockProgramManager).acceptUserInput();
        verify(mockProgramManager).stopAllProcesses();

    }

    @Test
    @Disabled //TODO: Finish me later
    public void generateLayoutTest() {

    }

}
