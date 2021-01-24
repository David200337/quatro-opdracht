package src.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostalCodeValidator {
    private static final String POSTALCODE_PATTERN = "[1-9]{1}[0-9]{3} {1}[a-zA-Z]{2}";

    private static final Pattern pattern = Pattern.compile(POSTALCODE_PATTERN);
 
    //Check if the given postal code is valid for the pattern
    public static boolean isValid(final String postalCodeStr) {
        Matcher matcher = pattern.matcher(postalCodeStr);
        return matcher.matches();
    }
}
