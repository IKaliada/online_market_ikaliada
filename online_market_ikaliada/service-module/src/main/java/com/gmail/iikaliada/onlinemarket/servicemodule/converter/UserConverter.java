package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.UserForReview;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForReviewDTO;

public interface UserConverter {

    LoginDTO toLoginDTO(User user);

    User fromLoginDTO(LoginDTO loginDTO);

    UserDTO toUserDTO(User user);

    User fromUserDTO(UserDTO userDTO);

    UserForReview fromUserForCommentDTO(UserForReviewDTO userForReviewDTO);

    UserForReviewDTO toUserForCommentDTO(UserForReview userForComment);
}
