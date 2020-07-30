package datatypes.credential;

import javautilwrappers.BasicMap;

public interface CredentialReader {
    public BasicMap<Integer, String> readIntoMap();
}
