package process;

import datatypes.Report;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SupportedProcessTest {

    public SupportedProcessTest() {
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

    }

    @Test
    public void testSetAuditReport() {

    }

    @Test
    public void testCreateThread() {

    }

    @Test
    public void testStopAllThreads() {

    }

    public class SupportedProcessImpl implements SupportedProcess {

        public boolean runsOnStart() {
            return false;
        }

        public void setAuditReport(Report auditReport) {
        }

        public void createThread() {
        }

        public void stopAllThreads() {
        }
    }

}
