package jp.pcaie.domain;

import java.util.Date;

public class FormBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = -3908089675700714078L;

    public static final int STATUS_UNREAD = 0;

    public static final int STATUS_READ = 1;

    public static final int STATUS_REPLIED = 2;

    public static final int STATUS_SUCCESS = 3;

    private CustomerBean customerBean = new CustomerBean();

    private String maker;

    private String model;

    private String content;

    private Integer status = STATUS_UNREAD;

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
