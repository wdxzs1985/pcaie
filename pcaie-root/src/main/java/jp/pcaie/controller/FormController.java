package jp.pcaie.controller;

import jp.pcaie.domain.FormBean;
import jp.pcaie.service.FormSerivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/form")
public class FormController {

    @Autowired
    private FormSerivce formService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(final Model model) {
        final FormBean formBean = new FormBean();
        model.addAttribute("formBean", formBean);
        return "form/input";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String confirm(@RequestParam final FormBean formBean, final Model model) {
        model.addAttribute("formBean", formBean);
        if (this.formService.validate(formBean)) {
            return "form/confirm";
        }
        return "form/input";
    }
}
