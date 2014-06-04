package jp.pcaie.mapper;

import java.util.List;
import java.util.Map;

import jp.pcaie.domain.CustomerBean;

public interface MCustomerMapper {

    CustomerBean fetchBean(Map<String, Object> params);

    void insert(CustomerBean customerBean);

    int count(Map<String, Object> params);

    List<CustomerBean> fetchList(Map<String, Object> params);

    void update(CustomerBean customerBean);

}
