package jp.pcaie.controller;

import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.FormBean;
import jp.pcaie.domain.StaffBean;
import jp.pcaie.service.FormService;
import jp.pcaie.support.Paginate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/form")
@SessionAttributes("LOGIN_USER")
public class FormController {

    @Autowired
    private FormService formService = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam(required = false) final String email,
                        @RequestParam(required = false) final String name,
                        @RequestParam(required = false, defaultValue = "1") final int page,
                        final Model model) {
        final Paginate<StaffBean> paginate = new Paginate<StaffBean>();
        paginate.setPage(page);

        final Map<String, Object> params = paginate.getParams();
        params.put("email", email);
        params.put("name", name);

        this.formService.doSearch(paginate);

        model.addAttribute("paginate", paginate);
        return "form/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String doGetCreate(final Model model) {
        final FormBean formBean = new FormBean();
        model.addAttribute("formBean", formBean);
        return "form/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String doPostCreate(@ModelAttribute final FormBean formBean,
                               final Model model,
                               final Locale locale,
                               final RedirectAttributes redirectAttributes) {
        if (this.formService.validate(formBean, model, locale)) {
            this.formService.save(formBean);
            return "redirect:/form";
        }
        model.addAttribute("formBean", formBean);
        return "form/create";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String doGetEdit(@PathVariable final Integer id, final Model model) {
        return "form/edit";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.POST)
    public String doPostEdit(@PathVariable final Integer id,
                             final Model model,
                             final Locale locale,
                             final RedirectAttributes redirectAttributes) {
        return "redirect:/form/" + id;
    }

}
