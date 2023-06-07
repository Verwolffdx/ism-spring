package com.edatwhite.smkd.entity;

public class FavoritesTemplate {
    private Long templateId;
    private Long userId;

    public FavoritesTemplate() {
    }

    public FavoritesTemplate(Long templateId, Long userId) {
        this.templateId = templateId;
        this.userId = userId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
