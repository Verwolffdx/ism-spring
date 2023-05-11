package com.edatwhite.smkd.entity;

import javax.persistence.*;

@Entity
public class Roles {
    @Id
    private Long role_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private ERole name;

    public Roles() {
    }

    public Roles(Long role_id, ERole name) {
        this.role_id = role_id;
        this.name = name;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
