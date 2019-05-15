package com.gmail.iikaliada.onlinemarket.servicemodule.validator;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.impl.LatinLetterValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LatinLetterValidatorImpl.class)
public @interface LatinLetterValidator {
    String message() default "{letter.not.correct}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
