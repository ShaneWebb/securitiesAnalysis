package learner.argparser4j;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Argparse4jCustomParserTest {
    
    public class myCustomParser {
        
        ArgumentParser parser;
        public myCustomParser() {
            parser = ArgumentParsers.newFor("Stats").build();
            parser.description("A basic statistics package.");
        }
        
    }

    @Test
    public void customParserTest() {
        
    }

}
