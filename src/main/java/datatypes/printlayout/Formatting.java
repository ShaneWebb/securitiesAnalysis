package datatypes.printlayout;

public class Formatting {
    private Justification justification;
    private int paddingThickness, borderThickness, outlineSize;
    private int maxLabelLineWidth;
    
    public Formatting(Formatting formatting) {
        this.justification = formatting.justification;
        this.paddingThickness = formatting.paddingThickness;
        this.borderThickness = formatting.borderThickness;
        this.maxLabelLineWidth = formatting.maxLabelLineWidth;
        this.outlineSize = formatting.outlineSize;
    }
    
    private Formatting() {
        
    }
    
    public static class Builder {
        private Justification justification;
        private int paddingThickness, borderThickness, outlineSize;
        private int maxLabelLineWidth;
        
        public Builder() {
            justification = Justification.CENTER;
            paddingThickness = 1;
            borderThickness = 1;
            maxLabelLineWidth = 16;
            outlineSize = generateOutlineSize(paddingThickness, borderThickness);
        }
        
        public Builder setJustification(Justification justification) {
            this.justification = justification;
            return this;
        }
        
        public Builder setPaddingThickness(int paddingThickness) {
            this.paddingThickness = paddingThickness;
            return this;
        }
        
        public Builder setBorderThickness(int borderThickness) {
            this.borderThickness = borderThickness;
            return this;
        }
        
        public Builder setMaxLabelLineWidth(int maxLabelLineWidth) {
            this.maxLabelLineWidth = maxLabelLineWidth;
            return this;
        }
        
        public Formatting build() {
            Formatting formatting = new Formatting();
            formatting.justification = this.justification;
            formatting.paddingThickness = this.paddingThickness;
            formatting.borderThickness = this.borderThickness;
            formatting.maxLabelLineWidth = this.maxLabelLineWidth;
            formatting.outlineSize = generateOutlineSize(this.paddingThickness, this.borderThickness);
            return formatting;
        }
        
    }
    
    private static int generateOutlineSize(int paddingThickness, int borderThickness) {
        return 2 * (paddingThickness + borderThickness);
    }

    public int getOutlineSize() {
        return outlineSize;
    }

    public Justification getJustification() {
        return justification;
    }

    public int getPaddingThickness() {
        return paddingThickness;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public int getMaxLabelLineWidth() {
        return maxLabelLineWidth;
    }

}
