package io.console;

import java.util.logging.Level;
import java.util.logging.Logger;
import javautilwrappers.BasicMap;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

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

    public <T> T get(String[] split) {
        try {
            Namespace result = internalParser.parseArgs(split);
            return result.get("func");
        } 
        catch (ArgumentParserException ex) {
            throw new IllegalArgumentException();
        }
    }

    public BasicMap<String, Object> parseArgs(String[] inputCommandParsed) throws IllegalArgumentException {
        try {
            Namespace tempNamespace = internalParser.parseArgs(inputCommandParsed);
            BasicMap<String, Object> basicMap
                    = new BasicMap<>(tempNamespace.getAttrs());
            return basicMap;
            
        } catch (ArgumentParserException ex) {
            throw new IllegalArgumentException();
        }
    }
    
}
