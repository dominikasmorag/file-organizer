package metadata;

import java.io.File;

public class MyFile extends File {
    private int id;

    public MyFile(String pathname) {
        super(pathname);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "ID: " + getId() + ", Path name: " + getPath() + " \n";
    }
}
