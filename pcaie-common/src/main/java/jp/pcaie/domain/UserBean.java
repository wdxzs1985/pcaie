package jp.pcaie.domain;

public class UserBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 5451974491866159547L;

    private String name;

    private String email;

    private String password;

    private String password2;

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

}