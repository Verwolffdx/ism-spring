package com.edatwhite.smkd.entity.smkdocument;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.List;

public class Content {

    private String chapter_title;
    private List<String> chapter;

    public Content() {
    }

    public Content(String chapter_title, List<String> chapter) {
        this.chapter_title = chapter_title;
        this.chapter = chapter;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public List<String> getChapter() {
        return chapter;
    }

    public void setChapter(List<String> chapter) {
        this.chapter = chapter;
    }

    public void addChapter(String chapter) {
        this.chapter.add(chapter);
    }

    @Override
    public String toString() {
        return "Content{" +
                "chapter_title='" + chapter_title + '\'' +
                ", chapter=" + chapter +
                '}';
    }
}
