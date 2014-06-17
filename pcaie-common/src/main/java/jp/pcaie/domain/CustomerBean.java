package jp.pcaie.domain;

public class CustomerBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6690459641663293003L;

    public static final int CONTACT_BY_EMAIL = 0;

    public static final int CONTACT_BY_TEL = 1;

    private String name;

    private String kana;

    private String employment;

    private String department;

    private String zipCode;

    private String address;

    private Integer contactBy = CONTACT_BY_EMAIL;

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

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Integer getContactBy() {
        return this.contactBy;
    }

    public void setContactBy(final Integer contactBy) {
        this.contactBy = contactBy;
    }

}
