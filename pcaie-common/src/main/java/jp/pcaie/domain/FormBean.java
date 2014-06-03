package jp.pcaie.domain;

import java.util.Date;

public class FormBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = -3908089675700714078L;

    public static final int CONTACT_BY_EMAIL = 0;

    public static final int CONTACT_BY_TEL = 1;

    public static final int STATUS_WAITING = 0;

    private CustomerBean customerBean = new CustomerBean();

    private Integer contactBy = CONTACT_BY_EMAIL;

    private String maker;

    private String model;

    private String content;

    private Integer status = STATUS_WAITING;

    private Date createDate = null;

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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

}
