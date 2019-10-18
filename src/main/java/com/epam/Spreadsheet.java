package com.epam;

public class Spreadsheet {

    private String link;
    private String path;

    public String getFormat() {
        return ".xlsx";
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPath() {
        // checking the last character in a string
        if (!path.endsWith("/")) {
            return path + "/";
        }
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
