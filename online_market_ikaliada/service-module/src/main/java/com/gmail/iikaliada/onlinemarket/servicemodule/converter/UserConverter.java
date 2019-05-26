package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForProfileDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForUiDTO;

public interface UserConverter {

    LoginDTO toLoginDTO(User user);

    UserDTO toUserDTO(User user);

    User fromUserDTO(UserDTO userDTO);

    UserForUiDTO toUserForUiDTO(User user);

    User fromUserForUiDTO(UserForUiDTO userForUiDTO);

    UserForProfileDTO toUserForProfileDTO(User user);

    User fromUserForArticleDTO(UserForArticleDTO userForArticleDTO);

    UserForArticleDTO toUserForArticleDTO(User user);
}
