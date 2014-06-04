package jp.pcaie.controller;

import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.CustomerBean;
import jp.pcaie.domain.FormBean;
import jp.pcaie.domain.StaffBean;
import jp.pcaie.exception.PageNotFoundException;
import jp.pcaie.service.FormService;
import jp.pcaie.support.Paginate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    @Autowired
    private final MessageSource messageSource = null;

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
    public String doPostCreate(@ModelAttribute final FormBean inputFormBean,
                               final Model model,
                               final Locale locale,
                               final RedirectAttributes redirectAttributes) {
        if (this.formService.validate(inputFormBean, model, locale)) {
            this.formService.save(inputFormBean);
            return "redirect:/form";
        }
        model.addAttribute("formBean", inputFormBean);
        return "form/create";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String doGetView(@PathVariable final Integer id, final Model model) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        } else if (formBean.getStatus() == FormBean.STATUS_UNREAD) {
            formBean.setStatus(FormBean.STATUS_READ);
            this.formService.update(formBean);
        }
        model.addAttribute("formBean", formBean);
        return "form/view";
    }

    @RequestMapping(value = "/{id:\\d+}/edit", method = RequestMethod.GET)
    public String doGetEdit(@PathVariable final Integer id, final Model model) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        model.addAttribute("formBean", formBean);
        return "form/edit";
    }

    @RequestMapping(value = "/{id:\\d+}/edit", method = RequestMethod.POST)
    public String doPostEdit(@PathVariable final Integer id,
                             @ModelAttribute final FormBean inputFormBean,
                             final Model model,
                             final Locale locale,
                             final RedirectAttributes redirectAttributes) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        // for validate
        final CustomerBean customerBean = formBean.getCustomerBean();
        customerBean.setEmail2(customerBean.getEmail());
        //
        formBean.setMaker(inputFormBean.getMaker());
        formBean.setModel(inputFormBean.getModel());
        formBean.setContent(inputFormBean.getContent());

        if (this.formService.validate(formBean, model, locale)) {
            this.formService.update(formBean);
            final String message = this.messageSource.getMessage("admin.form.edit.message",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/form/" + id;
        }
        model.addAttribute("formBean", formBean);
        return "form/edit";
    }

    @RequestMapping(value = "/{id:\\d+}/reply", method = RequestMethod.GET)
    public String doGetReply(@PathVariable final Integer id, final Model model) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        formBean.setStatus(FormBean.STATUS_REPLIED);
        this.formService.update(formBean);
        return "redirect:/form/" + id;
    }
}
