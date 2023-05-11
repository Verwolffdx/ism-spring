package com.edatwhite.smkd.entity.smkdocument;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class ApprovalSheet {

    private String fio;
    private String position;
    private String type_of_approval;

    public ApprovalSheet() {
    }

    public ApprovalSheet(String fio, String position, String type_of_approval) {
        this.fio = fio;
        this.position = position;
        this.type_of_approval = type_of_approval;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType_of_approval() {
        return type_of_approval;
    }

    public void setType_of_approval(String type_of_approval) {
        this.type_of_approval = type_of_approval;
    }

    @Override
    public String toString() {
        return "ApprovalSheet{" +
                "fio='" + fio + '\'' +
                ", position='" + position + '\'' +
                ", type_of_approval='" + type_of_approval + '\'' +
                '}';
    }
}
