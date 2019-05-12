package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.UserRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalDatabaseStateException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalFormatStatementRepositoryException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl extends ConnectionRepositoryImpl implements UserRepository {

    private final static Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String DATABASE_STATE_MESSAGE = "Database exception during getting user";
    private static final String STATEMENT_REPOSITORY_MESSAGE = "Cannot execute SQL query ";
    private static final int LIMIT = 10;

    @Override
    public User getUsersByUsername(Connection connection, String email) {
        String userQuery = "SELECT u.id, u.email, u.password, u.role_id, r.name as role_name FROM user u JOIN role r " +
                "ON u.role_id = r.id WHERE email = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userQuery)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return getUserForLogin(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + userQuery);
        }
        return null;
    }

    @Override
    public List<User> getUsers(Connection connection, int pageSize) {
        List<User> users = new ArrayList<>();
        String userQuery = "SELECT u.*, r.name as role_name  FROM user u JOIN role r ON u.role_id = r.id ORDER BY u.email LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userQuery)) {
            preparedStatement.setInt(1, LIMIT);
            preparedStatement.setInt(2, (pageSize - 1) * LIMIT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + userQuery);
        }
    }

    @Override
    public void deleteById(Connection connection, Long id) {
        String deleteQuery = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + deleteQuery);
        }
    }

    @Override
    public String updatePassword(Connection connection, User user) {
        String updatePasswordQuery = "UPDATE user SET password = ? where email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updatePasswordQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getString(1);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + updatePasswordQuery);
        }
        return null;
    }

    @Override
    public void addUser(Connection connection, User user) {
        String userQuery = "INSERT INTO user (name, lastname, middlename, email, password, role_id) VALUES (?, ?, ?, ?, ?, (SELECT r.id FROM role r WHERE r.name = ?))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userQuery)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getMiddlename());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + userQuery);
        }
    }

    @Override
    public void updateUsersRole(Connection connection, Long id, String roleName) {
        String updateUsersRoleQuery = "UPDATE user SET role_id = (SELECT id FROM role where name = ?) WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateUsersRoleQuery)) {
            preparedStatement.setString(1, roleName);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + updateUsersRoleQuery);
        }
    }

    @Override
    public User getUserById(Connection connection, Long id) {
        String userQuery = "SELECT u.*, r.name as role_name FROM user u JOIN role r ON u.role_id = r.id where u.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + userQuery);
        }
        return null;
    }


    @Override
    public int getTotalPagesForUser(Connection connection) {
        String countQuery = "SELECT COUNT(*) FROM user";
        int pagesNumber = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int amountRows = resultSet.getInt(1);
                pagesNumber = amountRows / LIMIT;
                if (amountRows % LIMIT > 0) {
                    pagesNumber += 1;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + countQuery);
        }
        return pagesNumber;
    }

    private User getUserForLogin(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            Role role = new Role();
            role.setId(resultSet.getLong("role_id"));
            role.setName(resultSet.getString("role_name"));
            user.setRole(role);
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalDatabaseStateException(DATABASE_STATE_MESSAGE);
        }
    }

    private User getUser(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLastname(resultSet.getString("lastname"));
            user.setMiddlename(resultSet.getString("middlename"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            Role role = new Role();
            role.setId(resultSet.getLong("role_id"));
            role.setName(resultSet.getString("role_name"));
            user.setRole(role);
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalDatabaseStateException(DATABASE_STATE_MESSAGE);
        }
    }
}
