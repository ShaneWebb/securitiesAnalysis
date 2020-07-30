package process;

import datatypes.Report;
import datatypes.EnvironmentVariables;
import main.Supplier;

public class ProgramManager {

    public static ProgramManager createFrom(DefaultFactory defaultFactory) {
        return defaultFactory.get();
    }
    
    private final EnvironmentVariables environmentVariables;

    public static class DefaultFactory implements Supplier<ProgramManager> {

        public DefaultFactory() {
        }

        @Override
        public ProgramManager get() {
            return new ProgramManager(
                    EnvironmentVariables.INSTANCE);
        }
    }

    public ProgramManager(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public void setAuditResult(Report auditReport) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void startAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Report getReports() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void acceptUserInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean getProgramActiveStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void stopAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
