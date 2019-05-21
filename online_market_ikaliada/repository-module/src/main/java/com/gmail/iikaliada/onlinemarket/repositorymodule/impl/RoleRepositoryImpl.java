package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.RoleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalFormatStatementRepositoryException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.RepositoryConstants.STATEMENT_REPOSITORY_MESSAGE;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    private static final Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);
    private static final String ROLE_EXECUTION_MESSAGE = "Exception during execution role from database";

    @Override
    public List<Role> getRoles(Connection connection) {
        String findRoleQuery = "SELECT * FROM role";
        try (PreparedStatement preparedStatement = connection.prepareStatement(findRoleQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                Role role = getRole(resultSet);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + findRoleQuery);
        }
    }

    private Role getRole(ResultSet resultSet) {
        try {
            Role role = new Role();
            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));
            return role;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(ROLE_EXECUTION_MESSAGE);
        }
    }
}
