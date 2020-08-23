package process;

import io.local.BasicFileReader;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlotterTest {
    
    private AutoCloseable closeable;
    
    @Mock
    private BasicFileReader reader;
    
    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }
    
    @Test
    public void testExecute() {
        MapWrapper<String, Object> map1 = new HashMapWrapper<>();
        map1.put("files", "A.csv,B.csv");
        map1.put("startDate", "8/21/1981");
        map1.put("endDate", "1/1/2020");
        map1.put("lineartrend", true);
        map1.put("type", Visualizations.BASIC);
        
        Plotter testPlotter = new Plotter(reader);
        try {
            testPlotter.setArgs(map1);
            testPlotter.execute();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
    }

}
