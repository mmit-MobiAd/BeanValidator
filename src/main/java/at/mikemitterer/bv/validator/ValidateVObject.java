/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.*;
import at.mikemitterer.bv.constraints.VObject;

import java.util.List;

/**
 * Validates Object with validation annoations.
 *
 * Mandatory = false : if it is null or empty return true. else check object.
 * Mandatory = true  : if it is null return false else check for pattern
 *
 * @author Mike Mitterer
 */
public class ValidateVObject implements Validate<Object, VObject> {

    public boolean validate(final AnnotationMetaData annotationMetaData, Object value, VObject annotation, ViolationInfoHandler violationInfoHandler) {

        if (value == null) {
            if (annotation.mandatory()) {
                violationInfoHandler.addMessageForNullPointer(annotationMetaData, annotation.message());
                return false;
            }
            return true;
        }

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();
        final List<ViolationInfo<Object>> violationInfos = beanValidator.validate(value);

        if (violationInfos.size() > 0) {
            for (final ViolationInfo<Object> violationInfo : violationInfos) {
                violationInfoHandler.addMessage(violationInfo.getMethodName(), violationInfo.getMessage(), violationInfo.getInvalidValue());
            }
            return false;
        }
        return true;
    }
}
