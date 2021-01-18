package src.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLValidator {
    private static final String URL_PATTERN = "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>";
 
    private static final Pattern pattern = Pattern.compile(URL_PATTERN);
 
    public static boolean isValid(final String urlStr) {
        Matcher matcher = pattern.matcher(urlStr);
        return matcher.matches();
    }
}
