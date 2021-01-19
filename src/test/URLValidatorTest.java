package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import src.validators.URLValidator;

/**
     * @desc Validates if URL is valid. It should be in the form of:
     *       <https:// or http://><at least 1 character>.<at least 1 character>
     * 
     * @subcontract no https:// or http:// {
     *   @requires (!url.contains("https://") || !url.contains("http://")) ||
     *             url.split(".")[0].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * 
     * @subcontract no https:// or http:// and no tld part {
     *   @requires (!url.contains("https://") || !url.contains("http://"))  ||
     *             url.split(".")[1].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract valid email with https:// {
     *   @requires url.contains("https://")
     *   @ensures \result = true;
     * }
     * 
     * @subcontract valid email with http:// {
     *   @requires url.contains("http://")
     *   @ensures \result = true;
     * }
     * 
     */

public class URLValidatorTest {
        @Test
        public void testIsValidRequiresURLWebsiteDotNlEnsuresFalse() {
            // Arrange
            String url = "website.nl";
    
            // Act
            Boolean result = URLValidator.isValid(url);
    
            // Assert
            assertEquals(false, result);
        }

        @Test
        public void testIsValidRequiresURLWebsiteEnsuresFalse() {
            // Arrange
            String url = "website";
    
            // Act
            Boolean result = URLValidator.isValid(url);
    
            // Assert
            assertEquals(false, result);
        }

        @Test
        public void testIsValidRequiresURLHttpsColonSlashSlashWebsiteDotNlEnsuresTrue() {
            // Arrange
            String url = "https://website.nl";
    
            // Act
            Boolean result = URLValidator.isValid(url);
    
            // Assert
            assertEquals(true, result);
        }

        @Test
        public void testIsValidRequiresURLHttpColonSlashSlashWebsiteDotNlEnsuresTrue() {
            // Arrange
            String url = "http://website.nl";
    
            // Act
            Boolean result = URLValidator.isValid(url);
    
            // Assert
            assertEquals(true, result);
        }
    
}
