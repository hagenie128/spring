package com.spring.problem02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class ProblemMain {

    @Configuration
    @ComponentScan("com.spring.problem02")
    static class ProblemConfig {

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(ProblemConfig.class);

        KioskConfig kioskConfig = ctx.getBean(KioskConfig.class);
        kioskConfig.printInfo();

        ctx.close();
    }
}
