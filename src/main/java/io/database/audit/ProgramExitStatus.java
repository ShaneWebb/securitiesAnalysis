package io.database.audit;

public enum ProgramExitStatus {
    
    CLEAN(AuditDegree.QUICK), UNCLEAN(AuditDegree.THOROUGH);
    
    private final AuditDegree followupAudit;
    
    ProgramExitStatus(AuditDegree auditDegree) {
        this.followupAudit = auditDegree;
    }

    public AuditDegree getFollowupAudit() {
        return followupAudit;
    }

}
