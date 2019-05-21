package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.LatinLetterValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserForProfileDTO {
    private Long id;
    @NotNull
    @Size(min = 2, max = 20)
    @LatinLetterValidator
    private String name;
    @NotNull
    @Size(min = 2, max = 40)
    @LatinLetterValidator
    private String lastname;
    private String password;
    private ProfileDTO profileDTO;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfileDTO getProfileDTO() {
        return profileDTO;
    }

    public void setProfileDTO(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
    }
}