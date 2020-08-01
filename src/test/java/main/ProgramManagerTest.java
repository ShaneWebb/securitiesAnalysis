package main;

import process.SupportedProcess;
import datatypes.EnvironmentVariables;
import datatypes.Report;
import io.database.audit.AuditReportFields;
import io.database.audit.AuditStatus;
import java.util.stream.Stream;
import javautilwrappers.BasicArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import process.ProgramManager;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ProgramManagerTest {

    private AutoCloseable closeable;
    private ProgramManager instance;

    @Mock
    private SupportedProcess runOnStart;

    @Mock
    private SupportedProcess doNotRunOnStart;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        when(runOnStart.runsOnStart()).thenReturn(true);
        when(doNotRunOnStart.runsOnStart()).thenReturn(false);
        
        instance = ProgramManager.createFrom(new TestFactory());
    }

    private class TestFactory implements Supplier<ProgramManager> {

        SupportedProcess[] supportedProcessList = {
            runOnStart,
            doNotRunOnStart};
  
        @Override
        public ProgramManager get() {
            return new ProgramManager(
                    EnvironmentVariables.INSTANCE,
                    supportedProcessList);
        }

    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    public ProgramManagerTest() {
    }

    @Test
    public void testStartAllProcesses() {
        Report auditReport = new Report(AuditReportFields.class);
        instance.setAuditReport(auditReport);
        instance.startAllProcesses();
        verify(runOnStart).run();
        verify(runOnStart).setAuditReport(auditReport);
        verify(doNotRunOnStart, never()).run();
        verify(doNotRunOnStart, never()).setAuditReport(auditReport);
    }

//    @Test
//    public void testGetReports() {
//
//    }
//
//    @Test
//    public void testAcceptUserInput() {
//
//    }
//
//    @Test
//    public void testGetProgramActiveStatus() {
//
//    }
//
//    @Test
//    public void testStopAll() {
//
//    }

}
