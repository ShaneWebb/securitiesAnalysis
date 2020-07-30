package javautilwrappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

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
    public int hashCode() {
        return this.internalArrayList.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.internalArrayList.equals(obj);
    }
    
    

    @Override
    public Iterator<T> iterator() {
        return internalArrayList.iterator();
    }
    
}
