package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import src.validators.EmailValidator;


/**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[0].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * 
     * @subcontract no subdomain part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".")[0].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract no tld part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".")[1].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract valid email {
     *   @requires no other precondition
     *   @ensures \result = true;
     * }
     * 
     */
public class EmailValidatorTest {
    @Test
    public void testIsValidRequiresMailAddressAtMailDotNlEnsuresFalse() {
        // Arrange
        String mailAddress = "@mail.nl";

        // Act
        Boolean result = EmailValidator.isValid(mailAddress);

        // Assert
        assertEquals(false, result);
    }


    @Test
    public void testIsValidRequiresMailAddressEmailAtDotNlEnsuresFalse() {
        // Arrange
        String mailAddress = "email@.nl";

        // Act
        Boolean result = EmailValidator.isValid(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testIsValidRequiresMailAddressEmailAtMailDotEnsuresFalse() {
        // Arrange
        String mailAddress = "email@mail.";

        // Act
        Boolean result = EmailValidator.isValid(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testIsValidRequiresMailAddressEmailAtMailDotNlEnsuresTrue() {
        // Arrange
        String mailAddress = "email@mail.nl";

        // Act
        Boolean result = EmailValidator.isValid(mailAddress);

        // Assert
        assertEquals(true, result);
    }
}
