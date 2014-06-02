package jp.pcaie.domain;

public class FormBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = -1891094883945223225L;

    public static final int CONTACT_BY_EMAIL = 0;

    public static final int CONTACT_BY_TEL = 1;

    private CustomerBean customerBean = new CustomerBean();

    private Integer contactBy = CONTACT_BY_EMAIL;

    private String maker;

    private String model;

    private String content;

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

    public CustomerBean getCustomerBean() {
        return this.customerBean;
    }

    public void setCustomerBean(final CustomerBean customerBean) {
        this.customerBean = customerBean;
    }

}
