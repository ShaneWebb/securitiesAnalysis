package view;

// TODO: Finish pretty print. 
import datatypes.printlayout.fields.LayoutField;
import datatypes.printlayout.*;

public class PrettyPrint {

    String border, filler;

    private PrettyPrint() {
    }

    public static class Builder {

        String border, filler;

        public Builder border(String border) {
            this.border = border;
            return this;
        }

        public Builder filler(String filler) {
            this.filler = filler;
            return this;
        }

        public PrettyPrint build() {
            PrettyPrint prettyPrintOb = new PrettyPrint();
            prettyPrintOb.border = this.border;
            prettyPrintOb.filler = this.filler;
            return prettyPrintOb;
        }

    }

    //TODO: Create prettyPrinter, which accepts a displayable object. 
    public void prettyPrinter(Displayable displayableItem) {
        Layout itemLayout = displayableItem.generateLayout();
        StringBuilder printlnCompatibleOutput = spool(itemLayout);
        System.out.println(printlnCompatibleOutput);

    }

    //TODO: Implement a spooling function, which takes a layout item and then
    //produces a single string with newline characters that can be printed as
    //intended by the println function. 
    private StringBuilder spool(Layout inputItem) {
        StringBuilder result = new StringBuilder();
        for (LayoutField primitive : inputItem) {
            String convertedStr = primitive.convert(border, filler);
            result.append(convertedStr);
        }
        return result;
    }

}
