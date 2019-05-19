package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ProfileRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Profile;
import com.gmail.iikaliada.onlinemarket.servicemodule.ProfileService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ProfileConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileConverter profileConverter) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
    }

    @Override
    @Transactional
    public void updateProfile(ProfileDTO profileDTO) {
        Profile profile = profileConverter.fromProfileDTO(profileDTO);
        profileRepository.merge(profile);
    }

    @Override
    @Transactional
    public ProfileDTO getProfileById(Long id) {
        Profile profile = profileRepository.findById(id);
        return profileConverter.toProfileDTO(profile);
    }
}
