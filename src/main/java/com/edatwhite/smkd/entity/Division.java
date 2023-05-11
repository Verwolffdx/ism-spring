package com.edatwhite.smkd.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Division {
    @Id
    private Long division_id;

    private String division_name;

    private Long parent_id;

    public Division() {
    }

    public Division(Long division_id, String division_name, Long parent_id) {
        this.division_id = division_id;
        this.division_name = division_name;
        this.parent_id = parent_id;
    }

    public Long getDivision_id() {
        return division_id;
    }

    public void setDivision_id(Long division_id) {
        this.division_id = division_id;
    }

    public String getDivision_name() {
        return division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }
}
