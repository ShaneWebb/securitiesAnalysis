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
    public void setArgs(MapWrapper<String, Object> parsedArgs) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
