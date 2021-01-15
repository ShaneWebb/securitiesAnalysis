
package view.chartdata;

import java.util.Iterator;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.CollectionWrapper;
import javautilwrappers.MapWrapper;

public class AbstractBinnedSubData<T> implements ChartSubDataWrapper{
    
    protected final CollectionWrapper<MapWrapper<String, Object>> internalSubData;
    protected final String fileName;

    public String getFileName() {
        return fileName;
    }
    
    public AbstractBinnedSubData(String fileName) {
        this.internalSubData = new ArrayListWrapper<>();
        this.fileName = fileName;
    }
    
    @Override
    public final void add(MapWrapper data) {
        this.internalSubData.add(data);
    }

    @Override
    public final Object unwrap() {
        return this.internalSubData;
    }

    @Override
    public Iterator<T> iterator() {
        return new subdataIterator<>();
    }
    
    public class subdataIterator<T> implements Iterator<T> {

        private final Iterator internalIterator = internalSubData.iterator();
        
        @Override
        public boolean hasNext() {
            return internalIterator.hasNext();
        }

        @Override
        public T next() {
            MapWrapper<String, Object> map =
                    (MapWrapper<String, Object>) internalIterator.next();
            return (T) map.get("value");
        }
        
    }

}
