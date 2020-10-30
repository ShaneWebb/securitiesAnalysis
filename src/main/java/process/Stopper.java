package process;

import javautilwrappers.MapWrapper;

public class Stopper implements SupportedProcess {

    public Stopper() {
    }

    @Override
    public void execute(MapWrapper<String, Object> parsedArgs) {
        ProgramManager.setProgramActiveStatus(false);
    }

}
