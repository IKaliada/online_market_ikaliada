package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;

import java.util.List;

public interface UserService {

    LoginDTO getUsersByUsername(String username);

    List<UserDTO> getUsers(int pageSize);

    void add(UserDTO userDTO);

    void deleteUserById(List<Long> id);

    String updateUsersRole(Long id, String roleName);

    void updateUserPassword(String email);

    int getTotalPages();
}
