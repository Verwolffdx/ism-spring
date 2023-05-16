package com.edatwhite.smkd.payload.request;

public class DocumentIdRequest {
    private String document_id;
    private String value;
    private Long user_id;

    public DocumentIdRequest() {
    }

    public DocumentIdRequest(String document_id, String value, Long user_id) {
        this.document_id = document_id;
        this.value = value;
        this.user_id = user_id;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
