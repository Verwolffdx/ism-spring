package com.edatwhite.smkd.entity.smkdocument;

import com.edatwhite.smkd.entity.Users;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "documents")
public class RelationalDocument {
    @Id
    private String document_id;
    private String document_code;
    private String document_name;
    private String document_path;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(name = "doctypes",
//            joinColumns = @JoinColumn(name = "document_id"),
//            inverseJoinColumns = @JoinColumn(name = "doctype_id"))
    @ManyToOne
    @JoinColumn(name = "doctype_id")
    private DocType doctype;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "favorites",
//            joinColumns = @JoinColumn(name = "document_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<RelationalDocument> favorites = new HashSet<>();

    @ManyToMany(mappedBy = "favorites")
    private Set<Users> favorites;

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

    public RelationalDocument(String document_id, String document_code, String document_name, String document_path, DocType doctype) {
        this.document_id = document_id;
        this.document_code = document_code;
        this.document_name = document_name;
        this.document_path = document_path;
        this.doctype = doctype;
    }

    public RelationalDocument(String document_id, String document_code, String document_name, String document_path, DocType doctype, Set<Users> favorites) {
        this.document_id = document_id;
        this.document_code = document_code;
        this.document_name = document_name;
        this.document_path = document_path;
        this.doctype = doctype;
        this.favorites = favorites;
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

    public DocType getDoctype() {
        return doctype;
    }

    public void setDoctype(DocType doctype) {
        this.doctype = doctype;
    }

    public Set<Users> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Users> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(Users user) {
        this.favorites.add(user);
    }

    public void deleteFavorite(Users user) {
        this.favorites.remove(user);
    }
}
