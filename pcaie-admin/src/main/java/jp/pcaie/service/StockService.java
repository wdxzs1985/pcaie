package jp.pcaie.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.ProductBean;
import jp.pcaie.domain.StockBean;
import jp.pcaie.mapper.MProductMapper;
import jp.pcaie.mapper.MStockMapper;
import jp.pcaie.support.Paginate;
import jp.pcaie.validator.ProductBeanValidator;
import jp.pcaie.validator.StockBeanValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class StockService {

    @Autowired
    private StockBeanValidator stockBeanValidator = null;
    @Autowired
    private ProductBeanValidator productBeanValidator = null;

    @Autowired
    private MStockMapper mStockMapper = null;
    @Autowired
    private MProductMapper mProductMapper = null;

    public void doSearch(final Paginate<StockBean> paginate) {
        final Map<String, Object> params = paginate.getParams();
        final int itemCount = this.mStockMapper.count(params);

        paginate.setItemCount(itemCount);
        paginate.compute();

        List<StockBean> items = null;
        if (itemCount == 0) {
            items = Collections.emptyList();
        } else {
            items = this.mStockMapper.fetchList(params);
        }
        paginate.setItems(items);
    }

    public boolean validate(final StockBean stockBean,
                            final Model model,
                            final Locale locale) {
        boolean isValid = true;

        final ProductBean productBean = stockBean.getProductBean();

        isValid = this.productBeanValidator.validateInputName(productBean.getName(),
                                                              model,
                                                              locale) && isValid;
        isValid = this.stockBeanValidator.validateInputPrice(stockBean.getPrice(),
                                                             model,
                                                             locale) && isValid;
        isValid = this.stockBeanValidator.validateInputStock(stockBean.getStock(),
                                                             model,
                                                             locale) && isValid;
        isValid = this.stockBeanValidator.validateInputSafeStock(stockBean.getSafeStock(),
                                                                 model,
                                                                 locale) && isValid;
        isValid = this.stockBeanValidator.validateInputNotificationEmail(stockBean.getNotificationEmail(),
                                                                         stockBean.getSafeStock(),
                                                                         model,
                                                                         locale) && isValid;
        return isValid;
    }

    @Transactional
    public void save(final StockBean stockBean) {
        this.saveProduct(stockBean);
        this.saveStock(stockBean);
    }

    private void saveProduct(final StockBean stockBean) {
        final ProductBean productBean = stockBean.getProductBean();
        this.mProductMapper.insert(productBean);
    }

    private void saveStock(final StockBean stockBean) {
        this.mStockMapper.insert(stockBean);
    }

    public StockBean getStockById(final Integer id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.mStockMapper.fetchBean(param);
    }

    @Transactional
    public void update(final StockBean stockBean) {
        this.mStockMapper.update(stockBean);
    }

}
