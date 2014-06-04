package jp.pcaie.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class AccessLogInterceptor extends HandlerInterceptorAdapter {

    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final String sid = request.getRequestedSessionId();
        final String method = request.getMethod();
        final String uri = request.getRequestURI();
        final String encode = request.getCharacterEncoding();
        final String referrer = request.getHeader("Referer");
        final String ua = request.getHeader("user-agent");

        final StringBuilder b = new StringBuilder();
        b.append("[SID]=").append(sid).append(" ");
        b.append("[METHOD]=").append(method).append(" ");
        b.append("[URI]=").append(uri).append(" ");
        b.append("[ENCODE]=").append(encode).append(" ");
        b.append("[REF]=").append(referrer).append(" ");
        b.append("[UA]=").append(ua).append(" ");

        this.log.info(b.toString());

        return super.preHandle(request, response, handler);
    }

}
