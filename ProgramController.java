import fileorganizer.database.MyFileDAO;
import fileorganizer.database.TagDAO;
import metadata.ResultFile;
import metadata.Tag;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class ProgramController {
    private final Connection connection;
    private final File file;
    private final Tag tag;
    private final MyFileDAO myFileDAO;
    private final ResultFile directory;
    private final TagDAO tagDAO;

    ProgramController(Connection connection, File file) throws SQLException {
        this.connection = connection;
        this.file = file;
        tag = new Tag();
        myFileDAO = new MyFileDAO(connection);
        directory = new ResultFile(myFileDAO);
        tagDAO = new TagDAO(connection);
    }

    public void showMenu() throws SQLException {
        String s = "";
        while(!s.equalsIgnoreCase("7")) {
            Scanner sc = new Scanner(System.in);
            System.out.println("1 - create new tags");
            System.out.println("2 - search the directories");
            System.out.println("3 - search the directories recursively");
            System.out.println("4 - show all tags");
            System.out.println("5 - add tags to files");
            System.out.println("6 - show all files from DB");
            System.out.println("7 - exit");
            s = sc.next();

            if(s.equalsIgnoreCase("1")) {
                tag.createNewTags(tagDAO);
                tagDAO.insertTagsToDb(Tag.tagsList);
            }
            else if(s.equalsIgnoreCase("2")) {
                directory.searchTheDirectory(file, false);
            }
            else if(s.equalsIgnoreCase("3")) {
                directory.searchTheDirectory(file, true);
            }
            else if(s.equalsIgnoreCase("4")) {
                System.out.println(tagDAO.getTagNames());
            }

            else if(s.equalsIgnoreCase("5")) {
                //not yet
            }

            else if(s.equalsIgnoreCase("6")) {
                myFileDAO.getFiles();
            }

            else if(s.equalsIgnoreCase("7")) {
                break;
            }

            else {
                System.err.println("wrong char");
            }
        }
    }
}
