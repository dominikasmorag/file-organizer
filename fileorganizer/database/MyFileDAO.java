package fileorganizer.database;

import metadata.MyFile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyFileDAO extends DAO {
    private final String TABLE_NAME = "FILES";

    public MyFileDAO(Connection conn) {
        super(conn);
    }

    public void insertFiles(Set<String> fileSet) throws SQLException {
        insertStatement = conn.prepareStatement("INSERT INTO " + TABLE_NAME + " (PATH) VALUES(?);");
        for(String s : fileSet) {
            insertStatement.setString(1, s);
            insertStatement.executeUpdate();
        }
    }

    public Set<String> getPathNames() throws SQLException {
        Set<String> pathNamesSet = new HashSet<>();
        getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = getDataStatement.executeQuery("SELECT PATH FROM FILES;");
        while(rs.next()) {
            String s = rs.getString(1);
            pathNamesSet.add(s);
        }
        return pathNamesSet;
    }

    public void getFiles() throws SQLException {
       ArrayList<MyFile> myFileSet = new ArrayList<>();
        getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = getDataStatement.executeQuery("SELECT ID_PATH, PATH FROM FILES;");
        while(rs.next()) {
            MyFile myFile = new MyFile(rs.getString(2));
            myFile.setId(rs.getInt(1));
            myFileSet.add(myFile);
        }
        for(MyFile f : myFileSet) {
            System.out.println(f.getId());
            System.out.println(f.getPath());
        }
    }
}
