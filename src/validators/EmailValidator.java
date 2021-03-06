package src.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator{
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
 
    //Check if the given email adres is valid for the pattern
    public static boolean isValid(final String emailStr) {
        Matcher matcher = pattern.matcher(emailStr);
        return matcher.matches();
    }
}


