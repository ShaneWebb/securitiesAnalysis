package io.console;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;
import process.SupportedProcess;

public class ArgumentParserWrapper {

    ArgumentParser internalParser;
    Subparsers internalSubParsers;
    
    public ArgumentParserWrapper(String programName, String helpText) {
        internalParser = ArgumentParsers.newFor(programName).build();
        internalSubParsers = internalParser.addSubparsers().help(helpText);
    }
    
    public SubparserWrapper addParser(String commandName, String helpText) {
        Subparser internalSubparser = internalSubParsers.addParser(commandName).help(helpText);
        return new SubparserWrapper(internalSubparser);
    }

    public SupportedProcess getPreparedProcess(String[] split) {
        try {
            Namespace result = internalParser.parseArgs(split);
            return result.get("func");
        } 
        catch (ArgumentParserException ex) {
            throw new IllegalArgumentException();
        }
    }
    
}
