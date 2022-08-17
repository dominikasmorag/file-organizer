import fileorganizer.database.DAO;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

class ProgramController {
    private final File file;
    private final DAO dao;
    private final Tag tag;

    ProgramController(File file, DAO dao) throws SQLException {
        this.file = file;
        this.dao = dao;
        this.tag = new Tag(dao);
    }

    public void showMenu() throws SQLException {
        ResultFile directory = new ResultFile(dao);
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
                tag.addTags();
                dao.insertTags(Tag.tagsList);
            }
            else if(s.equalsIgnoreCase("2")) {
                directory.searchTheDirectory(file, false);
            }
            else if(s.equalsIgnoreCase("3")) {
                directory.searchTheDirectory(file, true);
            }
            else if(s.equalsIgnoreCase("4")) {
                System.out.println(dao.getTagsFromDb());
            }

            else if(s.equalsIgnoreCase("5")) {
                directory.addTagsToFiles();
            }

            else if(s.equalsIgnoreCase("6")) {
                System.out.println(dao.getFilesFromDb());
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