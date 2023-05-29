package com.edatwhite.smkd.entity;

import java.util.List;

public class TemplatesDTO {
    private List<Templates> templatesList;

    public TemplatesDTO() {
    }

    public TemplatesDTO(List<Templates> templatesList) {
        this.templatesList = templatesList;
    }

    public List<Templates> getTemplatesList() {
        return templatesList;
    }

    public void setTemplatesList(List<Templates> templatesList) {
        this.templatesList = templatesList;
    }
}
