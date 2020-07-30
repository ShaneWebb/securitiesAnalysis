package main;

import process.ProgramManager;
import datatypes.Report;
import io.database.audit.Auditor;
import datatypes.printlayout.Layout;
import view.*;
import datatypes.*;
import datatypes.printlayout.TwoDArrayList;

public class Main {

    private boolean programIsActive;
    private Report programReport;

    private EnvironmentVariables environmentVariables;
    private Auditor auditor;
    private ProgramManager programManager;
    private PrettyPrint prettyPrint;

    public static Main createFrom(Supplier<Main> factory) {
        return factory.get();
    }

    public static class DefaultFactory implements Supplier<Main> {

        @Override
        public Main get() {
            Main instance = new Main(
                    Auditor.createFrom(new Auditor.DefaultFactory()),
                    ProgramManager.createFrom(new ProgramManager.DefaultFactory()),
                    new PrettyPrint.Builder().build(),
                    EnvironmentVariables.INSTANCE);

            return instance;
        }

    }

    public Main(
            Auditor auditor,
            ProgramManager programManager,
            PrettyPrint prettyPrint,
            EnvironmentVariables environmentVariables) {

        this.auditor = auditor;
        this.programManager = programManager;
        this.prettyPrint = prettyPrint;
        this.environmentVariables = environmentVariables;
    }

    public static void main(String[] args) {

        Main instance = Main.createFrom(new Main.DefaultFactory());
        instance.run();
    }

    public final void run() {

        this.environmentVariables = EnvironmentVariables.INSTANCE;

        Report auditReport = auditor.audit();

        programManager.setAuditResult(auditReport);
        programManager.startRequiredProcesses();

        programIsActive = true;
        while (programIsActive) {
            programReport = programManager.getReports();
            prettyPrint.prettyPrinter(this);
            programManager.acceptUserInput();
            programIsActive = programManager.getProgramActiveStatus();
        }

        programManager.stopAllProcesses();
    }

}
