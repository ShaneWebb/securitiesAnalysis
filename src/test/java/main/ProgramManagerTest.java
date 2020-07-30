package main;

import datatypes.EnvironmentVariables;
import datatypes.Report;
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

//    @Mock
//    myClass someClass;
    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ProgramManager instance = 
                ProgramManager.createFrom(new ProgramManager.DefaultFactory());
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    public ProgramManagerTest() {
    }

//    @ParameterizedTest
//    @MethodSource("startRequiredProcessesArguments")
//    public void testStartRequiredProcesses(
//            Report auditReport, BasicArrayList<SupportedProcess> expectedProcesses) {
//        instance.setAuditResult(auditReport);
//        instance.startRequiredProcesses();
//        
//        BasicArrayList<SupportedProcess> runningProcesses = instance.getRunning();
//        assertEquals(expectedProcesses, runningProcesses);        
//    }

    private static Stream<Arguments> startRequiredProcessesArguments() {
        Report auditFailed = new Report("Audit Degree", "Status", "Inconsistencies"); 
        auditFailed.setValue("Status", "Unable to audit!");
        
        Report auditClean = new Report("Audit Degree", "Status", "Inconsistencies"); 
        auditClean.setValue("Status", "Consistency Check Passed!");
        
        Report auditUnclean = new Report("Audit Degree", "Status", "Inconsistencies"); 
        auditUnclean.setValue("Status", "Consistency Check Failed!");

        return Stream.of(
                Arguments.of(auditFailed, true),
                Arguments.of(auditClean, true),
                Arguments.of(auditUnclean, true)
        );
    }

    @Test
    public void testGetReports() {

    }

    @Test
    public void testAcceptUserInput() {

    }

    @Test
    public void testGetProgramActiveStatus() {

    }

    @Test
    public void testStopAll() {

    }

}
