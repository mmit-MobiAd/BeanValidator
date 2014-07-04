/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.ZipCode;

import java.util.regex.Pattern;

/**
 * @author Jiren
 */
public class ValidateZipCode extends ValidateBasePattern implements Validate<String, ZipCode> {

    //Format : XXXXX
    private static final String  ZIP_PATTERN = "\\d{5}(-\\d{4})?";
    private final Pattern pattern = Pattern.compile(ZIP_PATTERN);

    public boolean validate(final AnnotationMetaData method, String zipCode, ZipCode aZipCode, ViolationInfoHandler validationMsges) {

        if (!validatePattern(zipCode, pattern, aZipCode.mandatory())) {
            validationMsges.addMessage(method, aZipCode.message(), zipCode);
            return false;
        }

        return true;
    }
}
