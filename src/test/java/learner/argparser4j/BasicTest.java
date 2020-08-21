package learner.argparser4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javautilwrappers.BasicArrayList;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BasicTest {

    private static interface Accumulate {
        int accumulate(Collection<Integer> ints);
    }

    private static class Sum implements Accumulate {
        @Override
        public int accumulate(Collection<Integer> ints) {
            int sum = 0;
            for (Integer i : ints) {
                sum += i;
            }
            return sum;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    private static class Max implements Accumulate {
        @Override
        public int accumulate(Collection<Integer> ints) {
            return Collections.max(ints);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }
    
    @Test
    public void example() {
        int returnMax = argParseAccumulate("1", "2", "3", "4");
        int returnSum = argParseAccumulate("--sum", "1", "2", "3", "4");
        assertEquals(4, returnMax);
        assertEquals(10, returnSum);
        // argParseAccumulate("--h");
    }

    private int argParseAccumulate(String ... args) {
        ArgumentParser parser = ArgumentParsers.newFor("prog").build()
                .description("Process some integers.");
        parser.addArgument("integers")
                .metavar("N")
                .type(Integer.class)
                .nargs("+")
                .help("an integer for the accumulator");
        parser.addArgument("--sum")
                .dest("accumulate")
                .action(Arguments.storeConst())
                .setConst(new Sum())
                .setDefault(new Max())
                .help("sum the integers (default: find the max)");
        
        int result = 0;
        try {
            Namespace res = parser.parseArgs(args);
            result = ((Accumulate) res.get("accumulate"))
                    .accumulate((List<Integer>) res.get("integers"));
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }
        return result;
    }

}
