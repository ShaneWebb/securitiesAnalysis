package io.console;

import net.sourceforge.argparse4j.inf.Subparser;

public class SubparserWrapper extends ArgumentParserWrapper {
    
    public SubparserWrapper(Subparser parser) {
        super("");
        this.internalArgumentContainer = parser;
        this.internalSubparsers = parser.addSubparsers();
    }
    
}
