package com.kghdev;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement
@SpringBootApplication
public class KGHWBApplication {
    public static void main(String[] args) {
        SpringApplication.run(KGHWBApplication.class, args);
    }


}
