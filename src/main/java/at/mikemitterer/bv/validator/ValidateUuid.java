/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.Uuid;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jiren
 */
public class ValidateUuid implements Validate<String, Uuid> {

    public boolean validate(final AnnotationMetaData method,String value, Uuid annotation, ViolationInfoHandler validationMsges) {

        try {

            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(annotation.pattern());

            if (value == null || !pattern.matcher(value).matches()) {
                validationMsges.addMessage(method,annotation.message(),value);
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(ValidateUuid.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
}
