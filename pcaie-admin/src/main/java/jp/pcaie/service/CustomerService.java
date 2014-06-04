package jp.pcaie.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.CustomerBean;
import jp.pcaie.mapper.MCustomerMapper;
import jp.pcaie.support.Paginate;
import jp.pcaie.validator.CustomerBeanValidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class CustomerService {

    @Autowired
    private CustomerBeanValidator customerBeanValidator = null;
    @Autowired
    private MCustomerMapper mCustomerMapper = null;

    public void doSearch(final Paginate<CustomerBean> paginate) {
        final Map<String, Object> params = paginate.getParams();
        final int itemCount = this.mCustomerMapper.count(params);

        paginate.setItemCount(itemCount);
        paginate.compute();

        List<CustomerBean> items = null;
        if (itemCount == 0) {
            items = Collections.emptyList();
        } else {
            items = this.mCustomerMapper.fetchList(params);
        }
        paginate.setItems(items);
    }

    public CustomerBean getCustomerById(final Integer id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.mCustomerMapper.fetchBean(param);
    }

    public boolean validate(final CustomerBean customerBean,
                            final Model model,
                            final Locale locale) {
        boolean isValid = true;

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

        if (StringUtils.isNotBlank(customerBean.getEmail())) {
            isValid = this.customerBeanValidator.validateInputEmail(customerBean.getEmail(),
                                                                    customerBean.getEmail(),
                                                                    model,
                                                                    locale) && isValid;
        }
        if (StringUtils.isNotBlank(customerBean.getTel())) {
            isValid = this.customerBeanValidator.validateInputTel(customerBean.getTel(),
                                                                  model,
                                                                  locale) && isValid;
        }
        return isValid;
    }

    @Transactional
    public void update(final CustomerBean customerBean) {
        this.mCustomerMapper.update(customerBean);
    }

}
