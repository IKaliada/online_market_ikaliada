package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForUiDTO;

public interface UserConverter {

    LoginDTO toLoginDTO(User user);

    User fromLoginDTO(LoginDTO loginDTO);

    UserDTO toUserDTO(User user);

    User fromUserDTO(UserDTO userDTO);

    UserForUiDTO toUserForUiDTO(User user);

    User fromUserForUiDTO(UserForUiDTO userForUiDTO);
}
