package datatypes.printlayout;

import java.util.ArrayList;

public class TwoDArrayList<T> extends ArrayList<ArrayList<T>>{
    ArrayList<ArrayList<T>> internalArrayList;
    
    public TwoDArrayList() {
        internalArrayList = new ArrayList<>();
    }
    
    public TwoDArrayList(TwoDArrayList<T> twoDArrayList){
        this.internalArrayList = new ArrayList<>();
        
        for(ArrayList<T> row:twoDArrayList) {
            ArrayList<T> tempRow = new ArrayList<>();
            for(T item:row) {
                tempRow.add(item);
            }
            this.internalArrayList.add(tempRow);
        }
    }

    public ArrayList<ArrayList<T>> getInternalArrayList() {
        return internalArrayList;
    }

    public void addRow(Layout generateLayout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
