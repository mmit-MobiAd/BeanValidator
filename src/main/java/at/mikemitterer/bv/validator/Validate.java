/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;

import java.lang.annotation.Annotation;

/**
 *
 * @author Jiren
 */
public interface Validate<T,A extends Annotation> {

    public boolean  validate(final AnnotationMetaData annotationMetaData,T value, A annotation, ViolationInfoHandler validationMsges);
               
}
