package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.AuthenticatedUserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForOrderDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForProfileDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForUiDTO;

public interface UserConverter {

    LoginDTO toLoginDTO(User user);

    UserDTO toUserDTO(User user);

    User fromUserDTO(UserDTO userDTO);

    UserForUiDTO toUserForUiDTO(User user);

    User fromUserForUiDTO(UserForUiDTO userForUiDTO);

    UserForProfileDTO toUserForProfileDTO(User user);

    User fromUserForArticleDTO(AuthenticatedUserDTO authenticatedUserDTO);

    AuthenticatedUserDTO toAuthenticatedUserDTO(User user);

    UserForOrderDTO toUserForOrderDTO(User users);

    User fromUserForOrderDTO(UserForOrderDTO userForOrderDTO);
}
