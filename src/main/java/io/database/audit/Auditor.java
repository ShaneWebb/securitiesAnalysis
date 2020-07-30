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
        Report composedReport = new Report("Audit Degree", "Status", "Inconsistencies");
        
        ManagerSpecifier exitStatusSpecifier = new ManagerSpecifier("EXIT CODE");
        ManagerSpecifier localPortfolioSpecifier = new ManagerSpecifier("Local Portfolio");
        ManagerSpecifier onlinePortfolioSpecifier = new ManagerSpecifier("Online Portfolio");
        
        ProgramExitStatus exitStatus = null;
        Portfolio localPortfolio = null;
        Portfolio onlinePortfolio = null;
        
        try {
            exitStatus = externalDataManager.<ProgramExitStatus>get(exitStatusSpecifier);
            localPortfolio = externalDataManager.<Portfolio>get(localPortfolioSpecifier);
            onlinePortfolio = externalDataManager.<Portfolio>get(onlinePortfolioSpecifier);
        } catch (ExternalDataException e) {
            composedReport.setValue("Status", "Unable to audit!");
            return composedReport;
        }
        
        if (exitStatus == ProgramExitStatus.CLEAN) {
            composedReport.setValue("Audit Degree", AuditDegree.QUICK);
        }
        else {
            composedReport.setValue("Audit Degree", AuditDegree.THOROUGH);
        }
        
        if(localPortfolio == onlinePortfolio) {
            composedReport.setValue("Status", "Consistency Check Passed!");
        }
        else {
            composedReport.setValue("Status", "Consistency Check Failed!");
            Report inconsistencies = Portfolio.differences(localPortfolio, onlinePortfolio);
            composedReport.setValue("Inconsistencies", inconsistencies);
        }
        
        return composedReport;
    }

}
