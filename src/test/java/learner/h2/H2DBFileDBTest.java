package learner.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class H2DBFileDBTest {

    private Connection inMemConn;
    private Statement stmt;

    @BeforeEach
    public void createDB() throws Exception {
        inMemConn = DriverManager.getConnection("jdbc:h2:./data/h2/h2");
        stmt = inMemConn.createStatement();
    }

    @AfterEach
    public void closeDB() throws Exception {
        inMemConn.close();
    }

//    @Test
//    public void initDB() throws Exception {
//        String sql = "Create table testing "
//                + "(id INTEGER not NULL, "
//                + " first VARCHAR(255), "
//                + " last VARCHAR(255), "
//                + " age INTEGER, "
//                + " PRIMARY KEY ( id ))";
//        stmt.executeUpdate(sql);
//    }
    @Test
    public void embeddedDBTest() throws Exception {
//        String sqlInsert = "INSERT INTO testing "
//                + "VALUES (100, 'Zara', 'Ali', 18)";
//        stmt.executeUpdate(sqlInsert);

        String sqlSelect = "SELECT first FROM testing "
                + "WHERE id = 100";
        ResultSet rs1 = stmt.executeQuery(sqlSelect);

        if (rs1.next()) {
            assertEquals("Zara", rs1.getString("first"));
        } else {
            fail();
        }

        String sqlSelectMore = "Select first, last, age FROM testing "
                + "where id = 100";
        ResultSet rs2 = stmt.executeQuery(sqlSelectMore);

        if (rs2.next()) {
            assertEquals("Ali", rs2.getString("last"));
            assertEquals("18", rs2.getString("age"));
            assertEquals(18, rs2.getInt("age"));
        } else {
            fail();
        }
    }
}
