package com.gmail.iikaliada.onlinemarket.springbootmodule.validation;

import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidation implements Validator {

    private final UserService userService;

    public UserValidation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> userClass) {
        return UserDTO.class.equals(userClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Long id = (Long) o;
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO.getEmail().equals("admin@admin.com")) {
            errors.rejectValue("email", "failed.update.message");
            errors.addAllErrors(errors);
        }
    }
}
