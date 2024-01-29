package fileorganizer.database;

import metadata.PathWithTag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class PathWithTagDAO extends DAO {
    private final String TABLE_NAME = "FILES_WITH_TAGS";
    private final TagDAO tagDAO;
    private final MyFileDAO myFileDAO;

    public PathWithTagDAO(Connection conn) throws SQLException{
        super(conn);
        this.tagDAO = new TagDAO(conn);
        this.myFileDAO = new MyFileDAO(conn);
        getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

     public void insertFileWithTags(HashSet<PathWithTag> pathWithTagHashSet) throws SQLException {
        HashMap<String, Integer> filesHashMap = myFileDAO.dataToHashMap();
        HashMap<String, Integer> tagHashMap = tagDAO.dataToHashMap();
        insertStatement = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(PATH_ID, TAG_ID) VALUES(?,?)");

        for(PathWithTag p : pathWithTagHashSet) {
            insertStatement.setInt(1, filesHashMap.get(p.getPathName()));
            insertStatement.setInt(2, tagHashMap.get(p.getTagName()));
            try {
                insertStatement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public HashSet<PathWithTag> findFileWithTag()  throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the extension without dot");
        String e = sc.next();
        System.out.println("enter the tag for the file you are looking for");
        String t = sc.next();
        return getFileWithTag(e, t);
    }

    private HashSet<PathWithTag> getFileWithTag(String extension, String tag) throws SQLException {
        HashSet<PathWithTag> fileWithTagSet = new HashSet<>();
        rs = getDataStatement.executeQuery("SELECT files.path, tags.name FROM FILES, TAGS INNER JOIN files_with_tags" +
                " ON files_with_tags.path_id = files.id AND files_with_tags.tag_id = tags.id WHERE files.path LIKE '%." + extension + "'" +
                " AND tags.name LIKE '" + tag + "'");
        while(rs.next()) {
            PathWithTag temp = new PathWithTag(rs.getString(1), rs.getString(2));
            fileWithTagSet.add(temp);
        }
        return fileWithTagSet;
    }

    public HashSet<PathWithTag> getDataAsString() throws SQLException {
        HashSet<PathWithTag> pathWithTagSet = new HashSet<>();
         rs = getDataStatement.executeQuery("SELECT files.path, tags.name FROM FILES, TAGS INNER JOIN files_with_tags" +
                " ON files_with_tags.path_id = files.id AND files_with_tags.tag_id = tags.id");
        while(rs.next()) {
            PathWithTag temp = new PathWithTag(rs.getString(1), rs.getString(2));
            pathWithTagSet.add(temp);
        }
        return pathWithTagSet;
    }

}
