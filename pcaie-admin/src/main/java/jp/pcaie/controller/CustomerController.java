package jp.pcaie.controller;

import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.CustomerBean;
import jp.pcaie.exception.PageNotFoundException;
import jp.pcaie.service.CustomerService;
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
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerSerivce = null;
    @Autowired
    private final MessageSource messageSource = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam(required = false) final String email,
                        @RequestParam(required = false) final String name,
                        @RequestParam(required = false, defaultValue = "1") final int page,
                        final Model model) {
        final Paginate<CustomerBean> paginate = new Paginate<CustomerBean>();
        paginate.setPage(page);

        final Map<String, Object> params = paginate.getParams();
        params.put("email", email);
        params.put("name", name);

        this.customerSerivce.doSearch(paginate);

        model.addAttribute("paginate", paginate);
        return "customer/index";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String doGetView(@PathVariable final Integer id, final Model model) {
        final CustomerBean customerBean = this.customerSerivce.getCustomerById(id);
        if (customerBean == null) {
            throw new PageNotFoundException();
        }
        model.addAttribute("customerBean", customerBean);
        return "customer/view";
    }

    @RequestMapping(value = "/{id:\\d+}/edit", method = RequestMethod.GET)
    public String doGetEdit(@PathVariable final Integer id, final Model model) {
        final CustomerBean customerBean = this.customerSerivce.getCustomerById(id);
        if (customerBean == null) {
            throw new PageNotFoundException();
        }
        model.addAttribute("customerBean", customerBean);
        return "customer/edit";
    }

    @RequestMapping(value = "/{id:\\d+}/edit", method = RequestMethod.POST)
    public String doPostEdit(@PathVariable final Integer id,
                             @ModelAttribute final CustomerBean inputCustomerBean,
                             final Model model,
                             final Locale locale,
                             final RedirectAttributes redirectAttributes) {
        final CustomerBean customerBean = this.customerSerivce.getCustomerById(id);
        if (customerBean == null) {
            throw new PageNotFoundException();
        }

        customerBean.setName(inputCustomerBean.getName());
        customerBean.setKana(inputCustomerBean.getKana());
        customerBean.setEmployment(inputCustomerBean.getEmployment());
        customerBean.setDepartment(inputCustomerBean.getDepartment());
        customerBean.setEmail(inputCustomerBean.getEmail());
        customerBean.setTel(inputCustomerBean.getTel());

        if (this.customerSerivce.validate(customerBean, model, locale)) {
            this.customerSerivce.update(customerBean);
            final String message = this.messageSource.getMessage("admin.customer.edit.message",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/customer/" + id;
        }
        model.addAttribute("customerBean", customerBean);
        return "customer/edit";
    }
}
