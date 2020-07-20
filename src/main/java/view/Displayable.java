package view;

import datatypes.printlayout.Layout;
import datatypes.*;

//For use with PrettyPrint. Anything which implements Displayable may be
//printed by PrettyPrint. 
public interface Displayable {
    
    public Layout generateLayout();
        
}
