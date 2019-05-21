package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.LatinLetterValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfileDTO {

    private Long id;
    @NotNull
    @Size(min = 2, max = 20)
    @LatinLetterValidator
    private String address;
    @NotNull
    @Size(min = 13, max = 14)
    private String telephone;
    private UserForProfileDTO userForProfileDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public UserForProfileDTO getUserForProfileDTO() {
        return userForProfileDTO;
    }

    public void setUserForProfileDTO(UserForProfileDTO userForProfileDTO) {
        this.userForProfileDTO = userForProfileDTO;
    }
}