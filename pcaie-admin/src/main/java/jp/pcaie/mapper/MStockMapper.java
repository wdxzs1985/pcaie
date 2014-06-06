package jp.pcaie.mapper;

import java.util.List;
import java.util.Map;

import jp.pcaie.domain.StockBean;

public interface MStockMapper {

    int count(Map<String, Object> params);

    List<StockBean> fetchList(Map<String, Object> params);

    StockBean fetchBean(Map<String, Object> param);

    void insert(StockBean stockBean);

    void update(StockBean stockBean);

}
