package main;

import process.ProgramManager;
import datatypes.Report;
import io.database.audit.Auditor;
import view.*;
import datatypes.*;

public class Main {

    private boolean programIsActive;

    private Auditor auditor;
    private ProgramManager programManager;
    private PrettyPrint prettyPrint;

    public static Main createFrom(Supplier<Main> factory) {
        return factory.get();
    }

    public static class DefaultFactory implements Supplier<Main> {

        @Override
        public Main get() {
            EnvironmentVariables.INSTANCE.loadFromFile("environmentvariables.txt");
            Main instance = new Main(
                    Auditor.createFrom(new Auditor.DefaultFactory()),
                    ProgramManager.createFrom(new ProgramManager.DefaultFactory()),
                    new PrettyPrint.Builder().build());

            return instance;
        }

    }

    public Main(
            Auditor auditor,
            ProgramManager programManager,
            PrettyPrint prettyPrint) {

        this.auditor = auditor;
        this.programManager = programManager;
        this.prettyPrint = prettyPrint;
    }

    public static void main(String[] args) {

        Main instance = Main.createFrom(new Main.DefaultFactory());
        instance.run();
    }

    public final void run() {

        Report auditReport = auditor.audit();

        programManager.setAuditReport(auditReport);
        programManager.startAllProcesses();

        programIsActive = true;
        while (programIsActive) {
            Report programReport = programManager.getReports();
            prettyPrint.prettyPrinter(programReport);
            programManager.acceptUserInput();
            programIsActive = programManager.getProgramActiveStatus();
        }

        programManager.stopAllProcesses();
    }

}
