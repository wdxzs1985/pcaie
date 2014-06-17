package jp.pcaie.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.CustomerBean;
import jp.pcaie.domain.FormBean;
import jp.pcaie.mapper.MCustomerMapper;
import jp.pcaie.mapper.MFormMapper;
import jp.pcaie.validator.CustomerBeanValidator;
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
    private CustomerBeanValidator customerBeanValidator = null;
    @Autowired
    private MCustomerMapper mCustomerMapper = null;
    @Autowired
    private MFormMapper mFormMapper = null;

    public boolean validate(final FormBean formBean,
                            final Model model,
                            final Locale locale) {
        boolean isValid = true;

        final CustomerBean customerBean = formBean.getCustomerBean();

        isValid = this.customerBeanValidator.validateInputName(customerBean.getName(),
                                                               model,
                                                               locale) && isValid;
        isValid = this.customerBeanValidator.validateInputKana(customerBean.getKana(),
                                                               model,
                                                               locale) && isValid;
        isValid = this.customerBeanValidator.validateInputEmployment(customerBean.getEmployment(),
                                                                     model,
                                                                     locale) && isValid;
        isValid = this.customerBeanValidator.validateInputDepartment(customerBean.getDepartment(),
                                                                     model,
                                                                     locale) && isValid;

        switch (customerBean.getContactBy()) {
        case CustomerBean.CONTACT_BY_EMAIL:
            isValid = this.customerBeanValidator.validateInputEmail(customerBean.getEmail(),
                                                                    customerBean.getEmail2(),
                                                                    model,
                                                                    locale) && isValid;
            break;
        case CustomerBean.CONTACT_BY_TEL:
            isValid = this.customerBeanValidator.validateInputTel(customerBean.getTel(),
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
