package jp.pcaie.validator;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class FormBeanValidator {

    public static final int MAX_MAKER_LENGTH = 45;
    public static final int MAX_MODEL_LENGTH = 45;
    public static final int MAX_CONTENT_LENGTH = 500;

    @Autowired
    private MessageSource messageSource = null;

    public boolean validateInputMaker(final String inputMaker,
                                      final Model model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.maker",
                                                               null,
                                                               locale);
        if (StringUtils.isEmpty(inputMaker)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("makerError", message);
            isValid = false;
        } else if (StringUtils.length(inputMaker) > MAX_MAKER_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_MAKER_LENGTH },
                                                                 locale);
            model.addAttribute("makerError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputModel(final String inputModel,
                                      final Model model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.model",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputModel) > MAX_MODEL_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_MODEL_LENGTH },
                                                                 locale);
            model.addAttribute("modelError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputContent(final String inputContent,
                                        final Model model,
                                        final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.content",
                                                               null,
                                                               locale);
        if (StringUtils.isEmpty(inputContent)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("contentError", message);
            isValid = false;
        } else if (StringUtils.length(inputContent) > MAX_CONTENT_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_CONTENT_LENGTH },
                                                                 locale);
            model.addAttribute("contentError", message);
            isValid = false;
        }
        return isValid;
    }

}
