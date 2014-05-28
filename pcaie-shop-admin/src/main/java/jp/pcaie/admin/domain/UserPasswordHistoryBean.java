package jp.pcaie.admin.domain;

import java.util.Date;

public class UserPasswordHistoryBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 4979436169803924116L;

    private UserBean userBean = null;

    private String token = null;

    private Date expire = null;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Date getExpire() {
        return this.expire;
    }

    public void setExpire(final Date expire) {
        this.expire = expire;
    }

}
