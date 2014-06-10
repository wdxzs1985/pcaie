package jp.pcaie.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ZipCodeValidator implements Validator<String> {

    private static final String ZIPCODE_PATTERN = "^[0-9]{3}-?[0-9]{4}$";

    private final Pattern pattern = Pattern.compile(ZIPCODE_PATTERN);

    @Override
    public boolean validate(final String value) {
        final Matcher matcher = this.pattern.matcher(value);
        return matcher.matches();
    }

}
