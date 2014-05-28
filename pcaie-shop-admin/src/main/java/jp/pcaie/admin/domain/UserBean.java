package jp.pcaie.admin.domain;

public class UserBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 5451974491866159547L;

    private String nickname;

    private String email;

    private String password;

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

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

}
