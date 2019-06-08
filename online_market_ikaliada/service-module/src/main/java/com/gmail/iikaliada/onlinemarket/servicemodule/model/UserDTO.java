package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @NotNull
    @Size(min = 2, max = 20, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{letter.not.correct}")
    private String name;
    @NotNull
    @Size(min = 2, max = 40, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{letter.not.correct}")
    private String lastname;
    @NotNull
    @Size(min = 2, max = 40, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{letter.not.correct}")
    private String middlename;
    @NotNull
    @Pattern(regexp = "^[-[a-zA-Z0-9].]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$", message = "{email.template.not.correct}")
    @Size(min = 2, max = 40, message = "{validation.size.message}")
    private String email;
    private String password;
    @NotNull
    private RoleDTO role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
