package fileorganizer.database;

import metadata.PathWithTag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class PathWithTagDAO extends DAO {
    private final String TABLE_NAME = "PATH_WITH_TAG";
    private final TagDAO tagDAO;
    private final MyFileDAO myFileDAO;

    public PathWithTagDAO(Connection conn) throws SQLException{
        super(conn);
        this.tagDAO = new TagDAO(conn);
        this.myFileDAO = new MyFileDAO(conn);
        getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

     public void insertFileWithTags(HashSet<PathWithTag> pathWithTagHashSet) throws SQLException {
        HashSet<PathWithTag> pathWithTags = pathWithTagHashSet;

        HashMap<String, Integer> filesHashMap = myFileDAO.dataToHashMap();
        HashMap<String, Integer> tagHashMap = tagDAO.dataToHashMap();
        insertStatement = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(ID_PATH, ID_TAG) VALUES(?, ?)");

        for(PathWithTag p : pathWithTags) {
            insertStatement.setInt(1, filesHashMap.get(p.getPathName()));
            insertStatement.setInt(2, tagHashMap.get(p.getTagName()));
        }
    }

    public HashSet<PathWithTag> getDataAsString() throws SQLException {
        HashSet<PathWithTag> pathWithTagSet = new HashSet<>();
       rs = getDataStatement.executeQuery("SELECT files.path, tags.name FROM FILES_WITH_TAGS");
        return pathWithTagSet;
    }

}
