package fileorganizer.database;

import metadata.Tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TagDAO extends DAO {
    private final String TABLE_NAME = "TAGS";
    private HashMap hashMap;

    public TagDAO(Connection conn) throws SQLException {
        super(conn);
        hashMap = new HashMap<String, Integer>();
        getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public HashSet<String> getTagNames() throws SQLException {
        HashSet<String> tagNameSet = new HashSet<>();
        rs = getDataStatement.executeQuery("SELECT name FROM " + TABLE_NAME);
        while(rs.next()) {
            String tagName = rs.getString(1);
            tagNameSet.add(tagName);
        }
        return tagNameSet;
    }

    public HashSet<Tag> getTags() throws SQLException {
        HashSet<Tag> tagHashSet = new HashSet<>();
        rs = getDataStatement.executeQuery("SELECT * FROM " + TABLE_NAME);
        while(rs.next()) {
            Tag tag = new Tag();
            tag.setId(rs.getInt(1));
            tag.setName(rs.getString(2));
            tagHashSet.add(tag);
        }
        return tagHashSet;
    }

    public void insertTagsToDb(Set<String> tagNamesSet) throws SQLException {
        insertStatement = conn.prepareStatement("INSERT INTO TAGS(NAME) VALUES(?);");
        for(String s : tagNamesSet) {
            insertStatement.setString(1, s);
            insertStatement.executeUpdate();
        }
    }

    HashMap<String, Integer> dataToHashMap() throws SQLException {
        HashSet<Tag> tagSet = getTags();
        for(Tag tag : tagSet) {
            hashMap.put(tag.getName(), tag.getId());
        }
        return hashMap;
    }
}
