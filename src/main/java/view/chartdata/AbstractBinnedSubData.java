
package view.chartdata;

import javautilwrappers.ArrayListWrapper;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;

public class AbstractBinnedSubData implements ChartSubDataWrapper{
    
    protected final ListWrapper<MapWrapper<String, Object>> internalSubData;
    protected final String fileName;
    
    public AbstractBinnedSubData(String fileName) {
        this.internalSubData = new ArrayListWrapper<>();
        this.fileName = fileName;
    }
    
    @Override
    public final void add(MapWrapper data) {
        data.put("col", fileName);
        this.internalSubData.add(data);
    }

    @Override
    public final Object unwrap() {
        return this.internalSubData;
    }

}
