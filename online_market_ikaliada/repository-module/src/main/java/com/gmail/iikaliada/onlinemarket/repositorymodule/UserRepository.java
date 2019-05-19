package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {

    User getUsersByUsername(Connection connection, String username);

    void deleteById(Connection connection, Long id);

    String updatePassword(Connection connection, User user);

    void updateUsersRole(Connection connection, Long id, Long roleId);

    User getUserById(Connection connection, Long id);

    int getTotalPagesForUser(Connection connection);
}
