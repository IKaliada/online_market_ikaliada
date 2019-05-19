package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;

public interface ProfileService {

    void updateProfile(ProfileDTO profileDTO);

    ProfileDTO getProfileById(Long id);
}
