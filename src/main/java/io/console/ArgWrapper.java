
package io.console;

import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Argument;

public class ArgWrapper {
    private Argument internalArgument;
    
    public ArgWrapper(Argument internalArgument) {
        this.internalArgument = internalArgument;
    }
    
    public <T extends Object> ArgWrapper type(Class<T> type) {
        internalArgument = internalArgument.<T>type(type);
        return this;
    }
    
    public ArgWrapper nargs(String str) {
        internalArgument = internalArgument.nargs(str);
        return this;
    }
    
    public ArgWrapper nargs(int num) {
        internalArgument = internalArgument.nargs(num);
        return this;
    }
    
    public ArgWrapper actionStoreConst() {
        internalArgument = internalArgument.action(Arguments.storeConst());
        return this;
    }
    
    public <T> ArgWrapper setDefault(T defaultVal) {
        internalArgument = internalArgument.setDefault(defaultVal);
        return this;
    }

    public ArgWrapper help(String str) {
        internalArgument.help(str);
        return this;
    }

    public void actionStoreTrue() {
        internalArgument = internalArgument.action(Arguments.storeTrue());
    }
    
}
