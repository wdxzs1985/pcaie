package jp.pcaie.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.pcaie.domain.UserBean;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

    public static final String LOGIN_USER = "LOGIN_USER";

    private String forward = null;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final HttpSession session = request.getSession();
        final UserBean loginUser = (UserBean) session.getAttribute(LOGIN_USER);
        if (loginUser != null) {
            return true;
        }
        request.getRequestDispatcher(this.forward).forward(request, response);
        return false;
    }

    public String getForward() {
        return this.forward;
    }

    public void setForward(final String forward) {
        this.forward = forward;
    }

}
