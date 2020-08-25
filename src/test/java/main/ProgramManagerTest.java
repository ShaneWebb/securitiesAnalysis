package main;

import io.console.ArgParseWrapper;
import process.SupportedProcess;
import datatypes.EnvironmentVariables;
import datatypes.Report;
import io.database.audit.AuditReportFields;
import java.util.stream.Stream;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import process.Visualizations;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ProgramManagerTest {

    private AutoCloseable closeable;
    private ProgramManager instance;

    @Mock
    private SupportedProcess runOnStart, doNotRunOnStart, processOne, processTwo;
    
    @Mock
    private SupportedProcess placeholder;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    private class TestFactory implements Supplier<ProgramManager> {

        private final HashMapWrapper<String, SupportedProcess> supportedProcesses;
        private final ArgParseWrapper argParser;

        TestFactory() {
            supportedProcesses = new HashMapWrapper<>();
            supportedProcesses.put("runonstart", runOnStart);
            supportedProcesses.put("doNotRunOnStart", doNotRunOnStart);
            argParser = new ArgParseWrapper("Test");
        }

        @Override
        public ProgramManager get() {
            return new ProgramManager(
                    EnvironmentVariables.INSTANCE,
                    supportedProcesses,
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
        ProgramManager.setProgramActiveStatus(false);
        programIsActive = ProgramManager.getProgramActiveStatus();
        assertFalse(programIsActive);

        ProgramManager.setProgramActiveStatus(true);
        programIsActive = ProgramManager.getProgramActiveStatus();
        assertTrue(programIsActive);

    }

    @ParameterizedTest
    @MethodSource("provideCommandAndProcesses")
    public void testRunUserInputCommand(String commandArg, String testMode) throws Exception {

        class LocalTestFactory implements Supplier<ProgramManager> {

            private final MapWrapper<String, SupportedProcess> supportedProcesses;
            private final ArgParseWrapper localArgParser;

            LocalTestFactory() {
                supportedProcesses = new HashMapWrapper<>();
                supportedProcesses.put("placeholder1", processOne);
                supportedProcesses.put("placeholder2", processTwo);

                localArgParser = new ArgParseWrapper("Test");
                localArgParser.addSubparserHelp("Test sub command help");

                ArgParseWrapper commandOne = localArgParser.addParser("CommandOne", "Command One Help");
                commandOne.setDefault("func", processOne);
                ArgParseWrapper commandTwo = localArgParser.addParser("CommandTwo", "Command One Help");
                commandTwo.setDefault("func", processTwo);
            }

            @Override
            public ProgramManager get() {
                return new ProgramManager(
                        EnvironmentVariables.INSTANCE,
                        supportedProcesses,
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
                Arguments.of(" ", "none"),
                Arguments.of(null, "none")
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommandAndMap")
    public void testInbuiltCommands(String command, HashMapWrapper<String, Object> map) {
        class LocalTestFactory implements Supplier<ProgramManager> {

            private final HashMapWrapper<String, SupportedProcess> supportedProcesses;
            private final ArgParseWrapper localArgParser;

            LocalTestFactory() {
                // Rest is defined in Program Manager.
                localArgParser = new ArgParseWrapper("Erasmus");
                
                // Keys must match the program manager object.
                supportedProcesses = new HashMapWrapper<>();
                supportedProcesses.put("plotter", placeholder);
                supportedProcesses.put("stopper", placeholder);
            }

            @Override
            public ProgramManager get() {
                return new ProgramManager(
                        EnvironmentVariables.INSTANCE,
                        supportedProcesses,
                        localArgParser);
            }
        }
        
        instance = ProgramManager.createFrom(new LocalTestFactory());
        instance.runUserInputCommand(command); map.put("func", placeholder);
        verify(placeholder).setArgs(map);
        
    }
    
    public static Stream<Arguments> provideCommandAndMap() {
        MapWrapper<String, Object> map1 = new HashMapWrapper<>();
        map1.put("files", "A.csv,B.csv");
        map1.put("header", "volume");
        map1.put("startDate", "8/21/1981");
        map1.put("endDate", "1/1/2020");
        map1.put("xAxis", "Date");
        map1.put("lineartrend", true);
        map1.put("type", Visualizations.BASIC);
        
        MapWrapper<String, Object> map2 = new HashMapWrapper<>();
        map2.put("files", "C.csv,D.csv");
        map2.put("header", "volume");
        map2.put("startDate", "8/21/1981");
        map2.put("endDate", "1/1/2020");
        map2.put("xAxis", "Time");
        map2.put("lineartrend", false);
        map2.put("type", Visualizations.BASIC);
        
        MapWrapper<String, Object> map3 = new HashMapWrapper<>(map1);
        map3.put("type", Visualizations.MOVING_AVERAGE);
        map3.put("period", 1);
        map3.put("initToIgnore", 1);
        
        MapWrapper<String, Object> map4 = new HashMapWrapper<>(map2);
        map4.put("type", Visualizations.MOVING_AVERAGE);
        map4.put("period", 20);
        map4.put("initToIgnore", 20);
        
        return Stream.of(
                Arguments.of("Visualize A.csv,B.csv volume 8/21/1981 1/1/2020 --lineartrend Basic", map1),
                Arguments.of("Visualize C.csv,D.csv volume 8/21/1981 1/1/2020 --xAxis Time Basic", map2),
                Arguments.of("Visualize A.csv,B.csv volume 8/21/1981 1/1/2020 --lineartrend MovingAvg", map3),
                Arguments.of("Visualize C.csv,D.csv volume 8/21/1981 1/1/2020 --xAxis Time MovingAvg --period 20 --initToIgnore 20", map4)
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
