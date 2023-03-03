package com.kghdev.bean.config;

import com.kghdev.bean.vo.Car;
import com.kghdev.bean.vo.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Car.class)
public class AppConfig {
    @Bean
    public Company getCompany() {
        return new Company("BMW");
    }
}