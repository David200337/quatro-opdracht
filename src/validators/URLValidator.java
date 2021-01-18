package src.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLValidator {
    private static final String URL_PATTERN = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\s]{2,})";
 
    private static final Pattern pattern = Pattern.compile(URL_PATTERN);
 
    public static boolean isValid(final String urlStr) {
        Matcher matcher = pattern.matcher(urlStr);
        return matcher.matches();
    }
}
