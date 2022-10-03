package metadata;

import fileorganizer.database.MyFileDAO;
import fileorganizer.database.PathWithTagDAO;
import fileorganizer.database.TagDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;

public class PathWithTag {
    private final String pathName;
    private final String tagName;
    public static HashSet<PathWithTag> pathWithTagSet;
    public static HashSet<PathWithTag> newPathWithTagSet;

    public PathWithTag(String pathName, String tagName) {
        this.pathName = pathName;
        this.tagName = tagName;
    }

    public static void combinePathWithTag(Connection connection) throws SQLException {
        PathWithTagDAO pathWithTagDAO = new PathWithTagDAO(connection);
        HashSet<String> tagNames = new TagDAO(connection).getTagNames();
        HashSet<String> myFileNames = new MyFileDAO(connection).getPathNames();

        pathWithTagSet = pathWithTagDAO.getDataAsString();
        newPathWithTagSet = new HashSet<>();

        for(String pathName : myFileNames) {
                askForCombination(pathName, tagNames);
        }

            pathWithTagDAO.insertFileWithTags(newPathWithTagSet);

    }

    private static void askForCombination(String pathName, HashSet<String> tagNames) {
        HashSet<PathWithTag> tempSet = new HashSet<>();
        HashSet<String> tempTagName = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        String s;
        System.out.println("Available tags: " + tagNames);
        System.out.println("Enter the tags you want to add to this path: " + pathName);
        while(true) {
            s = sc.next().toLowerCase();
            if(s.equalsIgnoreCase("next")) {
                break;
            }
            else if (tagNames.contains(s)) {
                if(!tempTagName.contains(s)) {
                    tempTagName.add(s);
                    PathWithTag temp = new PathWithTag(pathName, s);
                    tempSet.add(temp);
                    System.out.println("Tag added! You can add another one or type NEXT to add tags to next file");
                }
                else {
                    System.out.println("this tag is already added");
                }
            }
            else {
                System.out.println("there is no tag with that name");
            }
        }
            newPathWithTagSet.addAll(tempSet);
    }

    public String getPathName() {
        return pathName;
    }

    public String getTagName() {
        return tagName;
    }

    public String toString() {
        return "File: " + pathName + ", tag: " +tagName + "\n";
    }

}
