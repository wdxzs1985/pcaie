package jp.pcaie.validator;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class FormBeanValidator {

    public static final int MAX_NAME_LENGTH = 45;
    public static final int MAX_KANA_LENGTH = 45;
    public static final int MAX_ADDRESS_LENGTH = 200;
    public static final int MAX_EMPLOYMENT_LENGTH = 45;
    public static final int MAX_DEPARTMENT_LENGTH = 45;
    public static final int MAX_MAIL_LENGTH = 100;
    public static final int MAX_TEL_LENGTH = 20;

    public static final int MAX_MAKER_LENGTH = 45;
    public static final int MAX_MODEL_LENGTH = 45;
    public static final int MAX_CONTENT_LENGTH = 500;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private EmailValidator emailValidator = null;
    @Autowired
    private ZipCodeValidator zipCodeValidator = null;

    public boolean validateInputName(final String inputName,
                                     final Model model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.name",
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
        final String fieldName = this.messageSource.getMessage("FormBean.kana",
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

    public boolean validateInputZipCode(final String inputZipCode,
                                        final Model model,
                                        final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.zipCode",
                                                               null,
                                                               locale);
        if (StringUtils.isNotBlank(inputZipCode)) {
            if (!this.zipCodeValidator.validate(inputZipCode)) {
                final String message = this.messageSource.getMessage("validate.unavailable",
                                                                     new Object[] { fieldName },
                                                                     locale);
                model.addAttribute("zipCodeError", message);
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateInputAddress(final String inputAddress,
                                        final Model model,
                                        final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.address",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputAddress) > MAX_ADDRESS_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_ADDRESS_LENGTH },
                                                                 locale);
            model.addAttribute("addressError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputEmployment(final String inputEmployment,
                                           final Model model,
                                           final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.employment",
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
        final String fieldName = this.messageSource.getMessage("FormBean.department",
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
        final String fieldName = this.messageSource.getMessage("FormBean.email",
                                                               null,
                                                               locale);
        final String fieldName2 = this.messageSource.getMessage("FormBean.email2",
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
        final String fieldName = this.messageSource.getMessage("FormBean.tel",
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
