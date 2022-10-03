import fileorganizer.database.MyFileDAO;
import fileorganizer.database.PathWithTagDAO;
import fileorganizer.database.TagDAO;
import metadata.PathWithTag;
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
    private final PathWithTagDAO pathWithTagDAO;
    private final ResultFile directory;
    private final TagDAO tagDAO;

    ProgramController(Connection connection, File file) throws SQLException {
        this.connection = connection;
        this.file = file;
        tag = new Tag();
        myFileDAO = new MyFileDAO(connection);
        directory = new ResultFile(myFileDAO);
        pathWithTagDAO = new PathWithTagDAO(connection);
        tagDAO = new TagDAO(connection);
    }

    public void showMenu() throws SQLException {
        String s = "";
        while(!s.equalsIgnoreCase("9")) {
            Scanner sc = new Scanner(System.in);
            System.out.println("1 - create new tags");
            System.out.println("2 - search the directories");
            System.out.println("3 - search the directories recursively");
            System.out.println("4 - show all tags");
            System.out.println("5 - show all files from DB");
            System.out.println("6 - combine path with tags");
            System.out.println("7 - get files with tags as string");
            System.out.println("8 - Find the file with given file extension and tag");
            System.out.println("9 - exit");
            s = sc.next();

            switch (s) {
                case "1":
                    tag.createNewTags(tagDAO);
                    tagDAO.insertTagsToDb(Tag.tagsList);
                    break;
                case "2":
                    directory.searchTheDirectory(file, false);
                    break;
                case "3":
                    directory.searchTheDirectory(file, true);
                    break;
                case "4":
                    System.out.println(tagDAO.getTagNames());
                    break;
                case "5":
                    myFileDAO.getFiles();
                    break;
                case "6":
                    PathWithTag.combinePathWithTag(connection);
                    break;
                case "7":
                    System.out.println(pathWithTagDAO.getDataAsString());
                    break;
                case "8":
                    System.out.println(pathWithTagDAO.findFileWithTag());
                case "9":
                    break;
                default:
                    System.err.println("wrong char");
                    break;
            }
        }
    }
}
