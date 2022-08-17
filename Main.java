import fileorganizer.database.DAO;
import fileorganizer.database.DataBase;
import org.h2.jdbcx.JdbcDataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:C:/Users/domin/Desktop/git/filesdatabase");
        ds.setUser("sa");
        ds.setPassword("sa");

        Connection conn = ds.getConnection();
        DataBase.createSchema(conn);
        File f = new File("C:\\Users\\domin\\Desktop\\FileOrganizerTest");
        DAO dao = new DAO(conn);
        ProgramController programController = new ProgramController(f, dao);
        programController.showMenu();

        File[] files = dao.getFilesFromDb().toArray(new File[0]);
        String[] tags = dao.getTagsFromDb().toArray(new String[0]);

    }
}
