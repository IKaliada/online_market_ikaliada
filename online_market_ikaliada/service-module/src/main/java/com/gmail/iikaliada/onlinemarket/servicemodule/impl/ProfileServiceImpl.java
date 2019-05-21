package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ProfileRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.ProfileService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ProfileConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForProfileDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public UserForProfileDTO updateProfile(UserForProfileDTO userForProfileDTO) {
        User user = profileRepository.findById(userForProfileDTO.getId());
        reSetUpUser(userForProfileDTO, user);
        return userConverter.toUserForProfileDTO(profileRepository.merge(user));
    }

    @Override
    @Transactional
    public UserForProfileDTO getProfileById(Long id) {
        User user = profileRepository.findById(id);
        return userConverter.toUserForProfileDTO(user);
    }

    private void reSetUpUser(UserForProfileDTO userForProfileDTO, User user) {
        user.setId(userForProfileDTO.getId());
        user.setName(userForProfileDTO.getName());
        user.setLastname(userForProfileDTO.getLastname());
        if (!user.getPassword().equals(userForProfileDTO.getPassword())){
            user.setPassword(new BCryptPasswordEncoder().encode(userForProfileDTO.getPassword()));
        }else {
            user.setPassword(userForProfileDTO.getPassword());
        }
        user.setProfile(profileConverter.fromProfileDTO(userForProfileDTO.getProfileDTO()));
    }
}
