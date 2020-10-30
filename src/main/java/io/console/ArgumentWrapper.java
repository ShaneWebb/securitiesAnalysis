
package io.console;

import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Argument;

public class ArgumentWrapper {
    private Argument internalArgument;
    
    public ArgumentWrapper(Argument internalArgument) {
        this.internalArgument = internalArgument;
    }
    
    public <T extends Object> ArgumentWrapper type(Class<T> type) {
        internalArgument = internalArgument.<T>type(type);
        return this;
    }
    
    public ArgumentWrapper nargs(String str) {
        internalArgument = internalArgument.nargs(str);
        return this;
    }
    
    public ArgumentWrapper nargs(int num) {
        internalArgument = internalArgument.nargs(num);
        return this;
    }
    
    public ArgumentWrapper actionStoreConst() {
        internalArgument = internalArgument.action(Arguments.storeConst());
        return this;
    }
    
    public <T> ArgumentWrapper setDefault(T defaultVal) {
        internalArgument = internalArgument.setDefault(defaultVal);
        return this;
    }

    public ArgumentWrapper help(String str) {
        internalArgument.help(str);
        return this;
    }

    public void actionStoreTrue() {
        internalArgument = internalArgument.action(Arguments.storeTrue());
    }
    
}
