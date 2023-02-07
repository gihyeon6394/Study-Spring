package com.kghdev.bean.vo;

import org.springframework.stereotype.Component;

@Component
public class Car {

    private String name;

    private Company company;

    public Car() {
    }

    public Car(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
