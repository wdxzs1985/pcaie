package jp.pcaie.validator;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class ProductBeanValidator {

    public static final int MIN_PRICE = 0;
    public static final int MAX_NAME_LENGTH = 45;
    public static final int MAX_CONTENT_LENGTH = 500;

    @Autowired
    private MessageSource messageSource = null;

    public boolean validateInputName(final String inputName,
                                     final Model model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("ProductBean.name",
                                                               null,
                                                               locale);
        if (StringUtils.isEmpty(inputName)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("nameError", message);
            isValid = false;
        } else if (StringUtils.length(inputName) > MAX_NAME_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_NAME_LENGTH },
                                                                 locale);
            model.addAttribute("nameError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputContent(final String inputContent,
                                        final Model model,
                                        final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("ProductBean.content",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputContent) > MAX_CONTENT_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_CONTENT_LENGTH },
                                                                 locale);
            model.addAttribute("contentError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputPrice(final Integer inputPrice,
                                      final Model model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("ProductBean.price",
                                                               null,
                                                               locale);
        if (inputPrice == null) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("priceError", message);
            isValid = false;
        } else if (inputPrice < MIN_PRICE) {
            final String message = this.messageSource.getMessage("validate.tooSmall",
                                                                 new Object[] { fieldName,
                                                                         MIN_PRICE },
                                                                 locale);
            model.addAttribute("priceError", message);
            isValid = false;
        }
        return isValid;
    }
}
