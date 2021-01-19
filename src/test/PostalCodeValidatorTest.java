package src.test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import src.validators.PostalCodeValidator;

 /**
     * @desc Checks if the input postal code is in a uniform output in the form
     * nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital letters.
     * 
     * 
     * @subcontract valid postalCode {
     *   @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *             Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *             postalCode.trim().substring(4).trim().length == 2 &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z' &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z';
     *   @ensures \result = true;
     * }
     *  
     * @subcontract invalid postalCode {
     *   @requires no other valid precondition;
     *   @signals false;
     * }
     * 
     */
public class PostalCodeValidatorTest {
    

    @Test
    public void testIsValidRequiresPostalCode1234SpaceABEnsuresTrue() {
        // Arrange
        String postalCode = "1234 AB";

        // Act
        Boolean result = PostalCodeValidator.isValid(postalCode);

        // Assert
        assertEquals(true, result);
    }

    @Test
    public void testIsValidRequiresPostalCodeab12EnsuresFalse() {
        // Arrange
        String postalCode = "ab12";

        // Act
        Boolean result = PostalCodeValidator.isValid(postalCode);

        assertEquals(false, result);
    }
}


