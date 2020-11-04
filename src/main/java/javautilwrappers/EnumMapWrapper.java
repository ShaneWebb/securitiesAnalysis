package javautilwrappers;

import java.util.EnumMap;

public class EnumMapWrapper<K extends Enum<K>, V> extends AbstractMapWrapper<K, V> {

    public EnumMapWrapper(Class<K> keyType) {
        internalMap = new EnumMap(keyType);
    }
    
    public EnumMapWrapper(EnumMap<K, ? extends V> map) {
        internalMap = new EnumMap(map);
    }
    
    public EnumMapWrapper(MapWrapper<K, ? extends V> map) {
        internalMap = new EnumMap(map.unwrap());
    }
    
}
