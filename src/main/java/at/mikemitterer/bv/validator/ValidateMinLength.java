/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.MinLength;

/**
 * @author Jiren
 */
public class ValidateMinLength implements Validate<String, MinLength> {

    public boolean validate(final AnnotationMetaData method,String value, MinLength aMinLength, ViolationInfoHandler validationMsg) {

        if (value != null) {
            if (value.length() < aMinLength.value()) {
                validationMsg.addMessage(method,aMinLength.message(),value);
                return false;
            }
        } else {
            validationMsg.addMessage(method,aMinLength.message(),value);
            return false;
        }

        return true;
    }
}
