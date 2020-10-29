package main;

import java.util.Scanner;
import process.ProgramManager;

public class Main {

    private final ProgramManager programManager;
    private final Scanner scanner;

    public static Main createFrom(Supplier<Main> factory) {
        return factory.get();
    }

    public static class DefaultFactory implements Supplier<Main> {

        @Override
        public Main get() {
            Main instance = new Main(
                    new ProgramManager(),
                    new Scanner(System.in));

            return instance;
        }

    }

    public Main(
            ProgramManager programManager,
            Scanner scanner) {

        this.programManager = programManager;
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        Main instance = Main.createFrom(new Main.DefaultFactory());
        instance.run();
    }

    public final void run() {

        do {
            if (scanner.hasNext()) {
                programManager.runUserInputCommand(scanner.nextLine());
            } else {
                programManager.runUserInputCommand(null);
            }
        } while (ProgramManager.getProgramActiveStatus());

    }

}
