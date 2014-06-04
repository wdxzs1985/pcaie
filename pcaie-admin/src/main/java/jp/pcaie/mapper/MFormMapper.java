package jp.pcaie.mapper;

import java.util.List;
import java.util.Map;

import jp.pcaie.domain.FormBean;

public interface MFormMapper {

    void insert(FormBean formBean);

    int count(Map<String, Object> params);

    List<FormBean> fetchList(Map<String, Object> params);

    FormBean fetchBean(Map<String, Object> param);

    void update(FormBean formBean);

}
