package jp.pcaie.admin.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.pcaie.admin.domain.UserBean;
import jp.pcaie.admin.service.UserService;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.CookieGenerator;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

    public static final String LOGIN_USER = "LOGIN_USER";

    public static final String COOKIE_LOGIN_USER = "COOKIE_LOGIN_USER";

    @Autowired
    private final CookieGenerator cookieLoginUser = null;
    @Autowired
    private final UserService userService = null;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final HttpSession session = request.getSession();
        UserBean loginUser = (UserBean) session.getAttribute(LOGIN_USER);
        if (loginUser != null) {
            return true;
        }
        final Cookie cookie = this.findCookie(request, COOKIE_LOGIN_USER);
        if (cookie != null) {
            final String token = cookie.getValue();
            loginUser = this.userService.doLogin(token);
            if (loginUser != null) {
                session.setAttribute(LOGIN_USER, loginUser);
            }
        }
        return true;
    }

    private Cookie findCookie(final HttpServletRequest request, final String cookieName) {
        final Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (final Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
