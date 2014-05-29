package jp.pcaie.admin;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("jp.pcaie.admin.mapper")
@EnableTransactionManagement
public class MybatisConfig {

    private String driverClassName = "com.mysql.jdbc.Driver";

    private String url = "jdbc:mysql://localhost/pcaie?useUnicode=yes&characterEncoding=UTF-8";

    private String username = "root";

    private String password = "root";

    private final int maxActive = 100;

    private final int maxIdle = 8;

    private final int minIdle = 8;

    private final int initialSize = 10;

    private String validationQuery;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    private boolean testWhileIdle;

    private Integer timeBetweenEvictionRunsMillis;

    private Integer minEvictableIdleTimeMillis;

    private Integer maxWaitMillis;

    private String jdbcInterceptors;
    private final long validationInterval = 30000;
    private org.apache.tomcat.jdbc.pool.DataSource pool;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        this.pool = new org.apache.tomcat.jdbc.pool.DataSource();
        this.pool.setDriverClassName(this.getDriverClassName());
        this.pool.setUrl(this.getUrl());
        if (this.getUsername() != null) {
            this.pool.setUsername(this.getUsername());
        }
        if (this.getPassword() != null) {
            this.pool.setPassword(this.getPassword());
        }
        this.pool.setInitialSize(this.getInitialSize());
        this.pool.setMaxActive(this.getMaxActive());
        this.pool.setMaxIdle(this.getMaxIdle());
        this.pool.setMinIdle(this.getMinIdle());
        this.pool.setTestOnBorrow(this.isTestOnBorrow());
        this.pool.setTestOnReturn(this.isTestOnReturn());
        this.pool.setTestWhileIdle(this.isTestWhileIdle());
        if (this.getTimeBetweenEvictionRunsMillis() != null) {
            this.pool.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
        }
        if (this.getMinEvictableIdleTimeMillis() != null) {
            this.pool.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());
        }
        this.pool.setValidationQuery(this.getValidationQuery());
        this.pool.setValidationInterval(this.validationInterval);
        if (this.getMaxWaitMillis() != null) {
            this.pool.setMaxWait(this.getMaxWaitMillis());
        }
        if (this.jdbcInterceptors != null) {
            this.pool.setJdbcInterceptors(this.jdbcInterceptors);
        }
        return this.pool;
    }

    @PreDestroy
    public void close() {
        if (this.pool != null) {
            this.pool.close();
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(this.dataSource());
        sessionFactory.setTypeAliasesPackage("jp.pcaie.admin.domain");
        return sessionFactory.getObject();
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidationQuery() {
        return this.validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return this.testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return this.testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public Integer getTimeBetweenEvictionRunsMillis() {
        return this.timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public Integer getMinEvictableIdleTimeMillis() {
        return this.minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public Integer getMaxWaitMillis() {
        return this.maxWaitMillis;
    }

    public void setMaxWaitMillis(Integer maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getJdbcInterceptors() {
        return this.jdbcInterceptors;
    }

    public void setJdbcInterceptors(String jdbcInterceptors) {
        this.jdbcInterceptors = jdbcInterceptors;
    }

    public org.apache.tomcat.jdbc.pool.DataSource getPool() {
        return this.pool;
    }

    public void setPool(org.apache.tomcat.jdbc.pool.DataSource pool) {
        this.pool = pool;
    }

    public int getMaxActive() {
        return this.maxActive;
    }

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public int getInitialSize() {
        return this.initialSize;
    }

    public long getValidationInterval() {
        return this.validationInterval;
    }
}
