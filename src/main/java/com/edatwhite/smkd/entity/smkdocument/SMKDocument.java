package com.edatwhite.smkd.entity.smkdocument;

import com.edatwhite.smkd.helper.Indices;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = Indices.NIR_INDEX)
public class SMKDocument {

    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String code;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String version;

    @Field(type = FieldType.Text)
    private String date;

    @Field(type = FieldType.Text)
    private List<String> content;

    @Field(type = FieldType.Text)
    private List<String> appendix;

    @Field(type = FieldType.Text)
    private List<String> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<String> getAppendix() {
        return appendix;
    }

    public void setAppendix(List<String> appendix) {
        this.appendix = appendix;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
