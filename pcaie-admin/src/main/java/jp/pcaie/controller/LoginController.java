package jp.pcaie.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.pcaie.domain.StaffBean;
import jp.pcaie.domain.UserBean;
import jp.pcaie.service.StaffService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class LoginController {

    @Autowired
    private final StaffService userService = null;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String doGetHome(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final UserBean loginUser = (UserBean) session.getAttribute("LOGIN_USER");
        if (loginUser != null && loginUser.isLogin()) {
            return "redirect:/";
        }

        final String salt = RandomStringUtils.randomAlphanumeric(4);
        session.setAttribute("salt", salt);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doPostLogin(final String email,
                              final String password,
                              final Model model,
                              final Locale locale,
                              @RequestHeader("Referer") final String referer,
                              final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final String salt = (String) session.getAttribute("salt");
        session.removeAttribute("salt");

        if (StringUtils.isNotBlank(referer)) {
            final StaffBean loginUser = this.userService.doLogin(email,
                                                                 password,
                                                                 salt,
                                                                 model,
                                                                 locale);
            if (loginUser != null) {
                loginUser.setLogin(true);
                session.setAttribute("LOGIN_USER", loginUser);
                if (StringUtils.contains(referer, request.getRequestURI())) {
                    return "redirect:/";
                } else {
                    return "redirect:" + referer;
                }
            } else {
                model.addAttribute("email", email);
            }
        }
        return this.doGetHome(request);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doGetLogout(final Model model,
                              final SessionStatus status,
                              final HttpServletRequest request,
                              final HttpServletResponse response) {
        status.setComplete();
        return "redirect:/";
    }
}
