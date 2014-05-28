package jp.pcaie.admin;

import jp.pcaie.admin.interceptor.AccessLogInterceptor;
import jp.pcaie.admin.interceptor.AuthLoginInterceptor;
import jp.pcaie.admin.interceptor.AutoLoginInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.CookieGenerator;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.accessLog())
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**");
        //
        registry.addInterceptor(this.autoLogin())
                .addPathPatterns("/**")
                .excludePathPatterns("/signup")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/forgot")
                .excludePathPatterns("/forgot/**")
                .excludePathPatterns("/resources/**");
        //
        registry.addInterceptor(this.authLogin())
                .addPathPatterns("/**")
                .excludePathPatterns("/signup")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/forgot")
                .excludePathPatterns("/forgot/**")
                .excludePathPatterns("/resources/**");
    }

    protected AccessLogInterceptor accessLog() {
        return new AccessLogInterceptor();
    }

    protected AutoLoginInterceptor autoLogin() {
        return new AutoLoginInterceptor();
    }

    protected AuthLoginInterceptor authLogin() {
        AuthLoginInterceptor authLogin = new AuthLoginInterceptor();
        authLogin.setForward("/login");
        return authLogin;
    }

    @Bean
    public CookieGenerator cookieGenerator() {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieHttpOnly(true);
        cookieGenerator.setCookieMaxAge(1000 * 60 * 60 * 24 * 30);
        return cookieGenerator;
    }
}
