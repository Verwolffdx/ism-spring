package com.edatwhite.smkd.entity.reports;

import java.util.ArrayList;
import java.util.List;

public class DivisionForReport {
    private Long divisionId;
    private String divisionsName;
    private int familiarized;
    private int notFamiliarized;
    private List<DivisionForReport> childDivisions;

    public DivisionForReport() {
    }

    public DivisionForReport(Long divisionId, String divisionsName, List<DivisionForReport> childDivisions) {
        this.divisionId = divisionId;
        this.divisionsName = divisionsName;
        this.childDivisions = childDivisions;
    }

    public DivisionForReport(Long divisionId, String divisionsName, int familiarized, int notFamiliarized, List<DivisionForReport> childDivisions) {
        this.divisionId = divisionId;
        this.divisionsName = divisionsName;
        this.familiarized = familiarized;
        this.notFamiliarized = notFamiliarized;
        this.childDivisions = childDivisions;
    }

    public DivisionForReport(String divisionsName, int familiarized, int notFamiliarized, List<DivisionForReport> childDivisions) {
        this.divisionsName = divisionsName;
        this.familiarized = familiarized;
        this.notFamiliarized = notFamiliarized;
        this.childDivisions = childDivisions;
    }

    public DivisionForReport(String divisionsName, int familiarized, int notFamiliarized) {
        this.divisionsName = divisionsName;
        this.familiarized = familiarized;
        this.notFamiliarized = notFamiliarized;
        this.childDivisions = new ArrayList<>();
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionsName() {
        return divisionsName;
    }

    public void setDivisionsName(String divisionsName) {
        this.divisionsName = divisionsName;
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

    public List<DivisionForReport> getChildDivisions() {
        return childDivisions;
    }

    public void setChildDivisions(List<DivisionForReport> childDivisions) {
        this.childDivisions = childDivisions;
    }

    public void addChildDivisions(DivisionForReport divisionForReport) {
        this.childDivisions.add(divisionForReport);
    }

    public void deleteChildDivisions(DivisionForReport divisionForReport) {
        this.childDivisions.remove(divisionForReport);
    }
}
