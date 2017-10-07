package com.github.francoagarcia.social.rest.command;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public class CommandValidator {
    private final Validator validator;

    public CommandValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> T validateOrFail(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return obj;
    }

    public <T> boolean isValid(T obj) {
        return validator.validate(obj).isEmpty();
    }
}
