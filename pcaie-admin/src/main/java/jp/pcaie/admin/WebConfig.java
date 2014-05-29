package jp.pcaie.admin;

import jp.pcaie.admin.interceptor.AccessLogInterceptor;
import jp.pcaie.admin.interceptor.AuthLoginInterceptor;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ControllerAdvice
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.accessLog())
                .addPathPatterns("/**")
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

    @Bean
    public AccessLogInterceptor accessLog() {
        return new AccessLogInterceptor();
    }

    @Bean
    public AuthLoginInterceptor authLogin() {
        final AuthLoginInterceptor authLogin = new AuthLoginInterceptor();
        authLogin.setForward("/login");
        return authLogin;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }
}
