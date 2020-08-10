package main;

import io.console.ArgumentParserWrapper;
import process.SupportedProcess;
import datatypes.EnvironmentVariables;
import datatypes.Report;
import io.console.SubparserWrapper;
import io.database.audit.AuditReportFields;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import process.ProgramManager;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ProgramManagerTest {

    private AutoCloseable closeable;
    private ProgramManager instance;
    private ArgumentParserWrapper argParser;

    @Mock
    private SupportedProcess runOnStart, doNotRunOnStart, processOne, processTwo;

    @Mock
    private ArgumentParserWrapper argparser;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);;
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
                    supportedProcessList,
                    argParser);
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

    @ParameterizedTest
    @MethodSource("provideCommandAndProcesses")
    public void testRunUserInputCommand(String commandArg, String testMode) {

        class LocalTestFactory implements Supplier<ProgramManager> {

            private final SupportedProcess[] supportedProcessList;
            private final ArgumentParserWrapper localArgParser;
                    
            LocalTestFactory() {
                supportedProcessList = new SupportedProcess[] {processOne, processTwo};
                localArgParser = new ArgumentParserWrapper("Test", "Test Help");
                
                SubparserWrapper commandOne = localArgParser.addParser("CommandOne", "Command One Help");
                commandOne.setDefault("func", processOne);
                SubparserWrapper commandTwo = localArgParser.addParser("CommandTwo", "Command One Help");
                commandTwo.setDefault("func", processTwo);
            }

            @Override
            public ProgramManager get() {
                return new ProgramManager(
                        EnvironmentVariables.INSTANCE,
                        supportedProcessList,
                        localArgParser);
            }

        }

        instance = ProgramManager.createFrom(new LocalTestFactory());
        instance.runUserInputCommand(commandArg);

        switch (testMode) {
            case "one":
                verify(processOne).execute();
                verify(processTwo, never()).execute();
                break;
            case "two":
                verify(processOne, never()).execute();
                verify(processTwo).execute();
                break;
            case "none":
                verify(processOne, never()).execute();
                verify(processTwo, never()).execute();
        }

    }

    private static Stream<Arguments> provideCommandAndProcesses() {
        return Stream.of(
                Arguments.of("CommandOne", "one"),
                Arguments.of("CommandTwo", "two"),
                Arguments.of("Purposely!@#$ invalid command", "none"),
                Arguments.of("", "none"),
                Arguments.of(" ", "none")
        );
    }

    @Test
    public void testStopAll() {

        instance = ProgramManager.createFrom(new TestFactory());
        Report auditReport = new Report(AuditReportFields.class);
        instance.setAuditReport(auditReport);

        instance.startAllProcesses();
        instance.stopAllProcesses();

        verify(runOnStart).stopAllThreads();
        verify(runOnStart).stopAllThreads();

    }

}
