package jp.pcaie.mapper;

import jp.pcaie.domain.ProductBean;
import jp.pcaie.domain.StockBean;

public interface MProductMapper {

    void insert(ProductBean productBean);

    void update(StockBean stockBean);

}
