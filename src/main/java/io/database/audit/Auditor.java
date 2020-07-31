package io.database.audit;

import datatypes.Portfolio;
import datatypes.exceptions.ExternalDataException;
import io.database.manager.ExternalDataManager;
import io.database.manager.ManagerSpecifier;
import io.database.manager.handler.SQLDatabase;
import io.database.manager.handler.SQLQuery;
import datatypes.Report;
import main.Supplier;

//TODO Refactor
public class Auditor {

//<editor-fold defaultstate="collapsed" desc="Constructors/Factories">
    private ExternalDataManager externalDataManager;
    
    public static Auditor createFrom(Supplier<Auditor> factory) {
        return factory.get();
    }
    
    public static class DefaultFactory implements Supplier<Auditor> {
        
        @Override
        public Auditor get() {
            Auditor instance = new Auditor(new ExternalDataManager());
            return instance;
        }
        
    }
    
    public Auditor() {
    }
    
    public Auditor(ExternalDataManager externalDataManager) {
        this.externalDataManager = externalDataManager;
    }
//</editor-fold>

    public Report audit() {
        Report composedReport = new Report(AuditReportFields.class);
        
        ManagerSpecifier exitStatusSpecifier = new ManagerSpecifier("EXIT CODE");
        ManagerSpecifier localPortfolioSpecifier = new ManagerSpecifier("Local Portfolio");
        ManagerSpecifier onlinePortfolioSpecifier = new ManagerSpecifier("Online Portfolio");
        
        ProgramExitStatus exitStatus;
        Portfolio localPortfolio;
        Portfolio onlinePortfolio;
        
        try {
            exitStatus = externalDataManager.<ProgramExitStatus>get(exitStatusSpecifier);
            localPortfolio = externalDataManager.<Portfolio>get(localPortfolioSpecifier);
            onlinePortfolio = externalDataManager.<Portfolio>get(onlinePortfolioSpecifier);
        } catch (ExternalDataException e) {
            composedReport.setValue(AuditReportFields.STATUS, AuditStatus.FAILED);
            return composedReport;
        }
        
        if (exitStatus == ProgramExitStatus.CLEAN) {
            composedReport.setValue(AuditReportFields.DEGREE, AuditDegree.QUICK);
        }
        else {
            composedReport.setValue(AuditReportFields.DEGREE, AuditDegree.THOROUGH);
        }
        
        if(localPortfolio == onlinePortfolio) {
            composedReport.setValue(AuditReportFields.STATUS, AuditStatus.CONSISTENT);
        }
        else {
            composedReport.setValue(AuditReportFields.STATUS, AuditStatus.INCONSISTENT);
            Report inconsistencies = Portfolio.differences(localPortfolio, onlinePortfolio);
            composedReport.setValue(AuditReportFields.INCONSISTENCIES, inconsistencies);
        }
        
        return composedReport;
    }

}
