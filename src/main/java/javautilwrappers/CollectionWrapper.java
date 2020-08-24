
package javautilwrappers;

public interface CollectionWrapper<E> extends Iterable<E> {
    
    boolean add(E item);
    
    boolean remove(E item);

}
