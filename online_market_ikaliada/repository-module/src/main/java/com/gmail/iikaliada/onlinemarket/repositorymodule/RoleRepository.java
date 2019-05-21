package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleRepository extends GenericRepository<Long, Role> {
    List<Role> getRoles(Connection connection);
}

