/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv.validator;

import at.mikemitterer.bv.AnnotationMetaData;
import at.mikemitterer.bv.ViolationInfoHandler;
import at.mikemitterer.bv.constraints.CreditCard;

import java.util.regex.Pattern;

/**
 * @author Jiren
 */
public class ValidateCreditCard extends ValidateBasePattern implements Validate<String, CreditCard> {

    private final static String VISA_PATTERN     = "^4[0-9]{12}(?:[0-9]{3})?$";
    private final static String MASTER_PATTERN   = "^5[1-5][0-9]{14}$";
    private final static String AMEX_PATTERN     = "^3[47][0-9]{13}$";
    private final static String DISCOVER_PATTERN = "^6(?:011|5[0-9]{2})[0-9]{12}";
    private final static String DINERS_PATTERN   = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
    private final static String JCB_PATTERN      = "^(?:2131|1800|35\\d{3})\\d{11}$";

    private Pattern      pattern    = null;

    public ValidateCreditCard() {
        //noinspection StringBufferReplaceableByString
        final StringBuffer patternStr = new StringBuffer();

        patternStr.append("(" + VISA_PATTERN + ")|(");
        patternStr.append("(" + MASTER_PATTERN + ")|(");
        patternStr.append("(" + AMEX_PATTERN + ")|(");
        patternStr.append("(" + DISCOVER_PATTERN + ")|(");
        patternStr.append("(" + DINERS_PATTERN + ")|(");
        patternStr.append("(" + JCB_PATTERN + ")");

        pattern = Pattern.compile(patternStr.toString());

    }

    public boolean validate(final AnnotationMetaData method,String value, CreditCard annotation, ViolationInfoHandler validationMsges) {

        value = value.replace("[- ]", "");

        if (annotation.luhnchecksum()) {
            if (!checkSum(value)) {
                validationMsges.addMessage(method,annotation.message(), value);
                return false;
            }
        }

        if (!validatePattern(value, pattern, annotation.mandatory())) {
            validationMsges.addMessage(method,annotation.message(), value);
            return false;
        }

        return true;
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------

    /**
     * Luhn Algo to check card number is valid or not.
     */
    private boolean checkSum(String cardNumber) {

        char[] digitChars = cardNumber.toCharArray();
        Integer[] digits = new Integer[digitChars.length];
        Integer sum = 0;

        for (int i = 0; i < digits.length; i++) {
            digits[i] = Character.getNumericValue(digitChars[i]);
            if (((i + 1) % 2) != 0) {
                int oddnumber = digits[i] * 2;
                if (oddnumber > 9) {
                    oddnumber = oddnumber - 9;
                }

                //   oddnumber = oddnumber > 9? oddnumber-9:oddnumber;

                sum = sum + oddnumber;

            } else {
                sum = sum + digits[i];
            }
        }

        return (sum % 10 == 0);
    }

//    public static void main(String[] args) {
//
//        CreditCardValidator creditCardValidator = new CreditCardValidator();
//
//        String patternStr = "(" + VISA_PATTERN + ")|(" + MASTER_PATTERN + ")";
//
//        Pattern pattern = Pattern.compile(patternStr);
//
//        String card = "5811111111111111";
//
//        if (pattern.matcher(card).matches()) {
//            System.out.println("Match");
//        } else {
//            System.out.println("not match");
//        }
//
//
//    }
}
