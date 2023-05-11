package com.edatwhite.smkd.entity.smkdocument;

public class Links {
    private String link_name;
    private String link;

    public Links() {
    }

    public Links(String link_name, String link) {
        this.link_name = link_name;
        this.link = link;
    }

    public String getLink_name() {
        return link_name;
    }

    public void setLink_name(String link_name) {
        this.link_name = link_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Links{" +
                "link_name='" + link_name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
