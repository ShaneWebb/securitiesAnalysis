
package io.console;

import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.Subparser;
import process.SupportedProcess;

public class SubparserWrapper {
    Subparser internalSubparser;
    Argument internalArguments;
    
    public SubparserWrapper(Subparser internalSubparser) {
        this.internalSubparser = internalSubparser;
    }
    
    public SubparserWrapper addArgument(String arg) {
        internalArguments = internalSubparser.addArgument(arg);
        return this;
    }
    
    public <T extends Object> SubparserWrapper type(Class<T> type) {
        internalArguments = internalArguments.<T>type(type);
        return this;
    }
    
    public SubparserWrapper nargs(String str) {
        internalArguments = internalArguments.nargs(str);
        return this;
    }

    public SubparserWrapper actionStoreConst() {
        internalArguments = internalArguments.action(Arguments.storeConst());
        return this;
    }

    public <T> SubparserWrapper setDefault(T defaultVal) {
        internalArguments = internalArguments.setDefault(defaultVal);
        return this;
    }

    public SubparserWrapper setDefault(String string, Object ob) {
        internalSubparser.setDefault(string, ob);
        return this;
    }
    
}
