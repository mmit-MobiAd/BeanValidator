/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.constraints;


import at.mikemitterer.bv.validator.ValidatePhoneUS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *
 * @author Jiren
 */
@Constraint(validator = ValidatePhoneUS.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface PhoneUS {
    String value();
    boolean mandatory() default true;
    String message();
}
