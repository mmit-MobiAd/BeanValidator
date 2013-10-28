/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.NotEmpty;

/**
 * @author Jiren
 */
public class ValidateNotEmpty implements Validate<String, NotEmpty> {

    public boolean validate(final AnnotationMetaData method,String value, NotEmpty annotation, ViolationInfoHandler validationMsges) {

        if (value == null || value.trim().length() == 0) {
            validationMsges.addMessage(method,annotation.message(),value);
            return false;
        }

        return true;

    }

}
