package io.database.audit;

import datatypes.Portfolio;
import datatypes.Report;
import datatypes.exceptions.ExternalDataException;
import io.database.manager.ExternalDataManager;
import io.database.manager.ManagerSpecifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

//TODO refactor for brevity and to reduce repetition. 
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AuditorTest {

    private AutoCloseable closeable;
    private Auditor instance;

    @Mock
    private ExternalDataManager mockExternalDataManager;

    public AuditorTest() {
    }

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testAuditReportFields() {
        instance = new Auditor(mockExternalDataManager);

        Report auditReport = instance.audit();

        Enum[] supportedFieldsActual = auditReport.getFields();
        Enum[] supportedFieldsExpected = AuditReportFields.values();

        assertArrayEquals(supportedFieldsExpected,
                supportedFieldsActual,
                "The report must contain the listed fields.");
    }

    //TODO refactor to use parametrized test.
    @Test
    public void testAuditDegreeCleanExit() throws Exception {
        ManagerSpecifier exitCode = new ManagerSpecifier("EXIT CODE");
        when(mockExternalDataManager.<ProgramExitStatus>get(exitCode)).
                thenReturn(ProgramExitStatus.CLEAN);

        instance = new Auditor(mockExternalDataManager);
        Report auditReport = instance.audit();
        AuditDegree auditDegree = auditReport.<AuditDegree>getValueOf(AuditReportFields.DEGREE);
        assertEquals(AuditDegree.QUICK, auditDegree);
    }

    @Test
    public void testAuditDegreeDirtyExit() throws Exception {
        ManagerSpecifier exitCode = new ManagerSpecifier("EXIT CODE");
        when(mockExternalDataManager.<ProgramExitStatus>get(exitCode)).
                thenReturn(ProgramExitStatus.UNCLEAN);

        instance = new Auditor(mockExternalDataManager);
        Report auditReport = instance.audit();
        AuditDegree auditDegree = auditReport.<AuditDegree>getValueOf(AuditReportFields.DEGREE);
        assertEquals(AuditDegree.THOROUGH, auditDegree);
    }

    @Test
    public void testStatusReportConsistent() throws Exception {
        ManagerSpecifier exitStatusSpecifier = new ManagerSpecifier("EXIT CODE");
        ManagerSpecifier localPortfolioSpecifier = new ManagerSpecifier("Local Portfolio");
        ManagerSpecifier onlinePortfolioSpecifier = new ManagerSpecifier("Online Portfolio");

        Portfolio portfolio = new Portfolio();

        when(mockExternalDataManager.<ProgramExitStatus>get(exitStatusSpecifier)).
                thenReturn(ProgramExitStatus.CLEAN);
        when(mockExternalDataManager.<Portfolio>get(localPortfolioSpecifier)).
                thenReturn(portfolio);
        when(mockExternalDataManager.<Portfolio>get(onlinePortfolioSpecifier)).
                thenReturn(portfolio);

        instance = new Auditor(mockExternalDataManager);
        Report auditReport = instance.audit();
        String status = auditReport.<String>getValueOf(AuditReportFields.STATUS);
        assertEquals("Consistency Check Passed!", status);
    }

    @Test
    public void testStatusReportInconsistent() throws Exception {

        ManagerSpecifier exitStatusSpecifier = new ManagerSpecifier("EXIT CODE");
        ManagerSpecifier localPortfolioSpecifier = new ManagerSpecifier("Local Portfolio");
        ManagerSpecifier onlinePortfolioSpecifier = new ManagerSpecifier("Online Portfolio");

        Portfolio portfolioA = new Portfolio();
        Portfolio portFolioB = new Portfolio();

        when(mockExternalDataManager.<ProgramExitStatus>get(exitStatusSpecifier)).
                thenReturn(ProgramExitStatus.CLEAN);
        when(mockExternalDataManager.<Portfolio>get(localPortfolioSpecifier)).
                thenReturn(portfolioA);
        when(mockExternalDataManager.<Portfolio>get(onlinePortfolioSpecifier)).
                thenReturn(portFolioB);

        instance = new Auditor(mockExternalDataManager);
        Report auditReport = instance.audit();
        String status = auditReport.<String>getValueOf(AuditReportFields.STATUS);
        assertEquals("Consistency Check Failed!", status);
    }

    @Test
    public void testInconsistencyReportReturn() throws Exception {
        
        ManagerSpecifier exitStatusSpecifier = new ManagerSpecifier("EXIT CODE");
        ManagerSpecifier localPortfolioSpecifier = new ManagerSpecifier("Local Portfolio");
        ManagerSpecifier onlinePortfolioSpecifier = new ManagerSpecifier("Online Portfolio");

        Portfolio portfolioA = new Portfolio();
        Portfolio portFolioB = new Portfolio();

        when(mockExternalDataManager.<ProgramExitStatus>get(exitStatusSpecifier)).
                thenReturn(ProgramExitStatus.CLEAN);
        when(mockExternalDataManager.<Portfolio>get(localPortfolioSpecifier)).
                thenReturn(portfolioA);
        when(mockExternalDataManager.<Portfolio>get(onlinePortfolioSpecifier)).
                thenReturn(portFolioB);

        instance = new Auditor(mockExternalDataManager);
        Report auditReport = instance.audit();
        Report inconsistencies = auditReport.<Report>getValueOf(AuditReportFields.INCONSISTENCIES);
        assertNotNull(inconsistencies);
        
    }
    
    @Test
    public void testAuditFailure() throws Exception {
        ManagerSpecifier exitStatusSpecifier = new ManagerSpecifier("EXIT CODE");

        when(mockExternalDataManager.<ProgramExitStatus>get(exitStatusSpecifier)).
                thenThrow(new ExternalDataException("Failed to access!"));
        
        instance = new Auditor(mockExternalDataManager);
        Report auditReport = instance.audit();
        String auditDegree = auditReport.<String>getValueOf(AuditReportFields.DEGREE);
        String status = auditReport.<String>getValueOf(AuditReportFields.STATUS);
        Report inconsistencies = auditReport.<Report>getValueOf(AuditReportFields.INCONSISTENCIES);
        
        assertNull(inconsistencies);
        assertNull(auditDegree);
        assertEquals("Unable to audit!", status);
    }

}
