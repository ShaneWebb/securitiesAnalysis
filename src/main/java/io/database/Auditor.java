package io.database;

public class Auditor {
    
    private AuditDepth depth;
    
    //
    public void audit(){
        
    }
    
}

enum AuditDepth {
    LOW, MEDIUM, HIGH;
}