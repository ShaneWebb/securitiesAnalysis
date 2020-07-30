package main;

import datatypes.EnvironmentVariables;
import datatypes.Report;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@Disabled
public class ProgramManagerTest {

    public ProgramManagerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSetEnvironmentVariables() {
        System.out.println("setEnvironmentVariables");
        EnvironmentVariables environmentVariables = null;
        ProgramManager instance = new ProgramManager();
        instance.setEnvironmentVariables(environmentVariables);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetAuditResult() {
        System.out.println("setAuditResult");
        Report auditReport = null;
        ProgramManager instance = new ProgramManager();
        instance.setAuditResult(auditReport);
        fail("The test case is a prototype.");
    }

    @Test
    public void testStartAll() {
        System.out.println("startAll");
        ProgramManager instance = new ProgramManager();
        instance.startAll();
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetReports() {
        System.out.println("getReports");
        ProgramManager instance = new ProgramManager();
        Report expResult = null;
        Report result = instance.getReports();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAcceptUserInput() {
        System.out.println("acceptUserInput");
        ProgramManager instance = new ProgramManager();
        instance.acceptUserInput();
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProgramActiveStatus() {
        System.out.println("getProgramActiveStatus");
        ProgramManager instance = new ProgramManager();
        boolean expResult = false;
        boolean result = instance.getProgramActiveStatus();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testStopAll() {
        System.out.println("stopAll");
        ProgramManager instance = new ProgramManager();
        instance.stopAll();
        fail("The test case is a prototype.");
    }

}
