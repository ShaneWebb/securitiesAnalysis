package view;

import javautilwrappers.MapWrapper;

public interface ChartSubDataWrapper<T>{
    
    void add(MapWrapper data);
    
    T unwrap();

}
