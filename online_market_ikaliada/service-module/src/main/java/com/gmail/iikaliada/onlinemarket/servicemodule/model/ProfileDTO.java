package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.LatinLetterValidator;
import com.gmail.iikaliada.onlinemarket.servicemodule.validator.PhoneValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfileDTO {

    private Long id;
    @NotNull
    @Size(min = 2, max = 40)
    @LatinLetterValidator
    private String address;
    @NotNull
    @Size(min = 2, max = 40)
    @PhoneValidator
    private String telephone;
    private UserDTO userDTO;

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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
