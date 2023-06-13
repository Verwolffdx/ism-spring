package com.edatwhite.smkd.entity.reports;

public class ReportItem {
    private String divisionName;
    private String fio;
    private boolean viewed;

    public ReportItem() {
    }



    public ReportItem(String divisionName, String fio, boolean viewed) {
        this.divisionName = divisionName;
        this.fio = fio;
        this.viewed = viewed;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
