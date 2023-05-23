package com.edatwhite.smkd.entity.smkdocument;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class RelationalDocument {
    @Id
    private String document_id;
    private String document_code;
    private String document_name;
    private String document_path;

    public RelationalDocument() {
    }

    public RelationalDocument(String document_id, String document_code, String document_name) {
        this.document_id = document_id;
        this.document_code = document_code;
        this.document_name = document_name;
        this.document_path = "/";
    }

    //    public RelationalDocument(String document_code, String document_name, String document_path) {
//        this.document_code = document_code;
//        this.document_name = document_name;
//        this.document_path = document_path;
//    }

    public RelationalDocument(String document_id, String document_code, String document_name, String document_path) {
        this.document_id = document_id;
        this.document_code = document_code;
        this.document_name = document_name;
        this.document_path = document_path;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getDocument_code() {
        return document_code;
    }

    public void setDocument_code(String document_code) {
        this.document_code = document_code;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getDocument_path() {
        return document_path;
    }

    public void setDocument_path(String document_path) {
        this.document_path = document_path;
    }
}
