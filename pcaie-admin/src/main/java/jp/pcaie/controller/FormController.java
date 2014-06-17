package jp.pcaie.controller;

import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.EstimateBean;
import jp.pcaie.domain.FormBean;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/form")
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
        final Paginate<FormBean> paginate = new Paginate<FormBean>();
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
        //
        formBean.setName(inputFormBean.getName());
        formBean.setKana(inputFormBean.getKana());
        formBean.setZipCode(inputFormBean.getZipCode());
        formBean.setAddress(inputFormBean.getAddress());
        formBean.setEmployment(inputFormBean.getEmployment());
        formBean.setDepartment(inputFormBean.getDepartment());
        formBean.setContactBy(inputFormBean.getContactBy());
        formBean.setEmail(inputFormBean.getEmail());
        formBean.setTel(inputFormBean.getTel());
        formBean.setMaker(inputFormBean.getMaker());
        formBean.setModel(inputFormBean.getModel());
        formBean.setContent(inputFormBean.getContent());
        //
        if (this.formService.validate(formBean, model, locale)) {
            this.formService.save(formBean);
            final String message = this.messageSource.getMessage("admin.form.edit.message",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/form/" + id;
        }
        model.addAttribute("formBean", formBean);
        return "form/edit";
    }

    @RequestMapping(value = "/{id:\\d+}/receive", method = RequestMethod.GET)
    public String doGetReceive(@PathVariable final Integer id, final Model model) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        formBean.setStatus(FormBean.STATUS_ESTIMATE);
        this.formService.save(formBean);
        return "redirect:/form/" + id;
    }

    @RequestMapping(value = "/{id:\\d+}/estimate", method = RequestMethod.GET)
    public String doGetEstimate(@PathVariable final Integer id,
                                final Model model) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        EstimateBean estimateBean = this.formService.getEstimateById(id);
        if (estimateBean == null) {
            estimateBean = new EstimateBean();
            estimateBean.setId(id);
        }
        model.addAttribute("formBean", formBean);
        model.addAttribute("estimateBean", estimateBean);
        return "form/estimate";
    }

    @RequestMapping(value = "/{id:\\d+}/estimate", method = RequestMethod.POST)
    public String doPostEstimate(@PathVariable final Integer id,
                                 @ModelAttribute final EstimateBean inputEstimateBean,
                                 final Model model,
                                 final Locale locale) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        EstimateBean estimateBean = this.formService.getEstimateById(id);
        if (estimateBean == null) {
            estimateBean = new EstimateBean();
            estimateBean.setId(id);
        }

        estimateBean.setUnit1Price(inputEstimateBean.getUnit1Price());
        estimateBean.setUnit1Comment(inputEstimateBean.getUnit1Comment());
        estimateBean.setUnit2Price(inputEstimateBean.getUnit2Price());
        estimateBean.setUnit2Comment(inputEstimateBean.getUnit2Comment());
        estimateBean.setUnit3Price(inputEstimateBean.getUnit3Price());
        estimateBean.setUnit3Comment(inputEstimateBean.getUnit3Comment());
        estimateBean.setUnit4Price(inputEstimateBean.getUnit4Price());
        estimateBean.setUnit4Comment(inputEstimateBean.getUnit4Comment());
        estimateBean.setUnit5Price(inputEstimateBean.getUnit5Price());
        estimateBean.setUnit5Comment(inputEstimateBean.getUnit5Comment());
        estimateBean.setUnit6Price(inputEstimateBean.getUnit6Price());
        estimateBean.setUnit6Comment(inputEstimateBean.getUnit6Comment());
        estimateBean.setUnit7Price(inputEstimateBean.getUnit7Price());
        estimateBean.setUnit7Comment(inputEstimateBean.getUnit7Comment());
        estimateBean.setUnit8Price(inputEstimateBean.getUnit8Price());
        estimateBean.setUnit8Comment(inputEstimateBean.getUnit8Comment());
        estimateBean.setUnit9Price(inputEstimateBean.getUnit9Price());
        estimateBean.setUnit9Comment(inputEstimateBean.getUnit9Comment());

        if (this.formService.validate(estimateBean, model, locale)) {
            formBean.setStatus(FormBean.STATUS_PAY);
            this.formService.save(formBean, estimateBean);
            return "redirect:/form/" + id;
        }

        model.addAttribute("formBean", formBean);
        model.addAttribute("estimateBean", estimateBean);
        return "form/estimate";
    }

    @RequestMapping(value = "/{id:\\d+}/pay", method = RequestMethod.GET)
    public String doGetPay(@PathVariable final Integer id, final Model model) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        formBean.setStatus(FormBean.STATUS_WORKING);
        this.formService.save(formBean);
        return "redirect:/form/" + id;
    }

    @RequestMapping(value = "/{id:\\d+}/return", method = RequestMethod.GET)
    public String doGetReturn(@PathVariable final Integer id, final Model model) {
        final FormBean formBean = this.formService.getFormById(id);
        if (formBean == null) {
            throw new PageNotFoundException();
        }
        formBean.setStatus(FormBean.STATUS_FINISH);
        this.formService.save(formBean);
        return "redirect:/form/" + id;
    }
}
