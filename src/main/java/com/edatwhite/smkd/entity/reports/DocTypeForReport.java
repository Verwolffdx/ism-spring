package com.edatwhite.smkd.entity.reports;

import java.util.ArrayList;
import java.util.List;

public class DocTypeForReport {
    private String docTypeName;
    private List<DocumentForReport> documentsForReport;

    public DocTypeForReport() {
    }

    public DocTypeForReport(String docTypeName) {
        this.docTypeName = docTypeName;
        this.documentsForReport = new ArrayList<>();
    }

    public DocTypeForReport(String dcoTypeName, List<DocumentForReport> documentsForReport) {
        this.docTypeName = dcoTypeName;
        this.documentsForReport = documentsForReport;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public List<DocumentForReport> getDocumentsForReport() {
        return documentsForReport;
    }

    public void setDocumentsForReport(List<DocumentForReport> documentsForReport) {
        this.documentsForReport = documentsForReport;
    }

    public void addDocumentForReport(DocumentForReport documentForReport) {
        this.documentsForReport.add(documentForReport);
    }

    public void deleteDocumentForReport(DocumentForReport documentForReport) {
        this.documentsForReport.remove(documentForReport);
    }
}
