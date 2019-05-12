package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.EmailValidator;
import com.gmail.iikaliada.onlinemarket.servicemodule.validator.LatinLetterValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @NotNull
    @Size(min = 2, max = 20)
    @LatinLetterValidator
    private String name;
    @NotNull
    @Size(min = 2, max = 40)
    @LatinLetterValidator
    private String lastname;
    @NotNull
    @Size(min = 2, max = 40)
    @LatinLetterValidator
    private String middlename;
    @NotNull
    @EmailValidator
    @Size(min = 2, max = 40)
    private String email;
    private String password;
    @NotNull
    private RoleDTO role;

    public UserDTO(Long id, @NotNull @Size(min = 2, max = 20) String name,
                   @NotNull @Size(min = 2, max = 40) String lastname,
                   @NotNull @Size(min = 2, max = 40) String middlename,
                   @NotNull @Size(min = 2, max = 40) String email, String password,
                   @NotNull RoleDTO role) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.middlename = middlename;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDTO() {
    }

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

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
