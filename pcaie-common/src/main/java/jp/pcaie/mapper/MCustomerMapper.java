package jp.pcaie.mapper;

import java.util.Map;

import jp.pcaie.domain.CustomerBean;

public interface MCustomerMapper {

    CustomerBean fetchBean(Map<String, Object> params);

    void insert(CustomerBean customerBean);

}
