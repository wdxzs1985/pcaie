package jp.pcaie.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
