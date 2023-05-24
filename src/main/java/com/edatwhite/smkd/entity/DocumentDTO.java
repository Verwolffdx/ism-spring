package com.edatwhite.smkd.entity;

import com.edatwhite.smkd.entity.smkdocument.ApprovalSheet;
import com.edatwhite.smkd.entity.smkdocument.Content;
import com.edatwhite.smkd.entity.smkdocument.Links;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DocumentDTO {
    private String id;

    private String name;

    private String code;

    private String version;

    private String date;

    private List<Content> content;

    private List<String> appendix;

    private List<Links> links;

    private List<ApprovalSheet> approval_sheet;

    private Map<String, List<String>> highlightFields;

    private boolean isFavorite = false;

    private boolean isFamiliarize;

    public DocumentDTO() {
    }

    public DocumentDTO(String id, String name, String code, String version, String date, List<Content> content, List<String> appendix, List<Links> links, List<ApprovalSheet> approval_sheet) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.version = version;
        this.date = date;
        this.content = content;
        this.appendix = appendix;
        this.links = links;
        this.approval_sheet = approval_sheet;
    }

    public DocumentDTO(String id, String name, String code, String version, String date, List<Content> content, List<String> appendix, List<Links> links, List<ApprovalSheet> approval_sheet, Map<String, List<String>> highlightFields) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.version = version;
        this.date = date;
        this.content = content;
        this.appendix = appendix;
        this.links = links;
        this.approval_sheet = approval_sheet;
        this.highlightFields = highlightFields;
    }

    public DocumentDTO(String id, String name, String code, String version, String date, List<Content> content, List<String> appendix, List<Links> links, List<ApprovalSheet> approval_sheet, boolean isFavorite, boolean isFamiliarize) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.version = version;
        this.date = date;
        this.content = content;
        this.appendix = appendix;
        this.links = links;
        this.approval_sheet = approval_sheet;
        this.isFavorite = isFavorite;
        this.isFamiliarize = isFamiliarize;
    }

    public DocumentDTO(String id, String name, String code, String version, String date, List<Content> content, List<String> appendix, List<Links> links, List<ApprovalSheet> approval_sheet, Map<String, List<String>> highlightFields, boolean isFavorite, boolean isFamiliarize) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.version = version;
        this.date = date;
        this.content = content;
        this.appendix = appendix;
        this.links = links;
        this.approval_sheet = approval_sheet;
        this.highlightFields = highlightFields;
        this.isFavorite = isFavorite;
        this.isFamiliarize = isFamiliarize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public List<String> getAppendix() {
        return appendix;
    }

    public void setAppendix(List<String> appendix) {
        this.appendix = appendix;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

    public List<ApprovalSheet> getApproval_sheet() {
        return approval_sheet;
    }

    public void setApproval_sheet(List<ApprovalSheet> approval_sheet) {
        this.approval_sheet = approval_sheet;
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

    public boolean isFamiliarize() {
        return isFamiliarize;
    }

    public void setFamiliarize(boolean familiarize) {
        isFamiliarize = familiarize;
    }


}
