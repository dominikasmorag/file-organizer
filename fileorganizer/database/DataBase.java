package fileorganizer.database;
import java.sql.*;

public class DataBase {
    private static final String FILES_TABLE_NAME = "FILES";
    private static final String TAGS_TABLE_NAME = "TAGS";
    private static final String CREATE_FILES_TABLE = "CREATE TABLE IF NOT EXISTS " + FILES_TABLE_NAME + " (" +
            "ID_PATH NUMBER AUTO_INCREMENT PRIMARY KEY," +
            "PATH VARCHAR(400));";
    private static final String CREATE_TAGS_TABLE = "CREATE TABLE IF NOT EXISTS "+ TAGS_TABLE_NAME + " (" +
            "ID_TAG NUMBER AUTO_INCREMENT PRIMARY KEY," +
            "NAME VARCHAR(30));";

    public static void createSchema(Connection conn) throws SQLException {
        createFilesTable(conn);
        createTagsTable(conn);
    }

    private static void createFilesTable(Connection conn) throws SQLException {
        Statement createTable = conn.createStatement();
        createTable.execute(CREATE_FILES_TABLE);
    }

    private static void createTagsTable(Connection conn) throws SQLException {
        Statement createTagsTable = conn.createStatement();
        createTagsTable.execute(CREATE_TAGS_TABLE);
    }
}
