package jp.pcaie.domain;

public class ShopUserBean extends DtoBean implements UserBean {

    /**
     * 
     */
    private static final long serialVersionUID = -3155124231221052164L;

    private String name;

    private String email;

    private String password;

    private String password2;

    private boolean login = false;

    private String kana;

    private String address;

    private String zipCode;

    private String tel;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public String getPassword2() {
        return this.password2;
    }

    public void setPassword2(final String password2) {
        this.password2 = password2;
    }

    @Override
    public boolean isLogin() {
        return this.login;
    }

    public void setLogin(final boolean login) {
        this.login = login;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(final String tel) {
        this.tel = tel;
    }

    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

}
