package jp.pcaie;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@ImportResource({ "classpath:/config/app-config.xml",
        "classpath:/config/mybatis-config.xml",
        "classpath:/config/mvc-config.xml" })
public class Application implements ServletContextInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        this.log.info("setSessionTrackingModes: " + SessionTrackingMode.COOKIE);
        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
    }
}
