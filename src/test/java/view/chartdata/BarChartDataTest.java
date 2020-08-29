package view.chartdata;

import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import process.DisplayTypeBinned;
import process.Visualizations;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BarChartDataTest {

    private final BarChartData data;
    private final DefaultCategoryDataset dataset;
    private final MapWrapper<String, Object> map0;
    
    private AutoCloseable closeable;
    
    @Mock
    private ChartSubDataWrapper subdatawrapper;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    public BarChartDataTest() {
        dataset = new DefaultCategoryDataset();
        dataset.addValue(20.0, "Row 1", "Column 1");
        dataset.addValue(50.0, "Row 1", "Column 2");
        dataset.addValue(20.0, "Row 1", "Column 3");
        dataset.addValue(2.0, "Row 2", "Column 1");
        dataset.addValue(3.0, "Row 2", "Column 2");
        dataset.addValue(2.0, "Row 2", "Column 3");

        map0 = new HashMapWrapper<>();
        map0.put("files", "A.csv,B.csv");
        map0.put("header", "volume");
        map0.put("startDate", "8/21/1981");
        map0.put("endDate", "1/1/2020");
        map0.put("xAxis", "Date");
        map0.put("lineartrend", false);
        map0.put("type", Visualizations.BINNED);
        map0.put("displayType", DisplayTypeBinned.BAR);
        map0.put("bins", 10);

        data = new BarChartData(map0, dataset);
    }

    @Test
    public void testUnwrap() {
        assertEquals(dataset, data.unwrap());
    }

    @Test
    public void testAddSubDataToInternalCollection() {
        ListWrapper<MapWrapper<String, Object>> internalSubData
                = new ArrayListWrapper<>();
        MapWrapper<String, Object> item = new HashMapWrapper<>();
        item.put("value", 100.0);
        item.put("row", "Row 1");
        item.put("col", "Column 1");
        internalSubData.add(item);
        
        when(subdatawrapper.unwrap()).thenReturn(internalSubData);
        
        data.addSubDataToInternalCollection(subdatawrapper);
        assertEquals(100.0, ((DefaultCategoryDataset) data.unwrap()).getValue("Row 1", "Column 1"));

    }

    @Test
    @Disabled
    public void testChartSubDataFactory() {

    }

    @Test
    @Disabled
    public void testParseSingleCsvLine() throws Exception {

    }

}
