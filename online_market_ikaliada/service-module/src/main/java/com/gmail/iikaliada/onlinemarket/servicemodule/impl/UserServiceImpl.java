package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.UserRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.exception.NotValidUserException;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.ADMIN_AUTHORITY_CONSTANT;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
    @Transactional
    public LoginDTO getUsersByUsername(String username) {
        User user = userRepository.getUsersByUsername(username);
        return userConverter.toLoginDTO(user);
    }

    @Override
    @Transactional
    public UserForArticleDTO getUserForArticle(String username) {
        User user = userRepository.getUsersByUsername(username);
        return userConverter.toUserForArticleDTO(user);
    }

    @Override
    @Transactional
    public List<UserDTO> getUsers(int currentPage) {
        int offset = ((LIMIT * currentPage));
        currentPage = LIMIT * (currentPage - 1);
        List<User> users = userRepository.getUsers(currentPage, offset);
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
    @Transactional
    public void deleteUserById(List<Long> ids) {
        for (Long id : ids) {
            User user = userRepository.findById(id);
            validateUser(user);
            userRepository.removeById(id);
        }
    }

    @Override
    @Transactional
    public void updateUsersRole(Long id, Long roleId) {
        Role role = new Role();
        role.setId(roleId);
        User user = userRepository.findById(id);
        user.setRole(role);
        userRepository.merge(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(Long id) {
        String userPassword = String.valueOf(UUID.randomUUID());
        User user = userRepository.findById(id);
        user.setPassword(new BCryptPasswordEncoder().encode(userPassword));
        userRepository.merge(user);
        logger.info(userPassword);
        mailSender.send(user.getEmail(), user.getEmail(), SUCCESS_MESSAGE_AFTER_UPDATING + " " + userPassword);
    }

    @Override
    @Transactional
    public int getTotalPages() {
        int totalEntities = userRepository.getCountOfEntities();
        return getPagesNumber(totalEntities);
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        return userConverter.toUserDTO(user);
    }

    private int getPagesNumber(int totalEntities) {
        int pagesNumber = totalEntities / LIMIT;
        if (totalEntities % LIMIT > 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }

    private void validateUser(User user) {
        if (user.getRole().getName().equals(ADMIN_AUTHORITY_CONSTANT)) {
            throw new NotValidUserException("Not allowed delete this user");
        }
    }
}
