
package io.database.manager;

//TODO: Finish me. I provide information to ExternalDataManager to properly

import java.util.Objects;


//perform the desired read and writes.
public class ManagerSpecifier {
    private final String id;
    
    public ManagerSpecifier(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ManagerSpecifier other = (ManagerSpecifier) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
          
}
