package com.api.payMyBuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Controls the validation of request parameters and body
 */
public abstract class ValidationClass {

    private static final Logger logger = LogManager.getLogger(ValidationClass.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /**
     * Controls the validity of a json object
     *
     * @param object : The json object to be validated
     * @return A boolean : "True" if the object is valid, otherwise "False"
     */
    protected boolean isNotValid(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(object);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                logger.error(constraint.getRootBeanClass().getSimpleName() +
                        "." + constraint.getPropertyPath() + " " + constraint.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Controls if a string is empty
     *
     * @param string : The string to be validated
     * @return A boolean : "True" if the object is valid, otherwise "False"
     */
    protected boolean isEmpty(String string) {
        if (string.strip().isEmpty()) {
            logger.error("Error in request body");
            return true;
        } else {
            return false;
        }
    }
}
