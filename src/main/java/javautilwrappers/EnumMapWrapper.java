package javautilwrappers;

import java.util.EnumMap;

public class EnumMapWrapper<K extends Enum<K>, V> extends AbstractMapWrapper<K, V> {

    public EnumMapWrapper(Class<K> keyType) {
        internalMap = new EnumMap(keyType);
    }
    
}
