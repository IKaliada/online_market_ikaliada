package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Profile;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ProfileConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterImpl implements ProfileConverter {

    private final UserConverter userConverter;

    public ProfileConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public Profile fromProfileDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setId(profileDTO.getId());
        profile.setAddress(profileDTO.getAddress());
        profile.setTelephone(profileDTO.getTelephone());
        profile.setUser(userConverter.fromUserDTO(profileDTO.getUserDTO()));
        return profile;
    }

    @Override
    public ProfileDTO toProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setTelephone(profile.getTelephone());
        profileDTO.setUserDTO(userConverter.toUserDTO(profile.getUser()));
        return profileDTO;
    }
}
