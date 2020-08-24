package javautilwrappers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractMapWrapper<K, V> implements MapWrapper<K, V> {

    protected Map<K, V> internalMap;

    protected AbstractMapWrapper() {
    }

    @Override
    public V put(K key, V value) {
        return internalMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return internalMap.get(key);
    }
    
    @Override
    public V remove(K key) {
        return internalMap.remove(key);
    }

    @Override
    public CollectionWrapper<V> values() {

        Collection<V> localCollection = internalMap.values();

        CollectionWrapper<V> collection = new AbstractCollectionWrapper<V>() {

            @Override
            public Iterator<V> iterator() {
                return localCollection.iterator();
            }

            @Override
            public boolean add(V item) {
                return localCollection.add(item);
            }

            @Override
            public boolean remove(V item) {
                return localCollection.remove(item);
            }

        };

        return collection;
    }
    
    @Override
    public Map<K, V> unwrap() {
        return internalMap;
    }

    //TODO: Holey moley nested class anti-pattern. Refactor.
    @Override
    public SetWrapper<MapWrapper.Entry<K, V>> entrySet() {

        Set<Map.Entry<K, V>> internalSet = internalMap.entrySet();

        SetWrapper<MapWrapper.Entry<K, V>> set = new AbstractSetWrapper<MapWrapper.Entry<K, V>>() {

            @Override
            public boolean add(MapWrapper.Entry<K, V> item) {
                return internalSet.add(item.unwrap());
            }

            @Override
            public boolean remove(MapWrapper.Entry<K, V> item) {
                return internalSet.remove(item.unwrap());
            }

            @Override
            public Iterator<MapWrapper.Entry<K, V>> iterator() {

                Iterator<Map.Entry<K, V>> internalIterator = internalSet.iterator();
                Iterator<MapWrapper.Entry<K, V>> iterator = new Iterator<MapWrapper.Entry<K, V>>() {

                    @Override
                    public boolean hasNext() {
                        return internalIterator.hasNext();
                    }

                    @Override
                    public MapWrapper.Entry<K, V> next() {
                        Map.Entry<K, V> internalEntry = internalIterator.next();
                        MapWrapper.Entry<K, V> entry = new MapWrapper.Entry<K, V>() {
                            @Override
                            public K getKey() {
                                return internalEntry.getKey();
                            }

                            @Override
                            public V getValue() {
                                return internalEntry.getValue();
                            }

                            @Override
                            public Map.Entry<K, V> unwrap() {
                                return internalEntry;
                            }
                        };
                        return entry;
                    }
                };
                return iterator;
            }
        };
        return set;
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }

    @Override
    public String toString() {
        return internalMap.toString();
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
