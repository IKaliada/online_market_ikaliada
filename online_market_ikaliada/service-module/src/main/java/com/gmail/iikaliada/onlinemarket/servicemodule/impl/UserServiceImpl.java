package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.UserRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.exception.ConnectionServiceStateException;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String CONNECTION_SERVICE_MESSAGE = "Cannot create connection";

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final MailSenderImpl mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, MailSenderImpl mailSender) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.mailSender = mailSender;
    }

    @Override
    public LoginDTO getUsersByUsername(String username) {
        try (Connection connection = userRepository.getConnection()) {
            LoginDTO loginDTO = null;
            try {
                connection.setAutoCommit(false);
                User user = userRepository.getUsersByUsername(connection, username);
                loginDTO = userConverter.toLoginDTO(user);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
            return loginDTO;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public List<UserDTO> getUsers(int pageSize) {
        try (Connection connection = userRepository.getConnection()) {
            List<UserDTO> userDTOS;
            try {
                connection.setAutoCommit(false);
                List<User> users = userRepository.getUsers(connection, pageSize);
                userDTOS = users.stream().map(userConverter::toUserDTO).collect(Collectors.toList());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
            return userDTOS;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public void add(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            String password = String.valueOf(UUID.randomUUID());
            try {
                User user = userConverter.fromUserDTO(userDTO);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                userRepository.addUser(connection, user);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public void deleteUserById(List<Long> ids) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                validateUserRole(ids, connection);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public String updateUsersRole(Long id, String roleName) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                validateUser(id, roleName, connection);
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
        return null;
    }

    @Override
    public void updateUserPassword(String email) {
        String userPassword = String.valueOf(UUID.randomUUID());
        String message = "Your password was updated to";
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                LoginDTO loginDTO = getUsersByUsername(email);
                User user = userConverter.fromLoginDTO(loginDTO);
                user.setPassword(new BCryptPasswordEncoder().encode(userPassword));
                userRepository.updatePassword(connection, user);
                logger.info(userPassword);
                mailSender.send(email, email, message + " " + userPassword);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public int getTotalPages() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int totalPages = userRepository.getTotalPagesForUser(connection);
                connection.commit();
                return totalPages;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    private void validateUserRole(List<Long> ids, Connection connection) {
        for (Long id : ids) {
            User user = userRepository.getUserById(connection, id);
            if (!user.getRole().getName().equals("ADMINISTRATOR")) {
                userRepository.deleteById(connection, id);
            }
        }
    }

    private void validateUser(Long id, String roleName, Connection connection) throws SQLException {
        User user = userRepository.getUserById(connection, id);
        if (!user.getEmail().equals("admin@admin.com")) {
            userRepository.updateUsersRole(connection, id, roleName);
            connection.commit();
        }
    }
}
