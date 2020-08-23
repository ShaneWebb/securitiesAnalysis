package javautilwrappers;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

//Thin wrapper around ArrayList. 
public class ArrayListWrapper<T> extends AbstractListWrapper<T>
        implements Iterable<T> {


    public ArrayListWrapper() {
        internalArrayList = new ArrayList<>();
    }
    
    public ArrayListWrapper(List list) {
        internalArrayList = new ArrayList<>(list);
    }

    public boolean add(T item) {
        return internalArrayList.add(item);
    }

    public boolean remove(Object item) {
        return internalArrayList.remove(item);
    }

    public T get(int index) {
        return internalArrayList.get(index);
    }


    @Override
    public Iterator<T> iterator() {
        return internalArrayList.iterator();
    }

//<editor-fold defaultstate="collapsed" desc="Unimplemented behavior">
    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//</editor-fold>

}
