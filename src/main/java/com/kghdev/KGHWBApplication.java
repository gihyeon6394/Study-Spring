package com.kghdev;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableTransactionManagement
@SpringBootApplication
@EnableBatchProcessing  //batch 활성화
public class KGHWBApplication {
    public static void main(String[] args) {
        SpringApplication.run(KGHWBApplication.class, args);
    }


}
