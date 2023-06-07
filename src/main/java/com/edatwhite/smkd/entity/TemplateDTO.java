package com.edatwhite.smkd.entity;

import java.util.List;
import java.util.Map;

public class TemplateDTO {
    private String templateId;
    private String documentId;
    private String documentCode;
    private String documentName;
    private List<String> appendix;
    private Map<String, List<String>> highlightFields;
    private boolean isFavorite = false;

    public TemplateDTO() {
    }

    public TemplateDTO(String documentId, String documentCode, String documentName, List<String> appendix, Map<String, List<String>> highlightFields) {
        this.documentId = documentId;
        this.documentCode = documentCode;
        this.documentName = documentName;
        this.appendix = appendix;
        this.highlightFields = highlightFields;
    }

    public TemplateDTO(String documentId, String documentCode, String documentName, List<String> appendix, Map<String, List<String>> highlightFields, boolean isFavorite) {
        this.documentId = documentId;
        this.documentCode = documentCode;
        this.documentName = documentName;
        this.appendix = appendix;
        this.highlightFields = highlightFields;
        this.isFavorite = isFavorite;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public List<String> getAppendix() {
        return appendix;
    }

    public void setAppendix(List<String> appendix) {
        this.appendix = appendix;
    }

    public Map<String, List<String>> getHighlightFields() {
        return highlightFields;
    }

    public void setHighlightFields(Map<String, List<String>> highlightFields) {
        this.highlightFields = highlightFields;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
