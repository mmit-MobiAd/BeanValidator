/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.MaxLength;

/**
 * @author Jiren
 */
public class ValidateMaxLength implements Validate<String, MaxLength> {

    public boolean validate(final AnnotationMetaData method,String value, MaxLength maxLength, ViolationInfoHandler validationMsg) {

        if (value.trim().length() > maxLength.Value()) {
            validationMsg.addMessage(method,maxLength.Message(),value.trim());
            return false;
        }

        return true;
    }
}
