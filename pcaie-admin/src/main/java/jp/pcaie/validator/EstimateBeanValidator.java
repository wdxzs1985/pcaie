package jp.pcaie.validator;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class EstimateBeanValidator {

    public static final int MIN_PRICE = 0;
    public static final int MAX_COMMENT_LENGTH = 100;

    @Autowired
    private final MessageSource messageSource = null;

    public boolean validateInputPrice(final Integer inputPrice,
                                      final String messageAttribute,
                                      final Model model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("EstimateBean.price",
                                                               null,
                                                               locale);
        if (inputPrice == null) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute(messageAttribute, message);
            isValid = false;
        } else if (inputPrice != null && inputPrice < MIN_PRICE) {
            final String message = this.messageSource.getMessage("validate.tooSmall",
                                                                 new Object[] { fieldName,
                                                                         MIN_PRICE },
                                                                 locale);
            model.addAttribute(messageAttribute, message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputComment(final String inputComment,
                                        final String messageAttribute,
                                        final Model model,
                                        final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("EstimateBean.comment",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputComment) > MAX_COMMENT_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_COMMENT_LENGTH },
                                                                 locale);
            model.addAttribute(messageAttribute, message);
            isValid = false;
        }
        return isValid;
    }
}
