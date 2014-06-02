package jp.pcaie.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.CustomerBean;
import jp.pcaie.domain.FormBean;
import jp.pcaie.mapper.MCustomerMapper;
import jp.pcaie.mapper.MFormMapper;
import jp.pcaie.validator.EmailValidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class FormSerivce {

    public static final int MAX_NAME_LENGTH = 45;
    public static final int MAX_KANA_LENGTH = 45;
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
    private MCustomerMapper mCustomerMapper = null;
    @Autowired
    private MFormMapper mFormMapper = null;

    public boolean validate(final FormBean formBean, final Model model, final Locale locale) {
        boolean isValid = true;
        isValid = this.validateInputName(formBean.getCustomerBean().getName(),
                                         model,
                                         locale) && isValid;
        isValid = this.validateInputKana(formBean.getCustomerBean().getKana(),
                                         model,
                                         locale) && isValid;
        isValid = this.validateInputEmployment(formBean.getCustomerBean()
                                                       .getEmployment(),
                                               model,
                                               locale) && isValid;
        isValid = this.validateInputDepartment(formBean.getCustomerBean()
                                                       .getDepartment(),
                                               model,
                                               locale) && isValid;

        switch (formBean.getContactBy()) {
        case FormBean.CONTACT_BY_EMAIL:
            isValid = this.validateInputEmail(formBean.getCustomerBean()
                                                      .getEmail(),
                                              formBean.getCustomerBean()
                                                      .getEmail2(),
                                              model,
                                              locale) && isValid;
            break;
        case FormBean.CONTACT_BY_TEL:
            isValid = this.validateInputTel(formBean.getCustomerBean().getTel(),
                                            model,
                                            locale) && isValid;
            break;
        default:
            isValid = false;
            break;
        }

        isValid = this.validateInputMaker(formBean.getMaker(), model, locale) && isValid;
        isValid = this.validateInputModel(formBean.getModel(), model, locale) && isValid;
        isValid = this.validateInputContent(formBean.getContent(),
                                            model,
                                            locale) && isValid;

        return isValid;
    }

    private boolean validateInputName(final String inputName, final Model model, final Locale locale) {
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

    private boolean validateInputKana(final String inputKana, final Model model, final Locale locale) {
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

    private boolean validateInputEmployment(final String inputEmployment, final Model model, final Locale locale) {
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

    private boolean validateInputDepartment(final String inputDepartment, final Model model, final Locale locale) {
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

    private boolean validateInputEmail(final String inputEmail, final String inputEmail2, final Model model, final Locale locale) {
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

    private boolean validateInputTel(final String inputTel, final Model model, final Locale locale) {
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

    private boolean validateInputMaker(final String inputMaker, final Model model, final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.maker",
                                                               null,
                                                               locale);
        if (StringUtils.length(inputMaker) > MAX_MAKER_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_MAKER_LENGTH },
                                                                 locale);
            model.addAttribute("makerError", message);
            isValid = false;
        }
        return isValid;
    }

    private boolean validateInputModel(final String inputModel, final Model model, final Locale locale) {
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

    private boolean validateInputContent(final String inputContent, final Model model, final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("FormBean.content",
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

    @Transactional
    public void save(final FormBean formBean) {
        this.saveCustomer(formBean);
        this.saveForm(formBean);
    }

    private void saveCustomer(final FormBean formBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", formBean.getCustomerBean().getName());
        params.put("kana", formBean.getCustomerBean().getKana());
        params.put("email", formBean.getCustomerBean().getEmail());
        params.put("tel", formBean.getCustomerBean().getTel());

        final CustomerBean customerBean = this.mCustomerMapper.fetchBean(params);
        if (customerBean == null) {
            this.mCustomerMapper.insert(formBean.getCustomerBean());
        } else {
            formBean.setCustomerBean(customerBean);
        }
    }

    private void saveForm(final FormBean formBean) {
        this.mFormMapper.insert(formBean);
    }

}
