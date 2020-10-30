package io.console;

import javautilwrappers.HashMapWrapper;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

//ArgumentParser analog
public class ArgumentParserWrapper extends ArgumentContainerWrapper {


    private ArgumentParserWrapper(ArgumentParser internalParser) {
        this.internalArgumentContainer = internalParser;
        internalSubparsers = internalParser.addSubparsers();
    }
    
    public ArgumentParserWrapper(String programName) {
        internalArgumentContainer = ArgumentParsers.newFor(programName).build();
        internalSubparsers = ((ArgumentParser) internalArgumentContainer).addSubparsers();
    }

    public ArgumentParserWrapper addSubparserHelp(String helpText) {
        ((ArgumentParser) internalArgumentContainer).addSubparsers().help(helpText);
        return this;
    }

    public SubparserWrapper addSubparser(String commandName) {
        Subparser subparser = internalSubparsers.addParser(commandName);
        SubparserWrapper parser = new SubparserWrapper(subparser);
        return parser;
    }

    public ArgumentParserWrapper setDefault(String string, Object ob) {
        ((ArgumentParser) internalArgumentContainer).setDefault(string, ob);
        return this;
    }

    public HashMapWrapper<String, Object> parseArgs(String[] inputCommandParsed) throws IllegalArgumentException {
        try {
            Namespace tempNamespace = ((ArgumentParser) internalArgumentContainer).parseArgs(inputCommandParsed);
            HashMapWrapper<String, Object> basicMap
                    = new HashMapWrapper<>(tempNamespace.getAttrs());
            return basicMap;

        } catch (ArgumentParserException ex) {
            String msg = ex.getMessage();
            throw new IllegalArgumentException(msg);
        }
    }

    public MutuallyExclusiveGroupWrapper addMutuallyExclusiveGroup() {
        MutuallyExclusiveGroup group =
                ((ArgumentParser) this.internalArgumentContainer).addMutuallyExclusiveGroup();
        return new MutuallyExclusiveGroupWrapper(group);
    }

}
