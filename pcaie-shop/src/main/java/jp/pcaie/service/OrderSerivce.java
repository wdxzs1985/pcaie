package jp.pcaie.service;

import java.util.ArrayList;
import java.util.List;

import jp.pcaie.domain.OrderItemBean;
import jp.pcaie.domain.ShopUserBean;

import org.springframework.stereotype.Service;

@Service
public class OrderSerivce {

    private List<OrderItemBean> shoppingCart = new ArrayList<OrderItemBean>();

    public void add(final ShopUserBean shopUserBean,
                    final OrderItemBean orderBean) {
        boolean isFound = false;
        for (final OrderItemBean item : this.shoppingCart) {
            if (item.getId() == orderBean.getId()) {
                isFound = true;
                int quantity = item.getQuantity();
                quantity += orderBean.getQuantity();
                item.setQuantity(quantity);
                break;
            }
        }
        if (!isFound) {
            this.shoppingCart.add(orderBean);
        }
    }

    public List<OrderItemBean> getList(final ShopUserBean shopUserBean) {
        return this.shoppingCart;
    }

    public void clear(final ShopUserBean shopUserBean) {
        this.shoppingCart.clear();
    }

}
