package learner.h2.onetime;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.h2.tools.Csv;

public class CSVWrite {

    private static Connection inMemConn;
    private static Statement stmt;

    public static void main(String[] args) throws Exception {

        
        inMemConn = DriverManager.getConnection("jdbc:h2:./data/h2/hist");
        stmt = inMemConn.createStatement();

        File f = new File("./data");
        String[] files = f.list();

        for(String file: files) {
            final int fLen = file.length();
            if(fLen > 4 && ".csv".equals(file.substring(fLen - 4, fLen))) {
                migrateToDB(file.substring(0, fLen - 4));
            }
        }
  
        stmt.close();
        inMemConn.close();
    }

    private static void migrateToDB(String ticker) throws SQLException, ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String query1 = "Create table \"" + ticker + "\" "
                + "(line INTEGER not NULL,"
                + "date TIMESTAMP not NULL, "
                + " volume INTEGER not NULL, "
                + " open INTEGER not NULL, "
                + " close INTEGER not NULL, "
                + " high INTEGER not NULL, "
                + " low INTEGER not NULL, "
                + " adjclose INTEGER not NULL, "
                + " PRIMARY KEY ( line, date ))";
        stmt.executeUpdate(query1);

        String query2 = "Insert into \"" + ticker + 
                "\" values(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = inMemConn.prepareStatement(query2);

        ResultSet rs = new Csv().read("./data/" + ticker + ".csv", null, null);
        int lineNum = 1;
        while (rs.next()) {
            pstmt.setInt(1, lineNum++);
            pstmt.setTimestamp(2, new Timestamp(df.parse(rs.getString(1)).getTime()));
            pstmt.setInt(3, (int) Math.round(rs.getDouble(2)));
            pstmt.setInt(4, (int) Math.round(rs.getDouble(3) * 100));
            pstmt.setInt(5, (int) Math.round(rs.getDouble(4) * 100));
            pstmt.setInt(6, (int) Math.round(rs.getDouble(5) * 100));
            pstmt.setInt(7, (int) Math.round(rs.getDouble(6) * 100));
            pstmt.setInt(8, (int) Math.round(rs.getDouble(7) * 100));
            pstmt.execute();
        }

//        String query3 = "Select * from A where open < (close * 0.9)";
//        rs = stmt.executeQuery(query3);
//        ResultSetMetaData meta = rs.getMetaData();
//        while (rs.next()) {
//            for (int i = 0; i < meta.getColumnCount(); i++) {
//                System.out.println(meta.getColumnLabel(i + 1) + ": "
//                        + rs.getString(i + 1));
//            }
//        }

        rs.close();
        pstmt.close();
        
        System.out.println(ticker + " migrated!");
    }
}
