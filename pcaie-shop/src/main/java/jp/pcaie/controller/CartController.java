package jp.pcaie.controller;

import java.util.List;

import jp.pcaie.domain.OrderItemBean;
import jp.pcaie.domain.ShopUserBean;
import jp.pcaie.service.OrderSerivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/cart")
@SessionAttributes({ "LOGIN_USER" })
public class CartController {

    @Autowired
    private OrderSerivce orderService = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "cart/index";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderItemBean> getList(@ModelAttribute("LOGIN_USER") final ShopUserBean shopUserBean) {
        return this.orderService.getList(shopUserBean);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute("LOGIN_USER") final ShopUserBean shopUserBean,
                      @ModelAttribute final OrderItemBean orderBean) {
        this.orderService.add(shopUserBean, orderBean);
        return "ok";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute("LOGIN_USER") final ShopUserBean shopUserBean) {
        return "ok";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String remove(@ModelAttribute("LOGIN_USER") final ShopUserBean shopUserBean) {
        return "ok";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public String clear(@ModelAttribute("LOGIN_USER") final ShopUserBean shopUserBean) {
        this.orderService.clear(shopUserBean);
        return "ok";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(@ModelAttribute("LOGIN_USER") final ShopUserBean shopUserBean,
                          final Model model) {
        final List<OrderItemBean> shoppingCart = this.orderService.getList(shopUserBean);
        model.addAttribute("shoppingCart", shoppingCart);
        return "cart/confirm";
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order() {
        return "redirect:/cart/finish";
    }

    @RequestMapping(value = "/finish", method = RequestMethod.GET)
    public String finish() {
        return "cart/finish";
    }

}
