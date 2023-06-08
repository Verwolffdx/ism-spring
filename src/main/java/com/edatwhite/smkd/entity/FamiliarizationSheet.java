package com.edatwhite.smkd.entity;

import javax.persistence.*;

@Entity
@Table(name = "familiarization_sheet")
public class FamiliarizationSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fam_id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "document_id")
    private String documentId;
    private Boolean viewed;

    @Column(name = "fam_division")
    private Long famDivision;

    public FamiliarizationSheet() {
    }

    public FamiliarizationSheet(Long userId, String documentId, Boolean viewed) {
        this.userId = userId;
        this.documentId = documentId;
        this.viewed = viewed;
    }

    public FamiliarizationSheet(Long fam_id, Long userId, String documentId, Boolean viewed) {
        this.fam_id = fam_id;
        this.userId = userId;
        this.documentId = documentId;
        this.viewed = viewed;
    }

    public FamiliarizationSheet(Long userId, String documentId, Boolean viewed, Long famDivision) {
        this.userId = userId;
        this.documentId = documentId;
        this.viewed = viewed;
        this.famDivision = famDivision;
    }

    public Long getFam_id() {
        return fam_id;
    }

    public void setFam_id(Long fam_id) {
        this.fam_id = fam_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public Long getFamDivision() {
        return famDivision;
    }

    public void setFamDivision(Long famDivision) {
        this.famDivision = famDivision;
    }
}
