package com.gmail.iikaliada.onlinemarket.servicemodule.validator.impl;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[-[a-zA-Z0-9].]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
        return email.matches(regex);
    }
}
