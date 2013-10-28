/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.constraints;


/**
 *
 * @author Jiren
 */

import at.mikemitterer.bv.validator.ValidateNotEmpty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validator = ValidateNotEmpty.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface NotEmpty {

    String message();

}
