package jp.pcaie.validator;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class StockBeanValidator {

    public static final int MIN_PRICE = 0;
    public static final int MIN_STOCK = 0;
    public static final int MIN_SAFE_STOCK = 0;
    public static final int MAX_MAIL_LENGTH = 100;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private EmailValidator emailValidator = null;

    public boolean validateInputPrice(final Integer inputPrice,
                                      final Model model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StockBean.price",
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
        } else if (inputStock < MIN_STOCK) {
            final String message = this.messageSource.getMessage("validate.tooSmall",
                                                                 new Object[] { fieldName,
                                                                         MIN_STOCK },
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
        } else if (inputSafeStock < MIN_SAFE_STOCK) {
            final String message = this.messageSource.getMessage("validate.tooSmall",
                                                                 new Object[] { fieldName,
                                                                         MIN_SAFE_STOCK },
                                                                 locale);
            model.addAttribute("safeStockError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputNotificationEmail(final String inputNotificationEmail,
                                                  final Integer inputSafeStock,
                                                  final Model model,
                                                  final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StockBean.notificationEmail",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(inputNotificationEmail)) {
            if (inputSafeStock != null && inputSafeStock > 0) {
                final String message = this.messageSource.getMessage("validate.empty",
                                                                     new Object[] { fieldName },
                                                                     locale);
                model.addAttribute("notificationEmailError", message);
                isValid = false;
            }
        } else {
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
