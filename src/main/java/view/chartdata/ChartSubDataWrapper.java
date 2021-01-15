package view.chartdata;

import javautilwrappers.MapWrapper;

public interface ChartSubDataWrapper<T> extends Iterable<T> {
    
    void add(MapWrapper data);
    
    @Deprecated
    T unwrap();

}
