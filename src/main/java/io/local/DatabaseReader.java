package io.local;

import io.console.SupportedArgs;
import io.datatypes.ParsedInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

public class DatabaseReader extends AbstractDataReader {

    private Connection conn;
    
    public DatabaseReader() {
        super();
    }

    @Override
    public ParsedInfo read(MapWrapper<SupportedArgs, Object> parsedArgs)
            throws IOException {
        ParsedInfo result;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./db/hist");
            result = super.read(parsedArgs);
            conn.close();
        } catch (SQLException ex) {
            throw new IOException(ex);
        } 
        return result;
    }

    @Override
    protected final void setArgs(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super.setArgs(parsedArgs);
        String tables = (String) parsedArgs.get(SupportedArgs.DB);
        delimitedSources = tables.split(",");
    }

    @Override
    protected MapWrapper<Integer, MapWrapper<String, Object>> readSource(String source)
            throws IOException {
        
        String startTS = new Timestamp(startDateF.getTime()).toString();
        String endTS = new Timestamp(endDateF.getTime()).toString();
        String query = "Select " + header + ",line,date from \"" + source + "\" "
                + "where date between \'" + startTS + "\' and \'" + endTS + "\'";
        
        MapWrapper<Integer, MapWrapper<String, Object>> srcByLine 
                = new HashMapWrapper<>();
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                MapWrapper<String, Object> parsedLine = new HashMapWrapper<>();
                parsedLine.put("date", new Date(rs.getTimestamp("date").getTime()));
                parsedLine.put("value", (double) rs.getInt(header));
                srcByLine.put(rs.getInt("line"), parsedLine);
            }
        } catch (SQLException ex) {
            throw new IOException(ex);
        }
        return srcByLine;
    }

}
