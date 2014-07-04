/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import java.util.regex.Pattern;

/**
 * @author Jiren
 */
abstract class ValidateBasePattern {

    /**
     * validate Patten.
     * Mandatory = false : if it is null or empty return true. else check for pattern.
     * Mandatory = true  : if it is null return false else check for pattern
     */
    boolean validatePattern(String input, Pattern pattern, boolean mandatory) {

        if (!mandatory) {
            if (input == null) {
                return true;
            } else if (input.trim().isEmpty()) {
                return true;
            }
        } else {
            if (input == null) {
                return false;
            }
        }


        return pattern.matcher(input).matches();
    }
}
