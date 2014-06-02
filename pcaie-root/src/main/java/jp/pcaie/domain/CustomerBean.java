package jp.pcaie.domain;

public class CustomerBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = -5928364611016970537L;

    private String name;

    private String kana;

    private String employment;

    private String department;

    private String email;

    private String email2;

    private String tel;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getKana() {
        return this.kana;
    }

    public void setKana(final String kana) {
        this.kana = kana;
    }

    public String getEmployment() {
        return this.employment;
    }

    public void setEmployment(final String employment) {
        this.employment = employment;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail2() {
        return this.email2;
    }

    public void setEmail2(final String email2) {
        this.email2 = email2;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(final String tel) {
        this.tel = tel;
    }

}
