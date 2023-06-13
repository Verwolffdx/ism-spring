package com.edatwhite.smkd.entity;

public class RelationalDocumentDTO {
    private String document_id;
    private String document_code;
    private String document_name;

    public RelationalDocumentDTO() {
    }

    public RelationalDocumentDTO(String document_id, String document_code, String document_name) {
        this.document_id = document_id;
        this.document_code = document_code;
        this.document_name = document_name;
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
}
