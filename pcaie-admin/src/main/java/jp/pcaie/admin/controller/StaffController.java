package jp.pcaie.admin.controller;

import java.util.Locale;
import java.util.Map;

import jp.pcaie.admin.domain.StaffBean;
import jp.pcaie.admin.service.StaffService;
import jp.pcaie.common.Paginate;
import jp.pcaie.common.controller.ControllerUtils;
import jp.pcaie.common.exception.PageNotFoundException;

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
    private final StaffService userService = null;
    @Autowired
    private final MessageSource messageSource = null;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String doGetIndex(@RequestParam(required = false) final String email, @RequestParam(required = false) final String nickname, @RequestParam(required = false, defaultValue = "1") final int page, final Model model) {
        final Paginate<StaffBean> paginate = new Paginate<StaffBean>();
        paginate.setPage(page);

        final Map<String, Object> params = paginate.getParams();
        params.put("email", ControllerUtils.decodeQuery(email));
        params.put("nickname", ControllerUtils.decodeQuery(nickname));

        this.userService.doSearch(paginate);

        model.addAttribute("paginate", paginate);
        model.addAllAttributes(params);
        return "staff/index";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String doGetEdit(@PathVariable final Integer id, final Model model) {
        final StaffBean userBean = this.userService.getUserById(id);
        if (userBean == null) {
            throw new PageNotFoundException();
        }
        model.addAttribute("userBean", userBean);
        return "staff/edit";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.POST)
    public String doPostEdit(@PathVariable final Integer id, @RequestParam final String email, @RequestParam final String nickname, @RequestParam final String password, @RequestParam final String password2, @RequestParam final String role, final Model model, final Locale locale, final RedirectAttributes redirectAttributes) {
        final StaffBean userBean = this.userService.getUserById(id);
        if (userBean == null) {
            throw new PageNotFoundException();
        }
        userBean.setEmail(email);
        userBean.setNickname(nickname);
        userBean.setPassword(password);
        userBean.setPassword2(password2);
        final boolean isUpdated = this.userService.updateUser(userBean,
                                                              model,
                                                              locale);
        if (isUpdated) {
            final String message = this.messageSource.getMessage("user.edit.message",
                                                                 null,
                                                                 locale);
            redirectAttributes.addFlashAttribute("message", message);
            final StaffBean loginUser = (StaffBean) model.asMap()
                                                         .get("LOGIN_USER");
            if (id == loginUser.getId()) {
                model.addAttribute("LOGIN_USER", userBean);
            }
            return "redirect:/staff/" + id;
        } else {
            model.addAttribute("userBean", userBean);
            return "staff/edit";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String doGetCreate(final Model model) {
        return "staff/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String doPostCreate(@RequestParam final String email, @RequestParam final String nickname, @RequestParam final String password, @RequestParam final String password2, @RequestParam final String role, final Model model, final Locale locale, final RedirectAttributes redirectAttributes) {
        final StaffBean userBean = new StaffBean();
        userBean.setEmail(email);
        userBean.setNickname(nickname);
        userBean.setPassword(password);
        userBean.setPassword2(password2);
        final boolean isCreated = this.userService.createUser(userBean,
                                                              model,
                                                              locale);
        if (isCreated) {
            return "redirect:/staff";
        } else {
            model.addAttribute("userBean", userBean);
            return "staff/create";
        }
    }

    @RequestMapping(value = "/{id:\\d+}/delete", method = RequestMethod.GET)
    public String doGetDelete(@PathVariable final Integer id, final Model model) {
        final StaffBean userBean = this.userService.getUserById(id);
        if (userBean == null) {
            throw new PageNotFoundException();
        }
        this.userService.delete(userBean);
        return "redirect:/staff";
    }
}
