package process;

import io.local.ExternalDataReader;
import javautilwrappers.MapWrapper;

public interface ExternalDataReaderFactory {

    ExternalDataReader createFrom(MapWrapper<String, Object> parsedArgs);
    
}
