package com.edatwhite.smkd.entity;

import javax.persistence.*;

@Entity
public class Templates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_path")
    private String templatePath;

    public Templates() {
    }

    public Templates(String documentId, String templateName, String templatePath) {
        this.documentId = documentId;
        this.templateName = templateName;
        this.templatePath = templatePath;
    }

    public Templates(Long templateId, String documentId, String templateName, String templatePath) {
        this.templateId = templateId;
        this.documentId = documentId;
        this.templateName = templateName;
        this.templatePath = templatePath;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}
