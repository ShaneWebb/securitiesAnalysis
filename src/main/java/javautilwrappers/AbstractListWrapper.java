
package javautilwrappers;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class AbstractListWrapper<E> implements ListWrapper<E> {

    protected List<E> internalList;

    protected AbstractListWrapper() {
    }
    
    @Override
    public int size() {
        return this.internalList.size();
    }
    
    @Override
    public List<E> unwrap() {
        return this.internalList;
    }
    
    @Override
    public boolean add(E item) {
        return internalList.add(item);
    }

    @Override
    public boolean remove(E item) {
        return internalList.remove(item);
    }

    @Override
    public E get(int index) {
        return internalList.get(index);
    }
    
    @Override
    public int indexOf(E item) throws ItemNotFoundException {
        int index = internalList.indexOf(item);
        if (index == -1) {
            throw new ItemNotFoundException("Item not found in list: " + item);
        }
        return index;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.internalList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractListWrapper<?> other = (AbstractListWrapper<?>) obj;
        if (!Objects.equals(this.internalList, other.internalList)) {
            return false;
        }
        return true;
    }
    
    @Override
    public Iterator<E> iterator() {
        return internalList.iterator();
    }

}
