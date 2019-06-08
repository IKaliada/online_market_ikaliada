package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.PhoneValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProfileDTO {

    private Long id;
    @NotNull
    @Size(min = 2, max = 40, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z0-9., ]+$", message = "{letter.not.correct}")
    private String address;
    @NotNull
    @PhoneValidator
    private String telephone;

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
}
