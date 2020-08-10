package learner.argparser4j;

import java.util.List;
import javautilwrappers.BasicArrayList;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Argparse4jSubParserTest {

    @Test
    public void subParserDocsTest() {
        Namespace name1 = subParserCommandFromDocs(new String[]{"Hello", "Kitty"});
        assertNull(name1);

        Namespace name2 = subParserCommandFromDocs(new String[]{"a", "12"});
        assertFalse((boolean) name2.get("foo")); //Apparently does not autounbox?
        assertEquals(name2.get("bar"), Integer.valueOf(12));

    }

    private Namespace subParserCommandFromDocs(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("prog").build();
        parser.addArgument("--foo").action(Arguments.storeTrue()).help("foo help");
        Subparsers subparsers = parser.addSubparsers().help("sub-command help");

        Subparser parserA = subparsers.addParser("a").help("a help");
        parserA.addArgument("bar").type(Integer.class).help("bar help");

        Subparser parserB = subparsers.addParser("b").help("b help");
        parserB.addArgument("--baz").choices("X", "Y", "Z").help("baz help");
        try {
            return parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            return null;
        }
    }

    @Test
    public void mySubParserTest() {
        Namespace name1 = mySubParserCommand(new String[]{"CommandA", "1", "2", "3"});
        BasicArrayList<Integer> expectedNums = new BasicArrayList<>();
        expectedNums.add(1);
        expectedNums.add(2);
        expectedNums.add(3);
        assertEquals(expectedNums, name1.get("numbers"));

        Namespace name2 = mySubParserCommand(new String[]{"CommandB", "Hello", "Kitty"});
        BasicArrayList<String> expectedStrs = new BasicArrayList<>();
        expectedStrs.add("Hello");
        expectedStrs.add("Kitty");
        assertEquals(expectedStrs, name2.get("strings"));

        //Does not work as intended. 
        Namespace name3 = mySubParserCommand(
                new String[]{"CommandA", "1", "2", "3", "CommandB", "Hello", "Kitty"});
        assertNull(name3);

        //Without top level arguments, this also will not work. 
        Namespace name4 = mySubParserCommand(new String[]{"Hello", "Kitty"});
        assertNull(name4);

    }

    private Namespace mySubParserCommand(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("Erasmus").build();
        Subparsers subparsers = parser.addSubparsers().help("sub-command help");

        Subparser parserCommandA = subparsers.addParser("CommandA").help("Command A help");
        parserCommandA.addArgument("numbers").
                type(Integer.class).
                nargs("+").
                help("Any set of real numbers");

        Subparser parserCommandB = subparsers.addParser("CommandB").help("Command B help");
        parserCommandB.addArgument("strings").
                nargs("*").
                help("Any set of strings.");

        try {
            return parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            return null;
        }
    }

    @Test
    public void parserDefaultsTest() {
        ArgumentParser parser = ArgumentParsers.newFor("prog").build();
        Subparsers subparsers = parser.addSubparsers();
        Subparser parserSum = subparsers.addParser("one")
                .setDefault("func", 1);
        Subparser parserMax = subparsers.addParser("two")
                .setDefault("func", 2);
        try {
            Namespace res1 = parser.parseArgs(new String[] {"one"});
            Namespace res2 = parser.parseArgs(new String[] {"two"});
            assertEquals(1, (int) res1.get("func"));
            assertEquals(2, (int) res2.get("func"));
            
        } catch (ArgumentParserException e) {
            fail();
        }
    }

}
