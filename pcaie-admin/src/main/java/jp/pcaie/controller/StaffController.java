package jp.pcaie.controller;

import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.StaffBean;
import jp.pcaie.exception.PageNotFoundException;
import jp.pcaie.service.StaffService;
import jp.pcaie.support.Paginate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/staff")
@SessionAttributes("LOGIN_USER")
public class StaffController {

    @Autowired
    private final StaffService staffService = null;
    @Autowired
    private final MessageSource messageSource = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String doGetIndex(@RequestParam(required = false) final String email,
                             @RequestParam(required = false) final String name,
                             @RequestParam(required = false, defaultValue = "1") final int page,
                             final Model model) {
        final Paginate<StaffBean> paginate = new Paginate<StaffBean>();
        paginate.setPage(page);

        final Map<String, Object> params = paginate.getParams();
        params.put("email", email);
        params.put("name", name);

        this.staffService.doSearch(paginate);

        model.addAttribute("paginate", paginate);
        return "staff/index";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String doGetEdit(@PathVariable final Integer id, final Model model) {
        final StaffBean staffBean = this.staffService.getUserById(id);
        if (staffBean == null) {
            throw new PageNotFoundException();
        }
        model.addAttribute("staffBean", staffBean);
        return "staff/edit";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.POST)
    public String doPostEdit(@PathVariable final Integer id,
                             @RequestParam final String email,
                             @RequestParam final String name,
                             @RequestParam final String password,
                             @RequestParam final String password2,
                             @RequestParam final Integer role,
                             final Model model,
                             final Locale locale,
                             final RedirectAttributes redirectAttributes) {
        final StaffBean staffBean = this.staffService.getUserById(id);
        if (staffBean == null) {
            throw new PageNotFoundException();
        }
        staffBean.setEmail(email);
        staffBean.setName(name);
        staffBean.setPassword(password);
        staffBean.setPassword2(password2);
        staffBean.setRole(role);
        final boolean isUpdated = this.staffService.updateUser(staffBean,
                                                               model,
                                                               locale);
        if (isUpdated) {
            final String message = this.messageSource.getMessage("admin.staff.edit.message",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            final StaffBean loginUser = (StaffBean) model.asMap()
                                                         .get("LOGIN_USER");
            if (loginUser.getId().equals(id)) {
                loginUser.setEmail(email);
                loginUser.setName(name);
                loginUser.setRole(role);
            }
            return "redirect:/staff/" + id;
        } else {
            model.addAttribute("staffBean", staffBean);
            return "staff/edit";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String doGetCreate(final Model model) {
        final StaffBean staffBean = new StaffBean();
        model.addAttribute("staffBean", staffBean);
        return "staff/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String doPostCreate(@RequestParam final String email,
                               @RequestParam final String name,
                               @RequestParam final String password,
                               @RequestParam final String password2,
                               @RequestParam final String role,
                               final Model model,
                               final Locale locale,
                               final RedirectAttributes redirectAttributes) {
        final StaffBean staffBean = new StaffBean();
        staffBean.setEmail(email);
        staffBean.setName(name);
        staffBean.setPassword(password);
        staffBean.setPassword2(password2);
        final boolean isCreated = this.staffService.createUser(staffBean,
                                                               model,
                                                               locale);
        if (isCreated) {
            return "redirect:/staff";
        } else {
            model.addAttribute("staffBean", staffBean);
            return "staff/create";
        }
    }

    @RequestMapping(value = "/{id:\\d+}/delete", method = RequestMethod.GET)
    public String doGetDelete(@PathVariable final Integer id, final Model model) {
        final StaffBean staffBean = this.staffService.getUserById(id);
        if (staffBean == null) {
            throw new PageNotFoundException();
        }
        this.staffService.delete(staffBean);
        return "redirect:/staff";
    }
}
