package com.gmail.iikaliada.onlinemarket.servicemodule.validator;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.impl.EmailValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidatorImpl.class)
public @interface EmailValidator {
    String message() default "{email.not.correct}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
