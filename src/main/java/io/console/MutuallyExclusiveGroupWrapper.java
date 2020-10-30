package io.console;

import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;

public class MutuallyExclusiveGroupWrapper extends ArgumentContainerWrapper{

    public MutuallyExclusiveGroupWrapper(MutuallyExclusiveGroup group) {
        this.internalArgumentContainer = group;
    }
    
}
