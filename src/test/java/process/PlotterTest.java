
package process;

import datatypes.Report;
import javautilwrappers.BasicHashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

@Disabled
public class PlotterTest {

     public PlotterTest() {
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
    public void testRunsOnStart() {
        System.out.println("runsOnStart");
        Plotter instance = new Plotter();
        boolean expResult = false;
        boolean result = instance.runsOnStart();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetAuditReport() {
        System.out.println("setAuditReport");
        Report auditReport = null;
        Plotter instance = new Plotter();
        instance.setAuditReport(auditReport);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCreateThread() {
        System.out.println("createThread");
        Plotter instance = new Plotter();
        instance.createThread();
        fail("The test case is a prototype.");
    }

    @Test
    public void testStopAllThreads() {
        System.out.println("stopAllThreads");
        Plotter instance = new Plotter();
        instance.stopAllThreads();
        fail("The test case is a prototype.");
    }

    @Test
    public void testExecute() {
        System.out.println("execute");
        Plotter instance = new Plotter();
        instance.execute();
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetArgs() {
        System.out.println("setArgs");
        BasicHashMap<String, Object> parsedArgs = null;
        Plotter instance = new Plotter();
        instance.setArgs(parsedArgs);
        fail("The test case is a prototype.");
    }

}