package com.kghdev.di.vo;

public class Car {

    private String name;
    private String brandName;

    public Car() {
    }

    public Car(String name, String brandName) {
        this.name = name;
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * equals override
     * */
    @Override
    public boolean equals(Object obj) {
//        return super.equals(obj); // 동일성, identity
        Car carTmp = (Car) obj;
        return this.getName().equals(carTmp.getName()) && this.getBrandName().equals((carTmp.getBrandName())); // 동등성,  equality
    }
}
