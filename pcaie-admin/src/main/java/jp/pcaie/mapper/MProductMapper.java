package jp.pcaie.mapper;

import java.util.Map;

import jp.pcaie.domain.ProductBean;
import jp.pcaie.domain.StockBean;

public interface MProductMapper {

    StockBean fetchBean(Map<String, Object> param);

    void insert(ProductBean productBean);

    void update(StockBean stockBean);

}
