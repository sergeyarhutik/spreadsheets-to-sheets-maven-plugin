package com.epam;

public class FileProperties {


    private String link;
    private String path;
    private String format;

    public String getFormat() {
        // checking the first (dot) character in a string
        if (!format.substring(0,0).equals(".")){
            return "." + format;
        }
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPath() {
        // checking the last character in a string
        if (!path.substring(path.length() - 1).equals("/")){
            return path + "/";
        }
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
