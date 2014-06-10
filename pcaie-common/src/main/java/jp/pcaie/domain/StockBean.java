package jp.pcaie.domain;

public class StockBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6650673688967807327L;

    private ProductBean productBean = new ProductBean();

    private Integer stock;

    private Integer safeStock;

    private Integer price;

    private String notificationEmail;

    public ProductBean getProductBean() {
        return this.productBean;
    }

    public void setProductBean(final ProductBean productBean) {
        this.productBean = productBean;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(final Integer stock) {
        this.stock = stock;
    }

    public Integer getSafeStock() {
        return this.safeStock;
    }

    public void setSafeStock(final Integer safeStock) {
        this.safeStock = safeStock;
    }

    public String getNotificationEmail() {
        return this.notificationEmail;
    }

    public void setNotificationEmail(final String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }
}
