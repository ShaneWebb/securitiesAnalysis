package io.console;

import jdk.internal.HotSpotIntrinsicCandidate;
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

    @HotSpotIntrinsicCandidate
    public ArgumentContainerWrapper() {
    }

    public ArgumentWrapper addArgument(String arg) {
        Argument unwrappedArg = internalArgumentContainer.addArgument(arg);
        ArgumentWrapper wrappedArg = new ArgumentWrapper(unwrappedArg);
        return wrappedArg;
    }
    
}
