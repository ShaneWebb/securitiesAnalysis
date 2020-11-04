package process;

import io.console.SupportedArgs;
import io.local.DatabaseReader;
import io.local.ExternalDataReader;
import io.local.FileReader;
import javautilwrappers.MapWrapper;

class RealExternalDataReaderFactory implements ExternalDataReaderFactory {
    
    @Override
    public ExternalDataReader createFrom(MapWrapper<SupportedArgs, Object> parsedArgs) {
        if (parsedArgs.get(SupportedArgs.files) == null) {
            return new DatabaseReader();
        } else {
            return new FileReader();
        }
    }
    
}
