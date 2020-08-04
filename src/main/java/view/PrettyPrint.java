package view;

import datatypes.Report;
import main.Main;

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
    public void prettyPrinter(Report report) {
        System.out.println("CLI report coming soon! Use help function instead.");
    }

}
