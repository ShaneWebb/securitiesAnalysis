package io.console;

import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentContainer;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 *
 * @author shane
 */
public abstract class ArgumentContainerWrapper {
    
    protected ArgumentContainer internalArgumentContainer;
    protected Subparsers internalSubparsers;

    public ArgumentContainerWrapper() {
    }

    public ArgumentWrapper addArgument(String arg) {
        Argument unwrappedArg = internalArgumentContainer.addArgument(arg);
        ArgumentWrapper wrappedArg = new ArgumentWrapper(unwrappedArg);
        return wrappedArg;
    }
    
}
