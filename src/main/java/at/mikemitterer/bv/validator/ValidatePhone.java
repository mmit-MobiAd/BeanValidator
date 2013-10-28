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

    private static final String  PHONE_PATTERN = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
    private static       Pattern pattern       = Pattern.compile(PHONE_PATTERN);

    public boolean validate(final AnnotationMetaData method,String phone, Phone aPhone, ViolationInfoHandler validationMsges) {

        if (!validatePattern(phone, pattern, aPhone.mandatory())) {
            validationMsges.addMessage(method,aPhone.message(),phone);
            return false;
        }


        return true;

    }
}
