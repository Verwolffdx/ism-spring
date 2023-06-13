package com.edatwhite.smkd.entity.reports;

import java.util.List;

public class FamReportForDocument {
    private String documentCode;

    private List<ReportItem> report;

    public FamReportForDocument() {
    }

    public FamReportForDocument(String documentName) {
        this.documentCode = documentName;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public List<ReportItem> getReport() {
        return report;
    }

    public void setReport(List<ReportItem> report) {
        this.report = report;
    }
}
