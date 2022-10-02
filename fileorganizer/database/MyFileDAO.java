package fileorganizer.database;

import metadata.MyFile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MyFileDAO extends DAO {
    private final String TABLE_NAME = "FILES";
    private HashMap hashMap;

    public MyFileDAO(Connection conn) {
        super(conn);
        try {
            getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hashMap = new HashMap<String, Integer>();
    }

    public void insertFiles(Set<String> fileSet) throws SQLException {
        insertStatement = conn.prepareStatement("INSERT INTO " + TABLE_NAME + " (PATH) VALUES(?)");
        for (String s : fileSet) {
            insertStatement.setString(1, s);
            insertStatement.executeUpdate();
        }
    }

    public HashSet<String> getPathNames() throws SQLException {
        HashSet<String> pathNamesSet = new HashSet<>();
        rs = getDataStatement.executeQuery("SELECT PATH FROM FILES;");
        while (rs.next()) {
            String s = rs.getString(1);
            pathNamesSet.add(s);
        }
        return pathNamesSet;
    }

    public HashSet<MyFile> getFiles() throws SQLException {
        HashSet<MyFile> myFileSet = new HashSet<>();
        rs = getDataStatement.executeQuery("SELECT ID_PATH, PATH FROM FILES;");
        while (rs.next()) {
            MyFile myFile = new MyFile(rs.getString(2));
            myFile.setId(rs.getInt(1));
            myFileSet.add(myFile);
        }
        for (MyFile f : myFileSet) {
            System.out.println(f.getId());
            System.out.println(f.getPath());
        }
        return myFileSet;
    }

    HashMap<String, Integer> dataToHashMap() throws SQLException {
        HashSet<MyFile> myFileSet = getFiles();
        for (MyFile myFile : myFileSet) {
            hashMap.put(myFile.getPath(), myFile.getId());
        }
        return hashMap;
    }
}
