package jp.pcaie.admin.mapper;

import java.util.Map;

import jp.pcaie.admin.domain.UserBean;

public interface MUsersMapper {

    UserBean fetchBean(Map<String, Object> param);

    UserBean fetchBeanByToken(Map<String, Object> param);

    int insert(UserBean userBean);

    void update(UserBean userBean);

    void deleteToken(UserBean userBean);

}
