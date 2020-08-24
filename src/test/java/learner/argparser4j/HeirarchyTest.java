package learner.argparser4j;

import java.util.Arrays;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeirarchyTest {

    @Test
    public void simpleHeirachyTest() {
        ArgumentParser parser = ArgumentParsers.newFor("Test").build();
        Subparsers subparsers = parser.addSubparsers().help("sub-command help");

        parser.addArgument("numbersTop").
                type(Integer.class).
                nargs(2). //Cannot use + or *. Parser cannot separate args from subcommand.
                help("Any set of real numbers");

        Subparser parserCommandA = subparsers.addParser("A").help("Command A help");
        parserCommandA.addArgument("--numbersBottom").
                type(Integer.class).
                nargs("+").
                help("Any set of real numbers");

        try {
            Namespace ns = parser.parseArgs(
                    new String[]{"1", "2", "A", "--numbersBottom", "1", "2"});
            MapWrapper<String, Object> wrappedMap = new HashMapWrapper<>(ns.getAttrs());
            MapWrapper<String, Object> expectedMap = new HashMapWrapper<>();
            expectedMap.put("numbersTop", Arrays.asList(new Integer[]{1, 2}));
            expectedMap.put("numbersBottom", Arrays.asList(new Integer[]{1, 2}));

            assertEquals(expectedMap, wrappedMap);

        } catch (ArgumentParserException ex) {
            System.out.println(ex.getMessage());
            fail();
        }

    }

}
