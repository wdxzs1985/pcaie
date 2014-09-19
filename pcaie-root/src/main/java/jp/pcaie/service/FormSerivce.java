package jp.pcaie.service;

import java.util.Locale;

import jp.pcaie.domain.FormBean;
import jp.pcaie.mail.FormMail;
import jp.pcaie.mapper.MFormMapper;
import jp.pcaie.validator.FormBeanValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class FormSerivce {

    @Autowired
    private FormBeanValidator formBeanValidator = null;
    @Autowired
    private MFormMapper mFormMapper = null;
    @Autowired
    private FormMail formMail = null;

    public boolean validate(final FormBean formBean,
                            final Model model,
                            final Locale locale) {
        boolean isValid = true;

        isValid = this.formBeanValidator.validateInputName(formBean.getName(),
                                                           model,
                                                           locale) && isValid;
        isValid = this.formBeanValidator.validateInputKana(formBean.getKana(),
                                                           model,
                                                           locale) && isValid;
        isValid = this.formBeanValidator.validateInputEmployment(formBean.getEmployment(),
                                                                 model,
                                                                 locale) && isValid;
        isValid = this.formBeanValidator.validateInputDepartment(formBean.getDepartment(),
                                                                 model,
                                                                 locale) && isValid;

        switch (formBean.getContactBy()) {
        case FormBean.CONTACT_BY_EMAIL:
            isValid = this.formBeanValidator.validateInputEmail(formBean.getEmail(),
                                                                formBean.getEmail2(),
                                                                model,
                                                                locale) && isValid;
            break;
        case FormBean.CONTACT_BY_TEL:
            isValid = this.formBeanValidator.validateInputTel(formBean.getTel(),
                                                              model,
                                                              locale) && isValid;
            break;
        default:
            isValid = false;
            break;
        }

        isValid = this.formBeanValidator.validateInputMaker(formBean.getMaker(),
                                                            model,
                                                            locale) && isValid;
        isValid = this.formBeanValidator.validateInputModel(formBean.getModel(),
                                                            model,
                                                            locale) && isValid;
        isValid = this.formBeanValidator.validateInputContent(formBean.getContent(),
                                                              model,
                                                              locale) && isValid;
        return isValid;
    }

    @Transactional
    public void save(final FormBean formBean) {
        this.mFormMapper.insert(formBean);
        this.formMail.send(formBean);
    }
}
