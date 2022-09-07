import fileorganizer.database.DataBase;
import org.h2.jdbcx.JdbcDataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:C:/Users/domin/Desktop/git/filesdb");
        ds.setUser("sa");
        ds.setPassword("sa");

        Connection conn = ds.getConnection();
        DataBase.createSchema(conn);
        File f = new File("C:\\Users\\domin\\Desktop\\FileOrganizerTest");
        ProgramController programController = new ProgramController(conn, f);
        programController.showMenu();
    }
}
