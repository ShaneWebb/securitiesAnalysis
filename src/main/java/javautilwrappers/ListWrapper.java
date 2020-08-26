
package javautilwrappers;

public interface ListWrapper<E> extends CollectionWrapper<E> {
    
    E get(int index);
    
    int indexOf(E item) throws ItemNotFoundException;

}