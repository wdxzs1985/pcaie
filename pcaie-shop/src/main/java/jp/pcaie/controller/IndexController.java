package jp.pcaie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search() {
        return "index";
    }

    @RequestMapping(value = "/p/{id}", method = RequestMethod.GET)
    public String product() {
        return "index";
    }

}
