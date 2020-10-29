package process;

import javautilwrappers.MapWrapper;

//TODO: Refactor into multiple interfaces, since not all processes need
//all of these features.
public interface SupportedProcess {

    public void execute() throws Exception;

    public void setArgs(MapWrapper<String, Object> parsedArgs) throws IllegalArgumentException;
    
}
