/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.Phone;

import java.util.regex.Pattern;

/**
 * @author Jiren
 */
public class ValidatePhone extends ValidateBasePattern implements Validate<String, Phone> {

    private static final String  PHONE_PATTERN = "^\\+[0-9]{2,3}[0-9 -]{6,}$";
    private static final Pattern pattern = Pattern.compile(PHONE_PATTERN);

    public boolean validate(final AnnotationMetaData method, String phone, Phone annotation, ViolationInfoHandler validationMsges) {

        if (!validatePattern(phone, pattern, annotation.mandatory())) {
            validationMsges.addMessage(method, annotation.message(), phone);
            return false;
        }


        return true;

    }
}
