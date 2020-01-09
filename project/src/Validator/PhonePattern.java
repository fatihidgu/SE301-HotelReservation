package Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PhonePattern {
    STANDARD("(\\d{3})-(\\d{3})-(\\d{4})"),
    PLAIN("(\\d{3})(\\d{3})(\\d{4})");

    private final Pattern compiledPattern;

    PhonePattern(String pattern) {
        compiledPattern = Pattern.compile(pattern);
    }

    public static PhonePattern getPhonePattern(String patternString) {
        try {
            return valueOf(patternString.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            //returning default pattern;
            return STANDARD;
        }
    }

    public boolean match(String inputString) {
        Matcher matcher = compiledPattern.matcher(inputString);
        return matcher.matches();
    }

    public String getPattern() {
        return compiledPattern.pattern();
    }
}
