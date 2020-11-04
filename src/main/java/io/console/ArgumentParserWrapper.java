package io.console;

import javautilwrappers.EnumMapWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
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

    public MapWrapper<SupportedArgs, Object> parseArgs(String[] inputCommandParsed)
            throws IllegalArgumentException {
        try {
            Namespace tempNamespace = ((ArgumentParser) internalArgumentContainer).parseArgs(inputCommandParsed);
            EnumMapWrapper<SupportedArgs, Object> enumMap
                    = mapToEnums(new HashMapWrapper<>(tempNamespace.getAttrs()));
            return enumMap;

        } catch (ArgumentParserException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private EnumMapWrapper<SupportedArgs, Object> mapToEnums(
            MapWrapper<String, Object> map)
            throws IllegalArgumentException {

        EnumMapWrapper<SupportedArgs, Object> enumMap
                = new EnumMapWrapper<>(SupportedArgs.class);

        for (MapWrapper.Entry<String, Object> entry : map.entrySet()) {
            enumMap.put(SupportedArgs.valueOf(entry.getKey()), entry.getValue());
        }

        return enumMap;
    }

    public MutuallyExclusiveGroupWrapper addMutuallyExclusiveGroup() {
        MutuallyExclusiveGroup group
                = ((ArgumentParser) this.internalArgumentContainer).addMutuallyExclusiveGroup();
        return new MutuallyExclusiveGroupWrapper(group);
    }

}
