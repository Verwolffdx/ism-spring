package com.edatwhite.smkd.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class DocumentWrapper {
    private String name;
    private String title;

    public DocumentWrapper() {
    }

    public DocumentWrapper(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
