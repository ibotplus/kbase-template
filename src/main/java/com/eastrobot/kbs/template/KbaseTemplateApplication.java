package com.eastrobot.kbs.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KbaseTemplateApplication {

    public static void main(String[] args) {
        System.setProperty("appName", KbaseTemplateApplication.class.getSimpleName());
        SpringApplication.run(KbaseTemplateApplication.class, args);
    }

}
