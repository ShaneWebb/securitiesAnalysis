package learner.h2;

import java.sql.*;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class H2DBTest {

    private static Connection conn;

    @BeforeAll
    public static void createDB() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
    }

    @AfterAll
    public static void closeDB() throws Exception {
        conn.close();
    }

    @Test
    public void basicTest() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1+1");
            if (rs.next()) {
                assertEquals(rs.getInt(1), 2);
            }
        }
        catch (SQLException ex) {
            fail(ex.getMessage());
        }
    }

}
