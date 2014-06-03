package jp.pcaie.domain;


public class StaffBean extends UserBean {

    public static Integer ROLE_NORMAL = 0;

    public static Integer ROLE_ADMIN = 1;
    /**
     * 
     */
    private static final long serialVersionUID = -2882391645013070692L;

    private Integer role = ROLE_NORMAL;

    public Integer getRole() {
        return this.role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

}
