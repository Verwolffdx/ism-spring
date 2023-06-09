package com.edatwhite.smkd.entity.reports;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FamiliarizationReport {
    Set<DocTypeForReport> docTypesForReport;

    public FamiliarizationReport() {
        this.docTypesForReport = new HashSet<>();
    }

    public FamiliarizationReport(Set<DocTypeForReport> docTypesForReport) {
        this.docTypesForReport = docTypesForReport;
    }

    public Set<DocTypeForReport> getDocTypesForReport() {
        return docTypesForReport;
    }

    public void setDocTypesForReport(Set<DocTypeForReport> docTypesForReport) {
        this.docTypesForReport = docTypesForReport;
    }

    public void addDocTypeForReport(DocTypeForReport docTypeForReport) {
        this.docTypesForReport.add(docTypeForReport);
    }

    public void deleteDocTypeForReport(DocTypeForReport docTypeForReport) {
        this.docTypesForReport.remove(docTypeForReport);
    }
}
