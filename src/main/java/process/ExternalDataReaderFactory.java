package process;

import io.console.SupportedArgs;
import io.local.ExternalDataReader;
import javautilwrappers.MapWrapper;

public interface ExternalDataReaderFactory {

    ExternalDataReader createFrom(MapWrapper<SupportedArgs, Object> parsedArgs);
    
}
