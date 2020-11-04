package io.local;

import io.console.SupportedArgs;
import java.io.IOException;
import javautilwrappers.MapWrapper;
import process.datatypes.ParsedFile;

public interface ExternalDataReader {

    ParsedFile read(MapWrapper<SupportedArgs, Object> parsedArgs) throws IOException;
    
}
