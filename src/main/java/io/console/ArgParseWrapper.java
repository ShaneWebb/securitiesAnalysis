package io.console;

import javautilwrappers.BasicHashMap;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

public class ArgParseWrapper {

    private final ArgumentParser internalParser;
    private final Subparsers internalSubparsers;

    private ArgParseWrapper(ArgumentParser internalParser) {
        this.internalParser = internalParser;
        internalSubparsers = internalParser.addSubparsers();
    }
    
    public ArgParseWrapper(String programName) {
        internalParser = ArgumentParsers.newFor(programName).build();
        internalSubparsers = internalParser.addSubparsers();
    }

    public ArgParseWrapper addSubparserHelp(String helpText) {
        internalParser.addSubparsers().help(helpText);
        return this;
    }

    public ArgParseWrapper addParser(String commandName, String helpText) {
        Subparser subparser = internalSubparsers.addParser(commandName).help(helpText);
        ArgParseWrapper parser = new ArgParseWrapper(subparser);
        return parser;
    }

    public ArgWrapper addArgument(String arg) {
        Argument unwrappedArg = internalParser.addArgument(arg);
        ArgWrapper wrappedArg = new ArgWrapper(unwrappedArg);
        return wrappedArg;
    }
    
    
    public ArgParseWrapper setDefault(String string, Object ob) {
        internalParser.setDefault(string, ob);
        return this;
    }

    public BasicHashMap<String, Object> parseArgs(String[] inputCommandParsed) throws IllegalArgumentException {
        try {
            Namespace tempNamespace = internalParser.parseArgs(inputCommandParsed);
            BasicHashMap<String, Object> basicMap
                    = new BasicHashMap<>(tempNamespace.getAttrs());
            return basicMap;

        } catch (ArgumentParserException ex) {
            throw new IllegalArgumentException();
        }
    }

}
