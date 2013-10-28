/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.Pattern;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jiren
 */
public class ValidatePattern implements Validate<String, Pattern> {

    public boolean validate(final AnnotationMetaData method,String value, Pattern annotation, ViolationInfoHandler validationMsges) {

        try {

            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(value);

            if (!pattern.matcher(value).matches()) {
                validationMsges.addMessage(method,annotation.message(),value);
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(ValidatePattern.class.getName()).log(Level.SEVERE, null, ex);
        }


        return true;
    }
}
