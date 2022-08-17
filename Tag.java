import fileorganizer.database.DAO;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Tag {
    private DAO dao;
    static Set<String> tagsList;
    private Set<String> newTagsList;

    Tag(DAO dao) throws SQLException {
        newTagsList = new HashSet<>();
        this.dao=dao;
        tagsList = dao.getTagsFromDb();
    }

    public void addTags() {
        Scanner sc = new Scanner(System.in);
        String userInput = "";
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
                dao.insertTags(newTagsList);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
