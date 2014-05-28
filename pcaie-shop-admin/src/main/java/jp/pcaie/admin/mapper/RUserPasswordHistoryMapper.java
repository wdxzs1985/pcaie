package jp.pcaie.admin.mapper;

import jp.pcaie.admin.domain.UserPasswordHistoryBean;

public interface RUserPasswordHistoryMapper {

    public int insert(UserPasswordHistoryBean param);

    public int deleteToken(UserPasswordHistoryBean param);
}
