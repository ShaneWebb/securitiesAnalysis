package process;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;

public class Stopper implements SupportedProcess {

    public Stopper() {
    }

    @Override
    public void execute(MapWrapper<SupportedArgs, Object> parsedArgs) {
        ProgramManager.setProgramActiveStatus(false);
    }

}
