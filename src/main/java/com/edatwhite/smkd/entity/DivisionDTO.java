package com.edatwhite.smkd.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DivisionDTO {
    private Long division_id;
    private String division_name;
    private List<DivisionDTO> children;

    public DivisionDTO() {
    }

    public DivisionDTO(Long division_id, String division_name) {
        this.division_id = division_id;
        this.division_name = division_name;
        this.children = new ArrayList<>();
    }

    public DivisionDTO(Long division_id, String division_name, List<DivisionDTO> children) {
        this.division_id = division_id;
        this.division_name = division_name;
        this.children = children;
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

    public List<DivisionDTO> getChildren() {
        return children;
    }

    public void setChildren(List<DivisionDTO> children) {
        this.children = children;

    }

    public void addChild(DivisionDTO child) {
        this.children.add(child);
    }
}
