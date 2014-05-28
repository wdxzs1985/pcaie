package jp.pcaie.admin;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.mycompany.com");
        return mailSender;
    }

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
        Properties velocityProperties = new Properties();
        velocityProperties.put("resource.loader", "class");
        velocityProperties.put("class.resource.loader.class",
                               "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setVelocityProperties(velocityProperties);
        return velocityEngine.getObject();
    }

}
