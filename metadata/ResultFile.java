package metadata;

import fileorganizer.database.MyFileDAO;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ResultFile {
    private final MyFileDAO fileDAO;
    private static Set<String> fileSet;
    private static Set<String> newFileSet;
    private static Set<File> dirSet;

    public ResultFile(MyFileDAO dao) {
        this.fileDAO = dao;
        newFileSet = new HashSet<>();
        dirSet = new HashSet<>();
    }

    public void searchTheDirectory(File file, boolean searchRecursively) throws SQLException {
        try {
            fileSet = fileDAO.getPathNames();
        } catch (SQLException ex) {
            ex.getMessage();
        }
        try {
            searchTheDir(file, searchRecursively);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        addToDb(newFileSet);
        newFileSet.clear();
    }

    private void searchTheDir(File file, boolean searchRecursively) throws SQLException {
        File[] files = file.listFiles();
        if(files!=null) {
            for (File f : files) {
                MyFile myFile = new MyFile(f.getPath());
                if (myFile.isDirectory()) {
                    dirSet.add(myFile);
                    if (searchRecursively) {
                        searchTheDirectory(f, true);
                    }
                } else {
                    if(!fileSet.contains(myFile.getPath())) {
                        newFileSet.add(myFile.getPath());
                    }
                }
            }
        }
    }

    private void addToDb(Set<String> set) throws SQLException {
        fileDAO.insertFiles(set);
    }

}
