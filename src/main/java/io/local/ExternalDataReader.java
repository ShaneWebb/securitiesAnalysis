package io.local;

import java.io.IOException;
import javautilwrappers.MapWrapper;
import process.datatypes.ParsedFile;

public interface ExternalDataReader {

    ParsedFile read(MapWrapper<String, Object> parsedArgs) throws IOException;
    
}
