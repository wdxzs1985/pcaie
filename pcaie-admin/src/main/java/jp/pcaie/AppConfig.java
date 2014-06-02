package jp.pcaie;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
public class AppConfig {

    @Bean
    public JavaMailSender mailSender(@Value(value = "${mail.host}") final String host,
                                     @Value(value = "${mail.port}") final int port,
                                     @Value(value = "${mail.username}") final String username,
                                     @Value(value = "${mail.password}") final String password) {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }

    @Bean
    public VelocityEngine velocityEngine() {
        final VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
        final Properties velocityProperties = new Properties();
        velocityProperties.put("resource.loader", "class");
        velocityProperties.put("class.resource.loader.class",
                               "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setVelocityProperties(velocityProperties);
        return velocityEngine.getObject();
    }

    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("common-i18n",
                                   "common-bean",
                                   "common-validate",
                                   "i18n",
                                   "bean",
                                   "validate");
        return messageSource;
    }
}
