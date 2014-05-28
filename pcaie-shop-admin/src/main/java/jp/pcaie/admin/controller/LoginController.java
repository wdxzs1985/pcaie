package jp.pcaie.admin.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.pcaie.admin.domain.UserBean;
import jp.pcaie.admin.service.UserService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.CookieGenerator;

@Controller
@SessionAttributes("LOGIN_USER")
public class LoginController {

    @Autowired
    private final UserService userService = null;
    @Autowired
    private final CookieGenerator cookieLoginUser = null;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String doGetHome(final Model model, final HttpServletRequest request) {
        if (model.containsAttribute("LOGIN_USER")) {
            return "redirect:/";
        }
        final String salt = RandomStringUtils.randomAlphanumeric(4);
        final HttpSession session = request.getSession();
        session.setAttribute("salt", salt);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doPostLogin(final String email, final String password, final Model model, final Locale locale, @RequestHeader("Referer") final String referer, final HttpServletRequest request, final HttpServletResponse response) {
        if (StringUtils.isNotBlank(referer)) {
            final HttpSession session = request.getSession();
            final String salt = (String) session.getAttribute("salt");
            final UserBean loginUser = this.userService.doLogin(email,
                                                                password,
                                                                salt,
                                                                model,
                                                                locale);
            if (loginUser != null) {
                model.addAttribute("LOGIN_USER", loginUser);
                final String token = this.userService.postLogin(loginUser);
                this.cookieLoginUser.addCookie(response, token);
                session.removeAttribute("salt");
                if (StringUtils.contains(referer, request.getRequestURI())) {
                    return "redirect:/";
                } else {
                    return "redirect:" + referer;
                }
            }
        }
        return this.doGetHome(model, request);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String doGetSignup(final Model model) {
        return "signup/input";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String doPostSignup(final String email, final String password, final String nickname, final Model model, final Locale locale) {
        final boolean isSuccess = this.userService.doSignup(email,
                                                            password,
                                                            nickname,
                                                            model,
                                                            locale);
        if (isSuccess) {
            return "signup/finish";
        }
        model.addAttribute("email", email);
        model.addAttribute("nickname", nickname);
        return "signup/input";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String doGetForgot() {
        return "forgot/input-email";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public String doPostForgot(final String email, final Model model, final Locale locale) {
        final boolean isSuccess = this.userService.sendForgotMail(email,
                                                                  model,
                                                                  locale);
        if (isSuccess) {
            return "forgot/send-mail";
        }
        return "forgot/input-email";
    }

    @RequestMapping(value = "/forgot/{token}", method = RequestMethod.GET)
    public String doGetChangePassword(@PathVariable final String token, final Model model, final Locale locale) {
        final boolean isSuccess = this.userService.findPasswordHistory(token,
                                                                       model,
                                                                       locale);
        if (isSuccess) {
            model.addAttribute("token", token);
            return "forgot/input-password";
        }
        return "forgot/input-email";
    }

    @RequestMapping(value = "/forgot/{token}", method = RequestMethod.POST)
    public String doPostChangePassword(@PathVariable final String token, final String password, final Model model, final Locale locale) {
        boolean isSuccess = this.userService.findPasswordHistory(token,
                                                                 model,
                                                                 locale);
        if (isSuccess) {
            isSuccess = this.userService.changePassword(token,
                                                        password,
                                                        model,
                                                        locale);
            if (isSuccess) {
                return "forgot/finish";
            }
            return "forgot/input-password";
        }
        return "redirect:/forgot";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doGetLogout(final Model model, final SessionStatus status, final HttpServletRequest request, final HttpServletResponse response) {
        status.setComplete();
        final UserBean userBean = (UserBean) model.asMap().get("LOGIN_USER");
        this.userService.logout(userBean);
        this.cookieLoginUser.removeCookie(response);
        return "redirect:/";
    }
}
