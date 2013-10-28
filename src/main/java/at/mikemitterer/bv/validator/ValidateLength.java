/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.Length;

/**
 * @author Jiren
 */
public class ValidateLength implements Validate<String, Length> {

    public boolean validate(final AnnotationMetaData method,String value, Length annotation, ViolationInfoHandler validationMsges) {

        if (value != null) {
            if (value.length() > annotation.max()) {
                validationMsges.addMessage(method,annotation.message(),value);
                return false;
            }

        } else {
            validationMsges.addMessage(method,annotation.message(),value);
            return false;
        }

        return true;
    }
}
