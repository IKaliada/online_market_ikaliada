package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.AuthenticatedUserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface UserService {

    LoginDTO getUsersByUsername(String username);

    AuthenticatedUserDTO getAuthenticatedUser(String username);

    List<UserDTO> getUsers(int pageSize);

    void add(UserDTO userDTO) throws ConstraintViolationException;

    void deleteUserById(List<Long> id);

    void updateUsersRole(Long id, Long roleId);

    void updateUserPassword(Long id);

    int getTotalPages();

    UserDTO getUserById(Long id);

    List<UserDTO> getUsersForApi();
}
