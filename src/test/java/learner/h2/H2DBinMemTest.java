package learner.h2;

import java.sql.*;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class H2DBinMemTest {

    private Connection inMemConn;
    private Statement stmt;

    @BeforeEach
    public void createDB() throws Exception {
        inMemConn = DriverManager.getConnection("jdbc:h2:mem:");
        stmt = inMemConn.createStatement();
    }

    @AfterEach
    public void closeDB() throws Exception {
        inMemConn.close();
    }

    @Test
    public void basicTest() {
        try ( ResultSet rs = stmt.executeQuery("SELECT 1+1")) {
            if (rs.next()) {
                assertEquals(rs.getInt(1), 2);
            } else {
                fail("Nothing in the resultset!");
            }
        } catch (SQLException ex) {
            fail(ex.getMessage());
        } finally {
        }
    }

    @Test
    public void queryTests() throws Exception {
        String sql = "CREATE TABLE REGISTRATION "
                + "(id INTEGER not NULL, "
                + " first VARCHAR(255), "
                + " last VARCHAR(255), "
                + " age INTEGER, "
                + " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);

        String sqlInsert = "INSERT INTO REGISTRATION "
                + "VALUES (100, 'Zara', 'Ali', 18)";
        stmt.executeUpdate(sqlInsert);

        String sqlSelect = "SELECT first FROM REGISTRATION "
                + "WHERE id = 100";
        ResultSet rs1 = stmt.executeQuery(sqlSelect);

        if (rs1.next()) {
            assertEquals("Zara", rs1.getString("first"));
        } else {
            fail();
        }

        String sqlSelectMore = "Select first, last, age FROM Registration "
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

    @Test
    public void dateTest() throws Exception {
        String sql = "CREATE TABLE sample "
                + "(id DATE not NULL, "
                + " open BIGINT, "
                + " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);

        String sqlInsert = "Insert into sample "
                + "VALUES (?, 4000)";
        PreparedStatement pstmt = inMemConn.prepareStatement(sqlInsert);
        pstmt.setDate(1, Date.valueOf("1992-06-01"));
        pstmt.execute();
        
        String sqlSelect = "Select * from sample";
        ResultSet rs = stmt.executeQuery(sqlSelect);
        
        if(rs.next()) {
            assertEquals(Date.valueOf("1992-06-01"), rs.getDate("id"));
        } else {
            fail();
        }
    }

}
