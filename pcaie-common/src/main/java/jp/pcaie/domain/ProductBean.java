package jp.pcaie.domain;

public class ProductBean extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = -1017980183964728671L;

    private String name;

    private String content;

    private Integer price;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
