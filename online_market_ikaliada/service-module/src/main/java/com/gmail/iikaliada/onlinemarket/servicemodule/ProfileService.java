package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForProfileDTO;

public interface ProfileService {

    UserForProfileDTO updateProfile(UserForProfileDTO userForProfileDTO);

    UserForProfileDTO getProfileById(Long id);
}
