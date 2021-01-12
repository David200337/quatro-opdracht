package src.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLValidator {
    private static final String URL_PATTERN = "^(https?:\\/\\/)?([\\w]+\\.)+[‌​\\w]{2,63}\\/?$";
 
    private static final Pattern pattern = Pattern.compile(URL_PATTERN);
 
    public static boolean isValid(final String urlStr) {
        Matcher matcher = pattern.matcher(urlStr);
        return matcher.matches();
    }
}
