package main;

import process.SupportedProcess;
import datatypes.EnvironmentVariables;
import datatypes.Report;
import io.database.audit.AuditReportFields;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import process.FullReportFields;
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
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    private class TestFactory implements Supplier<ProgramManager> {

        private final SupportedProcess[] supportedProcessList = {
            runOnStart,
            doNotRunOnStart};

        @Override
        public ProgramManager get() {
            return new ProgramManager(
                    EnvironmentVariables.INSTANCE,
                    supportedProcessList);
        }

    }

    public ProgramManagerTest() {
    }

    @Test
    public void testStartAllProcesses() {
        when(runOnStart.runsOnStart()).thenReturn(true);
        when(doNotRunOnStart.runsOnStart()).thenReturn(false);
        instance = ProgramManager.createFrom(new TestFactory());
        Report auditReport = new Report(AuditReportFields.class);
        instance.setAuditReport(auditReport);

        instance.startAllProcesses();

        verify(runOnStart).createThread();
        verify(runOnStart).setAuditReport(auditReport);
        verify(doNotRunOnStart, never()).createThread();
        verify(doNotRunOnStart, never()).setAuditReport(auditReport);
    }

    @Test //Must return a condensed, holistic report.
    public void testGetFullReport() {
        instance = ProgramManager.createFrom(new TestFactory());
        Report auditReport = new Report(AuditReportFields.class);
        instance.setAuditReport(auditReport);

        instance.startAllProcesses();

        Report fullReport = instance.getFullReport();
        assertNotNull(fullReport);
    }

    //Note: This should be a part of the acceptance test.
//    @Test
//    public void testAcceptUserInput() {
//        
//    }
    
    @Test
    public void testGetProgramActiveStatus() {
        instance = ProgramManager.createFrom(new TestFactory());
        Report auditReport = new Report(AuditReportFields.class);
        instance.setAuditReport(auditReport);

        instance.startAllProcesses();
        
        Boolean programIsActive;
        instance.setProgramActiveStatus(false);
        programIsActive = instance.getProgramActiveStatus();
        assertFalse(programIsActive);
        
        instance.setProgramActiveStatus(true);
        programIsActive = instance.getProgramActiveStatus();
        assertTrue(programIsActive);
        
    }

    @Test
    public void testStopAll() {
        
        instance = ProgramManager.createFrom(new TestFactory());
        Report auditReport = new Report(AuditReportFields.class);
        instance.setAuditReport(auditReport);

        instance.startAllProcesses();
        instance.stopAllProcesses();
        
        verify(runOnStart).stopThreads();
        verify(runOnStart).stopThreads();
        
    }

}
