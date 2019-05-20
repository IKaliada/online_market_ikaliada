package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ProfileRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Profile;
import com.gmail.iikaliada.onlinemarket.servicemodule.ProfileService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ProfileConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;
    private final UserConverter userConverter;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileConverter profileConverter, UserConverter userConverter) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public void updateProfile(ProfileDTO profileDTO) {
//        profileDTO = getProfileById(profileDTO.getId());
        profileDTO.getUserDTO().setRole(getProfileById(profileDTO.getId()).getUserDTO().getRole());
        profileDTO.getUserDTO().setEmail(getProfileById(profileDTO.getId()).getUserDTO().getEmail());
        profileDTO.getUserDTO().setMiddlename(getProfileById(profileDTO.getId()).getUserDTO().getMiddlename());
        Profile profile = profileConverter.fromProfileDTO(profileDTO);
        profile.setUser(userConverter.fromUserDTO(profileDTO.getUserDTO()));
        profileRepository.merge(profile);
    }

    @Override
    @Transactional
    public ProfileDTO getProfileById(Long id) {
        Profile profile = profileRepository.findById(id);
        return profileConverter.toProfileDTO(profile);
    }
}
