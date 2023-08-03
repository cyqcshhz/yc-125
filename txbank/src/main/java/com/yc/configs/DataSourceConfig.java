package com.yc.configs;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:db.properties")
@Data
@Log4j2
public class DataSourceConfig {
    @Value("root")
    private String username;
    @Value("020503")
    private String password;
    @Value("jdbc:mysql://localhost:3306/testbank?serverTimeZone=UTC")
    private String url;
    @Value("com.mysql.cj.jdbc.Driver")
    private String driverclass;


    @Value("#{T(Runtime).getRuntime().availableprocessors()*2}")
    private int cpuCount;

    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource druidDataSource(){
        DruidDataSource dds=new DruidDataSource();
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);
        dds.setDriverClassName(driverclass);

        log.info(""+cpuCount);
        dds.setInitialSize(cpuCount);
        dds.setMaxActive(cpuCount*2);
        return dds;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource= new DriverManagerDataSource();
        dataSource.setDriverClassName(driverclass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    public DataSource dbcpDataSource(){
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName(driverclass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
