package com.edatwhite.smkd.entity.reports;

import java.util.ArrayList;
import java.util.List;

public class DocumentForReport {
    private String documentCode;
    private int familiarized;
    private int notFamiliarized;
    private List<DivisionForReport> familiarizationDivisions;

    public DocumentForReport() {
    }

    public DocumentForReport(String documentCode, int familiarized, int notFamiliarized, List<DivisionForReport> familiarizationDivisions) {
        this.documentCode = documentCode;
        this.familiarized = familiarized;
        this.notFamiliarized = notFamiliarized;
        this.familiarizationDivisions = familiarizationDivisions;
    }

    public DocumentForReport(String documentCode, int familiarized, int notFamiliarized) {
        this.documentCode = documentCode;
        this.familiarized = familiarized;
        this.notFamiliarized = notFamiliarized;
        this.familiarizationDivisions = new ArrayList<>();
    }



    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public int getFamiliarized() {
        return familiarized;
    }

    public void setFamiliarized(int familiarized) {
        this.familiarized = familiarized;
    }

    public int getNotFamiliarized() {
        return notFamiliarized;
    }

    public void setNotFamiliarized(int notFamiliarized) {
        this.notFamiliarized = notFamiliarized;
    }

    public List<DivisionForReport> getFamiliarizationDivisions() {
        return familiarizationDivisions;
    }

    public void setFamiliarizationDivisions(List<DivisionForReport> familiarizationDivisions) {
        this.familiarizationDivisions = familiarizationDivisions;
    }
}
