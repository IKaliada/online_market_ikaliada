package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ProfileConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.RoleConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.AuthenticatedUserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForOrderDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForProfileDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForUiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    private final RoleConverter roleConverter;
    private final ProfileConverter profileConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter, ProfileConverter profileConverter) {
        this.roleConverter = roleConverter;
        this.profileConverter = profileConverter;
    }

    @Override
    public LoginDTO toLoginDTO(User user) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(user.getId());
        loginDTO.setPassword(user.getPassword());
        loginDTO.setEmail(user.getEmail());
        loginDTO.setRoleDTO(roleConverter.toRoleDTO(user.getRole()));
        return loginDTO;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setMiddlename(user.getMiddlename());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(roleConverter.toRoleDTO(user.getRole()));
        return userDTO;
    }

    @Override
    public User fromUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setMiddlename(userDTO.getMiddlename());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(roleConverter.fromRoleDTO(userDTO.getRole()));
        return user;
    }

    @Override
    public UserForUiDTO toUserForUiDTO(User user) {
        UserForUiDTO userForUiDTO = new UserForUiDTO();
        userForUiDTO.setId(user.getId());
        userForUiDTO.setName(user.getName());
        userForUiDTO.setLastname(user.getLastname());
        return userForUiDTO;
    }

    @Override
    public User fromUserForUiDTO(UserForUiDTO userForUiDTO) {
        User user = new User();
        user.setId(userForUiDTO.getId());
        return user;
    }

    @Override
    public UserForProfileDTO toUserForProfileDTO(User user) {
        UserForProfileDTO userForProfileDTO = new UserForProfileDTO();
        userForProfileDTO.setId(user.getId());
        userForProfileDTO.setName(user.getName());
        userForProfileDTO.setMiddlename(user.getMiddlename());
        userForProfileDTO.setLastname(user.getLastname());
        userForProfileDTO.setPassword(user.getPassword());
        userForProfileDTO.setProfileDTO(profileConverter.toProfileDTO(user.getProfile()));
        return userForProfileDTO;
    }

    @Override
    public User fromUserForArticleDTO(AuthenticatedUserDTO authenticatedUserDTO) {
        User user = new User();
        user.setId(authenticatedUserDTO.getId());
        user.setEmail(authenticatedUserDTO.getEmail());
        return user;
    }

    @Override
    public AuthenticatedUserDTO toAuthenticatedUserDTO(User user) {
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
        authenticatedUserDTO.setId(user.getId());
        authenticatedUserDTO.setEmail(user.getEmail());
        return authenticatedUserDTO;
    }

    @Override
    public UserForOrderDTO toUserForOrderDTO(User user) {
        UserForOrderDTO userForOrderDTO = new UserForOrderDTO();
        userForOrderDTO.setId(user.getId());
        userForOrderDTO.setName(user.getName());
        userForOrderDTO.setLastname(user.getLastname());
        userForOrderDTO.setProfileDTO(profileConverter.toProfileDTO(user.getProfile()));
        return userForOrderDTO;
    }

    @Override
    public User fromUserForOrderDTO(UserForOrderDTO userForOrderDTO) {
        User user = new User();
        user.setId(userForOrderDTO.getId());
        return user;
    }
}
