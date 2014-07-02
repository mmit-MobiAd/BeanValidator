/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.Range;

/**
 * @author Jiren
 */
public class ValidateRange implements Validate<Number, Range> {

    public boolean validate(final AnnotationMetaData method,Number number, Range annotation, ViolationInfoHandler validationMsges) {


        if (number == null) {
            validationMsges.addMessageForNullPointer(method,"Null or " + annotation.message());
            return false;
        }

        if (number instanceof Integer) {
            if (number.intValue() < annotation.start() || number.intValue() > annotation.end()) {
                validationMsges.addMessage(method, annotation.message(),number);
                return false;
            }

        } else if (number instanceof Float) {
            if (number.floatValue() < annotation.start() || number.floatValue() > annotation.end()) {
                validationMsges.addMessage(method,annotation.message(),number);
                return false;
            }
        } else {
            if (number.doubleValue() < annotation.start() || number.doubleValue() > annotation.end()) {
                validationMsges.addMessage(method,annotation.message(),number);
                return false;
            }
        }

        return true;
    }
}
