/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This bean contain Field getter method and field Annotations.
 *
 * @author Jiren
 */
public class AnnotationMetaData {

    private final Field        field;
    private       Boolean      fromField;
    private final Method       method;
    private       Annotation[] annotation;

    public AnnotationMetaData(final Field field, final Method method, final Annotation[] annotations) {
        fromField = true;
        this.field = field;
        this.method = method;
        this.annotation = annotations;
    }

    public AnnotationMetaData(final Method method, final Annotation[] annotation) {
        fromField = false;
        field = null;
        this.method = method;
        this.annotation = annotation;
    }

    public String getFieldName() {
        if (fromField) {
            return field.getName();
        } else {
            return method.getName();
        }
    }

    public Method getMethod() {
        return method;
    }

    public Annotation[] getAnnotation() {
        return annotation;
    }

}
