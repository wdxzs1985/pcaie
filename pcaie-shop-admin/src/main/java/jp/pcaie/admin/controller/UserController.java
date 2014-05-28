package jp.pcaie.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
public class UserController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String doGetProfile(final Model model) {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String doPostProfile(final String nickname, final Model model) {
        return "redirect:/profile";
    }
}
