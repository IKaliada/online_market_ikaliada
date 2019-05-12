package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends ConnectionRepository {

    User getUsersByUsername(Connection connection, String username);

    List<User> getUsers(Connection connection, int pageSize);

    void deleteById(Connection connection, Long id);

    String updatePassword(Connection connection, User user);

    void addUser(Connection connection, User user);

    void updateUsersRole(Connection connection, Long id, String roleName);

    User getUserById(Connection connection, Long id);

    int getTotalPagesForUser(Connection connection);
}
