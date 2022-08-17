import fileorganizer.database.DAO;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

class ResultFile {
    DAO dao;
    private LinkedList<Tag> tagsList;
    static ArrayList<File> fileList;
    private static ArrayList<File> dirList;

    ResultFile(DAO dao) {
        this.dao = dao;
        tagsList = new LinkedList<>();
        fileList = new ArrayList<>();
        dirList = new ArrayList<>();
    }

    public void searchTheDirectory(File file, boolean searchRecursively) throws SQLException {
        try {
            searchTheDir(file, searchRecursively);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        dao.insertFiles(ResultFile.fileList);
    }

    private void searchTheDir(File file, boolean searchRecursively) throws SQLException {
        File[] files = file.listFiles();
        for(File f : files) {
            if(f.isDirectory()) {
                dirList.add(f);
                if(searchRecursively) {
                    searchTheDirectory(f, true);
                }
            }
            else {
                fileList.add(f);
            }
        }
    }

    public void addTagsToFiles() throws SQLException {
        Set<String> tagSet = dao.getTagsFromDb();
        ArrayList<File> pathsFromDb = dao.getFilesFromDb();
        Scanner sc = new Scanner(System.in);
        String userInput = "";
            for(File f : pathsFromDb) {
                System.out.println(f.getPath());
                System.out.println("Add tags to your file (space after each)\ntype exit to exit");
                System.out.println(tagSet);
                userInput = sc.next();
                if(userInput.equalsIgnoreCase("exit")) {
                    break;
                }
            }
    }
}
