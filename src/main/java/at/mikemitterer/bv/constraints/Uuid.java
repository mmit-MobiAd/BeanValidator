/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.constraints;


import at.mikemitterer.bv.validator.ValidateUuid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Jiren
 */

@Constraint(validator = ValidateUuid.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Uuid {
    public static final String PATTERN_UUID = "^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$";
    String pattern() default PATTERN_UUID;
    String message();
}
