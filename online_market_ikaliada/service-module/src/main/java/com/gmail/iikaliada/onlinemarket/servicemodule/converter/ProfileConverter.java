package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Profile;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;

public interface ProfileConverter {

    Profile fromProfileDTO(ProfileDTO profileDTO);

    ProfileDTO toProfileDTO(Profile profile);
}
