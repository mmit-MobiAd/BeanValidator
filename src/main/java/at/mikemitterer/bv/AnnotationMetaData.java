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
    private final Method       method;
    private       Annotation[] annotation;

    public AnnotationMetaData(final Field field, final Method method, final Annotation[] annotations) {
        this.field = field;
        this.method = method;
        this.annotation = annotations;
    }

    public Field getField() {
        return field;
    }

    public Method getMethod() {
        return method;
    }

    public Annotation[] getAnnotation() {
        return annotation;
    }

}
