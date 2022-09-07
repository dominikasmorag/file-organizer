package fileorganizer.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TagDAO extends DAO {
    private final String TABLE_NAME = "TAGS";

    public TagDAO(Connection conn) throws SQLException {
        super(conn);
    }

    public Set<String> getTagNames() throws SQLException {
        Set<String> tagNameSet = new HashSet<>();
        getDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = getDataStatement.executeQuery("SELECT name FROM " + TABLE_NAME);
        while(rs.next()) {
            String tagName = rs.getString(1);
            tagNameSet.add(tagName);
        }
        return tagNameSet;
    }

    public void insertTagsToDb(Set<String> tagNamesSet) throws SQLException {
        insertStatement = conn.prepareStatement("INSERT INTO TAGS(NAME) VALUES(?);");
        for(String s : tagNamesSet) {
            insertStatement.setString(1, s);
            insertStatement.executeUpdate();
        }
    }
}
