
package process;

import javautilwrappers.MapWrapper;

public class Stopper implements SupportedProcess {

    public Stopper() {
    }

    @Override
    public void execute() {
        ProgramManager.setProgramActiveStatus(false);
    }
    
    @Override
    public boolean runsOnStart() {
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Unused"> 
    @Override
    public void createThread() {
        
    }
    
    @Override
    public void stopAllThreads() {
        
    }
    
    @Override
    public void setArgs(MapWrapper<String, Object> parsedArgs) {
        
    }
//</editor-fold>

}
