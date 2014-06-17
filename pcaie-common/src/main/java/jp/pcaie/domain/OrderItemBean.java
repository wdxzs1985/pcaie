package jp.pcaie.domain;

public class OrderItemBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = -528118841412321108L;

    private ProductBean productBean;

    private Integer quantity;

    private Integer total;

    public ProductBean getProductBean() {
        return this.productBean;
    }

    public void setProductBean(final ProductBean productBean) {
        this.productBean = productBean;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

}
