/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * This bean contain Field getter method and field Annotations.
 *
 * @author Mike Mitterer
 */
public class AnnotationMetaData {

    private final Field        field;
    private final Method       method;
    private final Annotation[] annotations;

    public AnnotationMetaData(final Field field, final Method method, final Annotation[] annotations) {
        this.field = field;
        this.method = method;
        this.annotations = Arrays.copyOf(annotations, annotations.length);
    }

    public AnnotationMetaData(final Method method, final Annotation[] annotations) {
        field = null;
        this.method = method;
        this.annotations = Arrays.copyOf(annotations, annotations.length);

    }

    public Method getMethod() {
        return method;
    }

    public Annotation[] getAnnotations() {
        return annotations.clone();
    }

}
