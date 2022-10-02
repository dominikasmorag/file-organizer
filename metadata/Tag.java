package metadata;

import fileorganizer.database.TagDAO;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Tag {
    private int id;
    private String name;
    public static Set<String> tagsList;
    private static Set<String> newTagsList;

    public Tag() {
        newTagsList = new HashSet<>();
    }

    public void createNewTags(TagDAO tagDAO) throws SQLException {
        tagsList = tagDAO.getTagNames();
        Scanner sc = new Scanner(System.in);
        String userInput;
            System.out.println("Add your tags\nif finished - type 'EXIT'");
            while(true) {
                userInput = sc.next();
                if(!userInput.equalsIgnoreCase("EXIT") && !tagsList.contains(userInput.toLowerCase())) {
                    newTagsList.add(userInput.toLowerCase());
                }
                else if(userInput.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            try {
                tagDAO.insertTagsToDb(newTagsList);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
