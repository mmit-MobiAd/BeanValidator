/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.Email;

import java.util.regex.Pattern;

/**
 * @author Jiren
 */
public class ValidateEmail extends ValidateBasePattern implements Validate<String, Email> {

    private static final String  EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private              Pattern pattern       = Pattern.compile(EMAIL_PATTERN);

    public boolean validate(final AnnotationMetaData method,String email, Email annotation, ViolationInfoHandler validationMsges) {

        if (!validatePattern(email, pattern, annotation.mandatory())) {
            validationMsges.addMessage(method,annotation.message(),email);
            return false;
        }

        return true;


    }
}


