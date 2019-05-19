package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.UserRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.exception.ConnectionServiceStateException;
import com.gmail.iikaliada.onlinemarket.servicemodule.exception.UserServiceTransactionRollbackedException;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.ServiceConstants.CONNECTION_SERVICE_MESSAGE;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.ServiceConstants.TRANSACTION_ROLLBACK_MESSAGE;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final static String ROLE_ADMIN_CONSTANT = "Administrator";
    private final static String SUCCESS_MESSAGE_AFTER_UPDATING = "Your password was updated to";

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
    @Transactional
    public List<UserDTO> getUsers(int pageSize) {
        int offset = ((LIMIT * pageSize));
        pageSize = pageSize - 1;
        List<User> users = userRepository.findAll(pageSize, offset);
        return users.stream()
                .map(userConverter::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        User user = userConverter.fromUserDTO(userDTO);
        String password = String.valueOf(UUID.randomUUID());
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.persist(user);
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
    public String updateUsersRole(Long id, Long roleId) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userRepository.updateUsersRole(connection, id, roleId);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceTransactionRollbackedException(TRANSACTION_ROLLBACK_MESSAGE);
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
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                LoginDTO loginDTO = getUsersByUsername(email);
                User user = userConverter.fromLoginDTO(loginDTO);
                user.setPassword(new BCryptPasswordEncoder().encode(userPassword));
                userRepository.updatePassword(connection, user);
                logger.info(userPassword);
                mailSender.send(email, email, SUCCESS_MESSAGE_AFTER_UPDATING + " " + userPassword);
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

    @Override
    public UserDTO getUserById(Long id) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserById(connection, id);
                UserDTO userDTO = userConverter.toUserDTO(user);
                connection.commit();
                return userDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceTransactionRollbackedException(TRANSACTION_ROLLBACK_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    private void validateUserRole(List<Long> ids, Connection connection) {
        for (Long id : ids) {
            User user = userRepository.getUserById(connection, id);
            if (!user.getRole().getName().equals(ROLE_ADMIN_CONSTANT)) {
                userRepository.deleteById(connection, id);
            }
        }
    }
}
