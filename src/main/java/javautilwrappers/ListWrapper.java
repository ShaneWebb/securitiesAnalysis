
package javautilwrappers;

import java.util.List;

public interface ListWrapper<E> extends CollectionWrapper<E> {
    
    E get(int index);
    
    int indexOf(E item) throws ItemNotFoundException;
    
    List<E> unwrap();

}
