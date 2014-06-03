package jp.pcaie.mapper;

import java.util.List;
import java.util.Map;

import jp.pcaie.domain.FormBean;
import jp.pcaie.domain.StaffBean;

public interface MFormMapper {

    void insert(FormBean formBean);

    int count(Map<String, Object> params);

    List<StaffBean> fetchList(Map<String, Object> params);

}
