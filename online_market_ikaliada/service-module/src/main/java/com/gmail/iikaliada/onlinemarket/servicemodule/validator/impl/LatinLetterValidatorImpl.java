package com.gmail.iikaliada.onlinemarket.servicemodule.validator.impl;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.LatinLetterValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatinLetterValidatorImpl implements ConstraintValidator<LatinLetterValidator, String> {
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[a-zA-Z]+$";
        return letter.matches(regex);
    }
}
