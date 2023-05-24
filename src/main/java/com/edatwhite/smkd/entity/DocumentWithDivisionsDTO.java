package com.edatwhite.smkd.entity;

import com.edatwhite.smkd.entity.smkdocument.SMKDoc;

import java.util.Set;

public class DocumentWithDivisionsDTO {
    private Long user_id;
    private SMKDoc document;
    private Set<Long> divisions;

    public DocumentWithDivisionsDTO() {
    }

    public DocumentWithDivisionsDTO(SMKDoc document, Set<Long> divisions) {
        this.document = document;
        this.divisions = divisions;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public SMKDoc getDocument() {
        return document;
    }

    public void setDocument(SMKDoc document) {
        this.document = document;
    }

    public Set<Long> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<Long> divisions) {
        this.divisions = divisions;
    }
}
