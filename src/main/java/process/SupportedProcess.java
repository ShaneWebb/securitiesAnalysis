package process;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;

//TODO: Refactor into multiple interfaces, since not all processes need
//all of these features.
public interface SupportedProcess {

    public void execute(MapWrapper<SupportedArgs, Object> parsedArgs) throws Exception;
    
}
