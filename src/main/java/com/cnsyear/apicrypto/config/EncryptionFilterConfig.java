package com.cnsyear.apicrypto.config;

import com.cnsyear.apicrypto.filter.EncryptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 数据加密过滤器配置
 * @Author jie.zhao
 * @Date 2020/6/8 16:12
 */
@Configuration
public class EncryptionFilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new EncryptionFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("EncryptionFilter");
        registration.setOrder(1);
        return registration;
    }
}