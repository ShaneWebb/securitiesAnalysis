package io.local;

import io.console.SupportedArgs;
import java.io.IOException;
import javautilwrappers.MapWrapper;
import io.datatypes.ParsedInfo;

public interface ExternalDataReader {

    ParsedInfo read(MapWrapper<SupportedArgs, Object> parsedArgs) throws IOException;
    
}
