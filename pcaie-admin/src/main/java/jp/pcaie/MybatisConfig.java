package jp.pcaie;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@MapperScan("jp.pcaie.mapper")
@EnableTransactionManagement
public class MybatisConfig {

    private String typeAliasesPackage = "jp.pcaie.domain";

    private org.apache.tomcat.jdbc.pool.DataSource pool;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(@Value(value = "${spring.datasource.driverClassName}") final String driverClassName,
                                 @Value(value = "${spring.datasource.url}") final String url,
                                 @Value(value = "${spring.datasource.username}") final String username,
                                 @Value(value = "${spring.datasource.password}") final String password,
                                 @Value(value = "${spring.datasource.initial-size:10}") final int initialSize,
                                 @Value(value = "${spring.datasource.max-active:100}") final int maxActive,
                                 @Value(value = "${spring.datasource.max-idle:8}") final int maxIdle,
                                 @Value(value = "${spring.datasource.min-idle:8}") final int minIdle) {
        this.pool = new org.apache.tomcat.jdbc.pool.DataSource();
        this.pool.setDriverClassName(driverClassName);
        this.pool.setUrl(url);
        this.pool.setUsername(username);
        this.pool.setPassword(password);
        this.pool.setInitialSize(initialSize);
        this.pool.setMaxActive(maxActive);
        this.pool.setMaxIdle(maxIdle);
        this.pool.setMinIdle(minIdle);
        return this.pool;
    }

    @PreDestroy
    public void close() {
        if (this.pool != null) {
            this.pool.close();
        }
    }

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(final DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(this.typeAliasesPackage);
        return sessionFactory.getObject();
    }

}
