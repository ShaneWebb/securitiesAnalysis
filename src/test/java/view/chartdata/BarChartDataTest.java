package view.chartdata;

import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;
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
        map0.put("stochastic", false);
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
    //@Disabled
    public void testAddSubDataToInternalCollection() {

        MapWrapper<String, Object> item1 = new HashMapWrapper<>();
        item1.put("value", 100.0);
        item1.put("row", "Row 1");
        item1.put("col", "Column 1");
        MapWrapper<String, Object> item2 = new HashMapWrapper<>();
        item2.put("value", 200.0);
        item2.put("row", "Row 1");
        item2.put("col", "Column 1");
        
        ChartSubDataWrapper subdata = new BarChartSubData("file");
        subdata.add(item1); subdata.add(item2);
        
        BarChartData barchartdata = new BarChartData(map0);
        barchartdata.addSubDataToInternalCollection(subdata);

        assertEquals(1, ((DefaultCategoryDataset) barchartdata.unwrap()).getValue("190 - 200", "file"));
        assertEquals(1, ((DefaultCategoryDataset) barchartdata.unwrap()).getValue("100 - 110", "file"));

    }

    @Test
    public void rangeFindBuilderTest() {
        ListWrapper<MapWrapper<String, Object>> internalSubData
                = new ArrayListWrapper<>();

        for (int i = 0; i <= 500; ++i) {
            MapWrapper<String, Object> tempMap = new HashMapWrapper<>();
            tempMap.put("value", (double) i);
            internalSubData.add(tempMap);
        }
        //Note: data internally specifies the number of bins of 10.
        AbstractBinnedData.RangeFinder range = data.rangeFinderFactory(internalSubData);
        String[] expectedResults = {
            "0 - 50",
            "50 - 100",
            "100 - 150",
            "150 - 200",
            "200 - 250",
            "250 - 300",
            "300 - 350",
            "350 - 400",
            "400 - 450",
            "450 - 500",
            "450 - 500"};
        for (int i = 0; i <= 500; i += 50) {
            assertEquals(expectedResults[i / 50], range.getRange(i));
            //System.out.println(range.getRange(i));
        }

    }

    @Test
    public void rangeFindTest() {
        AbstractBinnedData.RangeFinder limits = new AbstractBinnedData.RangeFinder(0, 500, 10);
        String[] expectedResults = {
            "0 - 50",
            "50 - 100",
            "100 - 150",
            "150 - 200",
            "200 - 250",
            "250 - 300",
            "300 - 350",
            "350 - 400",
            "400 - 450",
            "450 - 500",
            "450 - 500"};
        for (int i = 0; i <= 500; i += 50) {
            assertEquals(expectedResults[i / 50], limits.getRange(i));
        }

    }

}
