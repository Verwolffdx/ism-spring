package com.edatwhite.smkd.entity.smkdocument;

import com.edatwhite.smkd.helper.Indices;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Document(indexName = Indices.SMK_INDEX)
public class SMKDoc {

    @Id
    @Field(type = FieldType.Text)
//    @GeneratedValue(generator = "sequence-generator")
//    @GenericGenerator(
//            name = "sequence-generator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "user_sequence"),
//                    @Parameter(name = "initial_value", value = "4"),
//                    @Parameter(name = "increment_size", value = "1")
//            }
//    )
    private String id;
//    private UUID id = UUID.randomUUID();

    @Field(type = FieldType.Text)
    private String name;


    @Field(type = FieldType.Text)
    private String code;

    @Field(type = FieldType.Text)
    private String _class;

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

    public SMKDoc() {
    }

    public SMKDoc(String name, String code, String version, String date, List<Content> content, List<String> appendix, List<Links> links, List<ApprovalSheet> approval_sheet) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.code = code;
        this.version = version;
        this.date = date;
        this.content = content;
        this.appendix = appendix;
        this.links = links;
        this.approval_sheet = approval_sheet;
    }

    public SMKDoc(String id, String name, String code, String _class, String version, String date, List<Content> content, List<String> appendix, List<Links> links, List<ApprovalSheet> approval_sheet) {
        this.id = id;
        this.name = name;
        this.code = code;
        this._class = _class;
        this.version = version;
        this.date = date;
        this.content = content;
        this.appendix = appendix;
        this.links = links;
        this.approval_sheet = approval_sheet;
    }

//    public SMKDoc(UUID id, String name, String code, String _class, String version, String date, List<Content> content, List<String> appendix, List<Links> links) {
//        this.id = id;
//        this.name = name;
//        this.code = code;
//        this._class = _class;
//        this.version = version;
//        this.date = date;
//        this.content = content;
//        this.appendix = appendix;
//        this.links = links;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }

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

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
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
