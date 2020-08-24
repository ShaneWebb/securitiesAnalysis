package javautilwrappers;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListWrapper<E> extends AbstractListWrapper<E> {

    public ArrayListWrapper() {
        internalList = new ArrayList<>();
    }

    public ArrayListWrapper(Collection<? extends E> collection) {
        internalList = new ArrayList<>(collection);
    }

}
