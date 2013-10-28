/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.NotNull;

/**
 * @author Jiren
 */
public class ValidateNotNull implements Validate<Object, NotNull> {

    public boolean validate(final AnnotationMetaData method,Object value, NotNull annotation, ViolationInfoHandler validationMsges) {

        if (value == null) {
            validationMsges.addMessage(method,annotation.message(),value);
            return false;
        }

        return true;
    }

}
