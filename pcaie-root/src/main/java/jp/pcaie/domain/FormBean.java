package jp.pcaie.domain;

public class FormBean extends DtoBean {

    public static Integer CONTACT_BY_EMAIL = 0;

    public static Integer CONTACT_BY_TEL = 1;
    /**
     * 
     */
    private static final long serialVersionUID = -6821328254375180614L;

    private String name;

    private String kana;

    private String employment;

    private String department;

    private Integer contactBy = CONTACT_BY_EMAIL;

    private String email;

    private String email2;

    private String tel;

    private String maker;

    private String model;

    private String content;

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

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(final String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Integer getContactBy() {
        return this.contactBy;
    }

    public void setContactBy(final Integer contactBy) {
        this.contactBy = contactBy;
    }

}
