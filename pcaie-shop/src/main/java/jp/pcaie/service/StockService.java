package jp.pcaie.service;

import java.util.Collections;
import java.util.List;

import jp.pcaie.domain.ProductBean;
import jp.pcaie.domain.StockBean;

import org.springframework.stereotype.Service;

@Service
public class StockService {

    public List<StockBean> getRecommand() {
        return Collections.nCopies(12, this.dummyStock());
    }

    public StockBean getStockById(final Integer id) {
        return this.dummyStock();
    }

    private StockBean dummyStock() {
        final StockBean stockBean = new StockBean();
        stockBean.setId(1);
        stockBean.setProductBean(this.dummyProduct());
        return stockBean;
    }

    private ProductBean dummyProduct() {
        final ProductBean productBean = new ProductBean();
        productBean.setId(1);
        productBean.setPrice(16800);
        productBean.setName("OS X Mavericks‎");
        productBean.setContent("新しい機能、アプリケーション、テクノロジーがつまった新しいOS Xは、できることがさらに広がるパワーに満ちています。Macのエネルギー効率も、これまで以上に優れたものになります。");
        return productBean;
    }
}
