package com.edatwhite.smkd.entity.smkdocument;

import javax.persistence.*;

@Entity(name = "doc_type")
public class DocType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctype_id")
    private Long id;
    @Column(name = "doctype_name")
    private String name;
    @Column(name = "doctype_sign")
    private String sign;

    public DocType() {
    }

    public DocType(Long doctypeId, String doctypeName) {
        this.id = doctypeId;
        this.name = doctypeName;
    }

    public DocType(Long doctypeId, String doctypeName, String doctypeSign) {
        this.id = doctypeId;
        this.name = doctypeName;
        this.sign = doctypeSign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
