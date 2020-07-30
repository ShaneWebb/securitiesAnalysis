package javautilwrappers;

import java.util.ArrayList;
import java.util.Iterator;

//Thin wrapper around ArrayList. 
public class BasicArrayList<T> implements Iterable<T> {
    private ArrayList<T> internalArrayList;
    
    public BasicArrayList() {
        internalArrayList = new ArrayList<>();
    }
    
    public boolean add(T item) {
        return internalArrayList.add(item);
    }
    
    public boolean remove(T item) {
        return internalArrayList.remove(item);
    }
    
    public T get(int index) {
        return internalArrayList.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return internalArrayList.iterator();
    }
    
}
