package fileorganizer.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAO {
    private final Connection conn;
    private final PreparedStatement insertFilesStatement;
    private final PreparedStatement insertTagsStatement;
    private Statement getDataStatement;
    private ResultSet rs;

    public DAO(Connection conn) throws SQLException {
        this.conn = conn;
        this.insertFilesStatement = conn.prepareStatement("INSERT INTO FILES(" +
                "PATH) VALUES(?)");
        this.insertTagsStatement = conn.prepareStatement("INSERT INTO TAGS(" +
                "NAME) VALUES(?)");
    }

    public void insertFiles(List<File> fileList) throws SQLException {
        for(File f : fileList) {
            insertFilesStatement.setString(1, f.getPath());
            insertFilesStatement.executeUpdate();
        }
    }

    public void insertTags(Set<String> tagSet) throws SQLException {
        for(String s : tagSet) {
            insertTagsStatement.setString(1, s);
            insertTagsStatement.executeUpdate();
        }
    }

    public ArrayList<File> getFilesFromDb() throws SQLException {
      ArrayList<File> fileDaoList = new ArrayList<>();
      this.getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      this.rs = getDataStatement.executeQuery("SELECT * FROM files LIMIT 100;");
      while(rs.next()) {
          File file = new File(rs.getString(2));
          fileDaoList.add(file);
      }
      return fileDaoList;
    }

    public Set<String> getTagsFromDb() throws SQLException {
        Set<String> tagSet = new HashSet<>();
        this.getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        this.rs = getDataStatement.executeQuery("SELECT * FROM tags;");
        while(rs.next()) {
            String s = rs.getString(2);
            tagSet.add(s);
        }
        return tagSet;
    }
}
