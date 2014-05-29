package jp.pcaie.admin.mapper;

import java.util.List;
import java.util.Map;

import jp.pcaie.admin.domain.StaffBean;

public interface MStaffMapper {

    StaffBean fetchBean(Map<String, Object> param);

    int insert(StaffBean userBean);

    void update(StaffBean userBean);

    int count(Map<String, Object> params);

    List<StaffBean> fetchList(Map<String, Object> params);

    void delete(StaffBean userBean);

}
