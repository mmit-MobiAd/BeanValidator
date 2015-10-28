/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.Password;

import java.util.regex.Pattern;

/**
 * @author Jiren
 */
public class ValidatePassword extends ValidateBasePattern implements Validate<String, Password> {

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%?]).{8,15})";
    private final Pattern       pattern          = Pattern.compile(PASSWORD_PATTERN);

    public boolean validate(final AnnotationMetaData method, String email, Password annotation, ViolationInfoHandler validationMsges) {

        if (!validatePattern(email, pattern, annotation.mandatory())) {
            validationMsges.addMessage(method, annotation.message(), email);
            return false;
        }

        return true;


    }
}


