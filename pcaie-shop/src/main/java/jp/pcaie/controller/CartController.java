package jp.pcaie.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/cart")
@SessionAttributes("LOGIN_USER")
public class CartController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "cart/index";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> getList() {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add() {
        return "ok";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update() {
        return "ok";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String remove() {
        return "ok";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public String clear() {
        return "ok";
    }

}
