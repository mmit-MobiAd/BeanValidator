/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.constraints;


/**
 *
 * @author Jiren
 */

import at.mikemitterer.bv.validator.ValidateRange;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validator = ValidateRange.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Range {
    float start();
    float end();
    String message();

}
