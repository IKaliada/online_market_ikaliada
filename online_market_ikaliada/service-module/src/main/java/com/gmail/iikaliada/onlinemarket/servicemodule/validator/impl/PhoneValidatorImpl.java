package com.gmail.iikaliada.onlinemarket.servicemodule.validator.impl;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.PhoneValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidatorImpl implements ConstraintValidator<PhoneValidator, String> {
    @Override
    public boolean isValid(String phonenumber, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^((\\+3|7|5)+([0-9]){10})$";
        return phonenumber.matches(regex);
    }
}
