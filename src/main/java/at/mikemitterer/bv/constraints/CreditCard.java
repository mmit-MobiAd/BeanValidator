/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.mikemitterer.bv.constraints;

/**
 *
 * @author Jiren
 */

import at.mikemitterer.bv.validator.ValidateCreditCard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validator = ValidateCreditCard.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CreditCard {

    boolean mandatory() default true;
    boolean luhnchecksum() default true;
    String message();

}
