package com.ltrlabs;

import com.ltrlabs.client.SystemClient;
import com.ltrlabs.configuration.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        SystemClient client = context.getBean(SystemClient.class);
        client.start();
    }
}