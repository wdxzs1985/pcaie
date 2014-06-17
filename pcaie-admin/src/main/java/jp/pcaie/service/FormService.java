package jp.pcaie.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.EstimateBean;
import jp.pcaie.domain.FormBean;
import jp.pcaie.mapper.DEstimateMapper;
import jp.pcaie.mapper.MFormMapper;
import jp.pcaie.support.Paginate;
import jp.pcaie.validator.EstimateBeanValidator;
import jp.pcaie.validator.FormBeanValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class FormService {

    @Autowired
    private FormBeanValidator formBeanValidator = null;
    @Autowired
    private EstimateBeanValidator estimateBeanValidator = null;
    @Autowired
    private MFormMapper mFormMapper = null;
    @Autowired
    private DEstimateMapper dEstimateMapper = null;

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
                                                                formBean.getEmail(),
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
        if (formBean.getId() == null) {
            this.mFormMapper.insert(formBean);
        } else {
            this.mFormMapper.update(formBean);
        }
    }

    public void doSearch(final Paginate<FormBean> paginate) {
        final Map<String, Object> params = paginate.getParams();
        final int itemCount = this.mFormMapper.count(params);

        paginate.setItemCount(itemCount);
        paginate.compute();

        List<FormBean> items = null;
        if (itemCount == 0) {
            items = Collections.emptyList();
        } else {
            items = this.mFormMapper.fetchList(params);
        }
        paginate.setItems(items);
    }

    public FormBean getFormById(final Integer id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.mFormMapper.fetchBean(param);
    }

    public EstimateBean getEstimateById(final int id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.dEstimateMapper.fetchBean(param);
    }

    @Transactional
    public void save(final FormBean formBean, final EstimateBean estimateBean) {
        int total = 0;
        total += estimateBean.getUnit1Price();
        total += estimateBean.getUnit2Price();
        total += estimateBean.getUnit3Price();
        total += estimateBean.getUnit4Price();
        total += estimateBean.getUnit5Price();
        total += estimateBean.getUnit6Price();
        total += estimateBean.getUnit7Price();
        total += estimateBean.getUnit8Price();
        total += estimateBean.getUnit9Price();
        estimateBean.setTotal(total);

        this.dEstimateMapper.delete(estimateBean);
        this.dEstimateMapper.insert(estimateBean);

        this.mFormMapper.update(formBean);
    }

    public boolean validate(final EstimateBean estimateBean,
                            final Model model,
                            final Locale locale) {
        boolean isValid = true;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit1Price(),
                                                                "unit1PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit1Comment(),
                                                                  "unit1CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit2Price(),
                                                                "unit2PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit2Comment(),
                                                                  "unit2CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit3Price(),
                                                                "unit3PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit3Comment(),
                                                                  "unit3CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit4Price(),
                                                                "unit4PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit4Comment(),
                                                                  "unit4CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit5Price(),
                                                                "unit5PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit5Comment(),
                                                                  "unit5CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit6Price(),
                                                                "unit6PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit6Comment(),
                                                                  "unit6CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit7Price(),
                                                                "unit7PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit7Comment(),
                                                                  "unit7CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit8Price(),
                                                                "unit8PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit8Comment(),
                                                                  "unit8CommentError",
                                                                  model,
                                                                  locale) && isValid;

        isValid = this.estimateBeanValidator.validateInputPrice(estimateBean.getUnit9Price(),
                                                                "unit9PriceError",
                                                                model,
                                                                locale) && isValid;
        isValid = this.estimateBeanValidator.validateInputComment(estimateBean.getUnit9Comment(),
                                                                  "unit9CommentError",
                                                                  model,
                                                                  locale) && isValid;
        return isValid;
    }
}
