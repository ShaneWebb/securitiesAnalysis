
package javautilwrappers;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractListWrapper<T> extends AbstractList<T> {

    protected List<T> internalArrayList;

    protected AbstractListWrapper() {
    }

    @Override
    public int hashCode() {
        return this.internalArrayList.hashCode();
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
        final ArrayListWrapper<?> other = (ArrayListWrapper<?>) obj;
        if (!Objects.equals(this.internalArrayList, other.internalArrayList)) {
            return false;
        }
        return true;
    }

}
