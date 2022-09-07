package fileorganizer.database;

import java.sql.*;

public class DAO {
    protected final Connection conn;
    protected PreparedStatement insertStatement;
    protected Statement getDataStatement;
    protected ResultSet rs;

    public DAO(Connection conn) {
        this.conn = conn;
    }
}
