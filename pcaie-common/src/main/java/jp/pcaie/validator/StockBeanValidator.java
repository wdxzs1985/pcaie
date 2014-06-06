package jp.pcaie.validator;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class StockBeanValidator {

    public static final int MAX_MAIL_LENGTH = 100;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private EmailValidator emailValidator = null;

    public boolean validateInputStock(final Integer inputStock,
                                      final Model model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StockBean.stock",
                                                               null,
                                                               locale);
        if (inputStock == null) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("stockError", message);
            isValid = false;
        } else if (inputStock < 0) {
            final String message = this.messageSource.getMessage("validate.tooSmall",
                                                                 new Object[] { fieldName,
                                                                         1 },
                                                                 locale);
            model.addAttribute("stockError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputSafeStock(final Integer inputSafeStock,
                                          final Model model,
                                          final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StockBean.safeStock",
                                                               null,
                                                               locale);
        if (inputSafeStock == null) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("safeStockError", message);
            isValid = false;
        } else if (inputSafeStock < 0) {
            final String message = this.messageSource.getMessage("validate.tooSmall",
                                                                 new Object[] { fieldName,
                                                                         0 },
                                                                 locale);
            model.addAttribute("safeStockError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputNotificationEmail(final String inputNotificationEmail,
                                                  final Model model,
                                                  final Locale locale) {
        boolean isValid = true;
        if (StringUtils.isNotBlank(inputNotificationEmail)) {
            final String fieldName = this.messageSource.getMessage("StockBean.notificationEmail",
                                                                   null,
                                                                   locale);
            if (StringUtils.length(inputNotificationEmail) > MAX_MAIL_LENGTH) {
                final String message = this.messageSource.getMessage("validate.tooLong",
                                                                     new Object[] { fieldName,
                                                                             MAX_MAIL_LENGTH },
                                                                     locale);
                model.addAttribute("notificationEmailError", message);
                isValid = false;
            } else if (!this.emailValidator.validate(inputNotificationEmail)) {
                final String message = this.messageSource.getMessage("validate.unavailable",
                                                                     new Object[] { fieldName },
                                                                     locale);
                model.addAttribute("notificationEmailError", message);
                isValid = false;
            }
        }
        return isValid;
    }
}
