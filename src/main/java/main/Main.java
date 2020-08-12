package main;

import process.ProgramManager;
import datatypes.Report;
import io.database.audit.Auditor;
import view.*;
import datatypes.*;
import java.util.Scanner;

public class Main {

    private final Auditor auditor;
    private final ProgramManager programManager;
    private final PrettyPrint prettyPrint;
    private final Scanner scanner;

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
                    new PrettyPrint.Builder().build(),
                    new Scanner(System.in));

            return instance;
        }

    }

    public Main(
            Auditor auditor,
            ProgramManager programManager,
            PrettyPrint prettyPrint,
            Scanner scanner) {

        this.auditor = auditor;
        this.programManager = programManager;
        this.prettyPrint = prettyPrint;
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        Main instance = Main.createFrom(new Main.DefaultFactory());
        instance.run();
    }

    public final void run() {
        Report auditReport = auditor.audit();
        programManager.setAuditReport(auditReport);
        programManager.startAllProcesses();

        Boolean programIsActive = true;
        while (programIsActive) {
            Report programReport = programManager.getFullReport();
            prettyPrint.prettyPrinter(programReport);
            if (scanner.hasNext()) {
                programManager.runUserInputCommand(scanner.nextLine());
            }
            else {
                programManager.runUserInputCommand(null);
            }
            programIsActive = ProgramManager.getProgramActiveStatus();
        }
        programManager.stopAllProcesses();
    }

}
