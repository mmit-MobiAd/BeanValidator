/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.PhoneUS;

import java.util.regex.Pattern;

/**
 * @author Jiren
 */
public class ValidatePhoneUS extends ValidateBasePattern implements Validate<String, PhoneUS> {

    private static final String  PHONE_PATTERN = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
    private static       Pattern pattern       = Pattern.compile(PHONE_PATTERN);

    public boolean validate(final AnnotationMetaData method,String phone, PhoneUS annotation, ViolationInfoHandler validationMsges) {

        if (!validatePattern(phone, pattern, annotation.mandatory())) {
            validationMsges.addMessage(method,annotation.message(),phone);
            return false;
        }


        return true;

    }
}
