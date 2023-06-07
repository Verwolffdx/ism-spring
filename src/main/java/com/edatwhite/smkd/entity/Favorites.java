package com.edatwhite.smkd.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Embeddable
public class Favorites {

    private String document_id;

    private Long user_id;

    public Favorites() {
    }

    public Favorites(String document_id, Long user_id) {
        this.document_id = document_id;
        this.user_id = user_id;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
