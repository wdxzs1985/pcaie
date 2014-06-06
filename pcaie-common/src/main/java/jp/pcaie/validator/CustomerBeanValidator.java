package jp.pcaie.validator;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CustomerBeanValidator {

    public static final int MAX_NAME_LENGTH = 45;
    public static final int MAX_KANA_LENGTH = 45;
    public static final int MAX_EMPLOYMENT_LENGTH = 45;
    public static final int MAX_DEPARTMENT_LENGTH = 45;
    public static final int MAX_MAIL_LENGTH = 100;
    public static final int MAX_TEL_LENGTH = 20;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private EmailValidator emailValidator = null;

    public boolean validateInputName(final String inputName,
                                     final Model model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("CustomerBean.name",
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

    public boolean validateInputKana(final String inputKana,
                                     final Model model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("CustomerBean.kana",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputKana) > MAX_KANA_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_KANA_LENGTH },
                                                                 locale);
            model.addAttribute("kanaError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputEmployment(final String inputEmployment,
                                           final Model model,
                                           final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("CustomerBean.employment",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputEmployment) > MAX_EMPLOYMENT_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_EMPLOYMENT_LENGTH },
                                                                 locale);
            model.addAttribute("employmentError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputDepartment(final String inputDepartment,
                                           final Model model,
                                           final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("CustomerBean.department",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputDepartment) > MAX_DEPARTMENT_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_DEPARTMENT_LENGTH },
                                                                 locale);
            model.addAttribute("departmentError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputEmail(final String inputEmail,
                                      final String inputEmail2,
                                      final Model model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("CustomerBean.email",
                                                               null,
                                                               locale);
        final String fieldName2 = this.messageSource.getMessage("CustomerBean.email2",
                                                                null,
                                                                locale);
        if (StringUtils.isBlank(inputEmail)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        } else if (StringUtils.length(inputEmail) > MAX_MAIL_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_MAIL_LENGTH },
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        } else if (!this.emailValidator.validate(inputEmail)) {
            final String message = this.messageSource.getMessage("validate.unavailable",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        } else if (!StringUtils.equalsIgnoreCase(inputEmail, inputEmail2)) {
            final String message = this.messageSource.getMessage("validate.notSame",
                                                                 new Object[] { fieldName,
                                                                         fieldName2 },
                                                                 locale);
            model.addAttribute("email2Error", message);
            isValid = false;
        }

        return isValid;
    }

    public boolean validateInputTel(final String inputTel,
                                    final Model model,
                                    final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("CustomerBean.tel",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(inputTel)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("telError", message);
            isValid = false;
        } else if (StringUtils.length(inputTel) > MAX_TEL_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_TEL_LENGTH },
                                                                 locale);
            model.addAttribute("telError", message);
            isValid = false;
        }
        return isValid;
    }
}
