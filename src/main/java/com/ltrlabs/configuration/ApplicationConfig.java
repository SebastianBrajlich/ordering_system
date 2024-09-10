package com.ltrlabs.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.ltrlabs")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
}
