package jp.pcaie.controller;

import java.util.Locale;

import jp.pcaie.domain.FormBean;
import jp.pcaie.mail.FormNotification;
import jp.pcaie.service.FormSerivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/form")
public class FormController {

    @Autowired
    private FormSerivce formService;
    @Autowired
    private FormNotification formNotification = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(final Model model) {
        final FormBean formBean = new FormBean();
        model.addAttribute("formBean", formBean);
        return "form/input";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String confirm(@ModelAttribute final FormBean formBean, final Model model, final Locale locale) {
        model.addAttribute("formBean", formBean);
        if (this.formService.validate(formBean, model, locale)) {
            return "form/confirm";
        }
        return "form/input";
    }

    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public String finishi(@ModelAttribute final FormBean formBean, final Model model, final Locale locale) {
        if (this.formService.validate(formBean, model, locale)) {
            this.formService.save(formBean);
            this.formNotification.send(formBean, locale);
            return "redirect:/form/finish";
        }
        model.addAttribute("formBean", formBean);
        return "form/input";
    }

    @RequestMapping(value = "finish", method = RequestMethod.GET)
    public String finishi(final Model model, final Locale locale) {
        return "form/finish";
    }
}
