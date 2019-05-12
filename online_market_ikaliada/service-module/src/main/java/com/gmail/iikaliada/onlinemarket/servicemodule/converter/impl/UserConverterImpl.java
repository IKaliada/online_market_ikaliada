package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.UserForReview;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.RoleConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    private final RoleConverter roleConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public LoginDTO toLoginDTO(User user) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(user.getId());
        loginDTO.setPassword(user.getPassword());
        loginDTO.setEmail(user.getEmail());
        loginDTO.setRole(user.getRole());
        return loginDTO;
    }

    @Override
    public User fromLoginDTO(LoginDTO loginDTO) {
        User user = new User();
        user.setId(loginDTO.getId());
        user.setPassword(loginDTO.getPassword());
        user.setEmail(loginDTO.getEmail());
        return user;
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
    public UserForReview fromUserForCommentDTO(UserForReviewDTO userForReviewDTO) {
        UserForReview userForComment = new UserForReview();
        userForComment.setId(userForReviewDTO.getId());
        userForComment.setName(userForReviewDTO.getName());
        userForComment.setLastname(userForReviewDTO.getLastname());
        return userForComment;
    }

    @Override
    public UserForReviewDTO toUserForCommentDTO(UserForReview userForComment) {
        UserForReviewDTO userForReviewDTO = new UserForReviewDTO();
        userForReviewDTO.setId(userForComment.getId());
        userForReviewDTO.setName(userForComment.getName());
        userForReviewDTO.setLastname(userForComment.getLastname());
        return userForReviewDTO;
    }
}
