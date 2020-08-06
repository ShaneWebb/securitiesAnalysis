package javautilwrappers;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//Thin wrapper around ArrayList. 
public class BasicArrayList<T> extends AbstractList<T>
        implements Collection<T> {

    private final ArrayList<T> internalArrayList;

    public BasicArrayList() {
        internalArrayList = new ArrayList<>();
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
