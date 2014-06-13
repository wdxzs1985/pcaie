package jp.pcaie.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.pcaie.domain.ShopUserBean;
import jp.pcaie.domain.UserBean;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class DefaultUserInterceptor extends HandlerInterceptorAdapter {

    public static final String LOGIN_USER = "LOGIN_USER";

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final HttpSession session = request.getSession();
        final UserBean loginUser = (UserBean) session.getAttribute(LOGIN_USER);
        if (loginUser == null) {
            session.setAttribute(LOGIN_USER, new ShopUserBean());
        }
        return true;
    }

}
