package datatypes.printlayout;

import datatypes.printlayout.fields.LayoutField;
import java.util.ArrayList;
import java.util.Iterator;
import main.Helper;

public class Layout implements Iterable<LayoutField> {

    private String label;
    private TwoDArrayList<Layout> innerLayouts;
    private Formatting formatting;
    private Size size;

    private Layout() {}

    public static class Builder {
        private String label;
        private TwoDArrayList innerLayouts;
        private Formatting formatting;

        //Default values if no formatting information is supplied.
        public Builder() {
            label = "";
            formatting = new Formatting.Builder().build();
        }

//<editor-fold defaultstate="collapsed" desc="Setters">
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setInnerLayouts(TwoDArrayList innerLayouts) {
            this.innerLayouts = innerLayouts;
            return this;
        }

        public Builder setFormatting(Formatting formatting) {
            this.formatting = formatting;
            return this;
        }
//</editor-fold>
        
        public Layout build() {
            Layout layout = new Layout();
            layout.innerLayouts = new TwoDArrayList(this.innerLayouts);
            layout.formatting = new Formatting(this.formatting);
            layout.label = Helper.insertLabelNewlines(this.label, this.formatting.getMaxLabelLineWidth());
            layout.computeSize();
            return layout;
        }

    }

    //FIXME: Refactor this awful construct.
    private void computeSize() {
        size = new Size();
        ArrayList<ArrayList<Layout>> extractedInnerLayouts = this.innerLayouts.getInternalArrayList();
        
        int labelNumLines = label.split("\n").length;
        int outlineSize = this.formatting.getOutlineSize();
        int maxLabelLineWidth = this.formatting.getMaxLabelLineWidth();
        int labelMaxLength = labelNumLines > 1 ? maxLabelLineWidth : label.length();

        if (extractedInnerLayouts.isEmpty()) {
            size.setHeight(outlineSize + labelNumLines);
            size.setWidth(outlineSize + labelMaxLength);
        } else {

            //The label height is added to the running total, and the width
            //has the maximum line size as a lower bound.
            int totalRunningHeight = labelNumLines;
            int totalRunningWidth = labelMaxLength;

            for (ArrayList<Layout> row : extractedInnerLayouts) {

                int rowRunningHeight = 0;
                int rowRunningWidth = 0;

                for (Layout layout : row) {
                    rowRunningWidth += layout.size.getWidth();
                    rowRunningHeight
                            = Math.max(layout.size.getHeight(), rowRunningHeight);
                }

                totalRunningHeight += rowRunningHeight;
                totalRunningWidth
                        = Math.max(rowRunningWidth, totalRunningWidth);

            }

            size.setHeight(outlineSize + totalRunningHeight);
            size.setWidth(outlineSize + totalRunningWidth);

        }

    }

    @Override
    public Iterator<LayoutField> iterator() {
        return new LayoutIterator(this);
    }

    private class LayoutIterator implements Iterator<LayoutField> {
        
        private int height, width;

        LayoutIterator(Layout layout) {
            this.height = layout.size.getHeight();
            this.width = layout.size.getWidth();
        }

        // TODO: Check if the next element exists 
        @Override
        public boolean hasNext() {
            return false;
        }

        // TODO: Return the current element, then advance the cursor.
        @Override
        public LayoutField next() {
            
            
            return null;
        }

    }
}
