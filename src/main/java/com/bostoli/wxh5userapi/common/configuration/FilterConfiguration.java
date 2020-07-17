package com.bostoli.wxh5userapi.common.configuration;

import com.bostoli.wxh5userapi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class FilterConfiguration {
    @Value("${wechat.url-pattern}")
    String urlPattern;

    @Bean
    public FilterRegistrationBean authFilter(UserInfoService authService) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthFilter(authService));
        registration.setUrlPatterns(Arrays.asList(urlPattern));
        registration.setName("authFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean exceptionFilter(ExceptionHandlerFilter exceptionHandlerFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(exceptionHandlerFilter);
        filterRegistrationBean.setOrder(101);
        filterRegistrationBean.setName("exceptionFilter");
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }

}
