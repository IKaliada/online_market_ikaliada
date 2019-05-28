package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {

    User getUsersByUsername(String username);

    List<User> getUsers(int offset, int limit);
}
