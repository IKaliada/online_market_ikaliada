package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Profile;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ProfileConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterImpl implements ProfileConverter {

    @Override
    public Profile fromProfileDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setId(profileDTO.getId());
        profile.setAddress(profileDTO.getAddress());
        profile.setTelephone(profileDTO.getTelephone());
        return profile;
    }

    @Override
    public ProfileDTO toProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setTelephone(profile.getTelephone());
        return profileDTO;
    }
}
