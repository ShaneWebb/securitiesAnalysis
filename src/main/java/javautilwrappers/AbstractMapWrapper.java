package javautilwrappers;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractMapWrapper<K, V> implements MapWrapper<K, V> {

    protected Map<K, V> internalMap;

    protected AbstractMapWrapper() {
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
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
        final AbstractMapWrapper<?, ?> other = (AbstractMapWrapper<?, ?>) obj;
        if (!Objects.equals(this.internalMap, other.internalMap)) {
            return false;
        }
        return true;
    }

}
