package jp.pcaie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@ImportResource({ "config/app-config.xml",
        "config/mybatis-config.xml",
        "config/mvc-config.xml" })
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
