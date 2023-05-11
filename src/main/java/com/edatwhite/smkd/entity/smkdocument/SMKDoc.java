package com.edatwhite.smkd.entity.smkdocument;

import com.edatwhite.smkd.helper.Indices;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = Indices.SMK_INDEX)
public class SMKDoc {
    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String code;

    @Field(type = FieldType.Text)
    private String version;

    @Field(type = FieldType.Text)
    private String date;

    @Field(type = FieldType.Nested)
    private List<Content> content;

    @Field(type = FieldType.Text)
    private List<String> appendix;

    @Field(type = FieldType.Nested)
    private List<Links> links;

    @Field(type = FieldType.Nested)
    private List<ApprovalSheet> approval_sheet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public List<String> getAppendix() {
        return appendix;
    }

    public void setAppendix(List<String> appendix) {
        this.appendix = appendix;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

    public List<ApprovalSheet> getApproval_sheet() {
        return approval_sheet;
    }

    public void setApproval_sheet(List<ApprovalSheet> approval_sheet) {
        this.approval_sheet = approval_sheet;
    }

    @Override
    public String toString() {
        return "SMKDoc{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", version='" + version + '\'' +
                ", date='" + date + '\'' +
                ", content=" + content +
                ", appendix=" + appendix +
                ", links=" + links +
                ", approval_sheet=" + approval_sheet +
                '}';
    }
}
