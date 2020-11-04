package process;

import io.local.DatabaseReader;
import io.local.ExternalDataReader;
import io.local.FileReader;
import javautilwrappers.MapWrapper;

class RealExternalDataReaderFactory implements ExternalDataReaderFactory {
    
    @Override
    public ExternalDataReader createFrom(MapWrapper<String, Object> parsedArgs) {
        if (parsedArgs.get("files") == null) {
            return new DatabaseReader();
        } else {
            return new FileReader();
        }
    }
    
}
