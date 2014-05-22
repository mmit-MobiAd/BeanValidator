/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.NotEmpty;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jiren
 */
public class ValidateNotEmpty implements Validate<Object, NotEmpty> {

    public boolean validate(final AnnotationMetaData method, Object value, NotEmpty annotation, ViolationInfoHandler validationMsges) {

        if (value == null) {
            validationMsges.addMessage(method, annotation.message(), value);
            return false;
        }

        if (value instanceof String) {
            final String valueAsString = (String) value;
            if (valueAsString.trim().length() == 0) {
                validationMsges.addMessage(method, annotation.message(), valueAsString);
                return false;
            }
        } else if (value instanceof Collection) {
            final Collection valueAsCollection = (Collection) value;
            if (valueAsCollection.isEmpty()) {
                validationMsges.addMessage(method, annotation.message(), valueAsCollection);
                return false;
            }
        } else if (value instanceof Map) {
            final Map valueAsMap = (Map) value;
            if (valueAsMap.isEmpty()) {
                validationMsges.addMessage(method, annotation.message(), valueAsMap);
                return false;
            }
        } else {
            validationMsges.addMessage(method, annotation.message(), value);
            return false;
        }

        return true;

    }

}
