package jp.pcaie.domain;

public class StaffBean extends DtoBean implements UserBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6085733586405309440L;

    public static Integer ROLE_NORMAL = 0;

    public static Integer ROLE_ADMIN = 1;

    private String name;

    private String email;

    private String password;

    private String password2;

    private boolean login = false;

    private Integer role = ROLE_NORMAL;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword2() {
        return this.password2;
    }

    public void setPassword2(final String password2) {
        this.password2 = password2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isLogin() {
        return this.login;
    }

    public void setLogin(final boolean login) {
        this.login = login;
    }

    public Integer getRole() {
        return this.role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

}
