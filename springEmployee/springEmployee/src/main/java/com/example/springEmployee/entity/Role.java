package com.example.springEmployee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    private String roleName;
    private boolean isActive;

    @OneToMany(mappedBy="role",cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private List<Employee> employees;

    public Role(int roleId, String roleName, boolean isActive, List<Employee> employees) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isActive = isActive;
        this.employees = employees;
    }

    public Role() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
