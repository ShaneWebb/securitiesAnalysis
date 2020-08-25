package learner.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class TimeLibTest {

    @Test
    public void dateTest() throws Exception {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = df.parse("8/24/2020");
        //System.out.println(date);
    }
    
}
