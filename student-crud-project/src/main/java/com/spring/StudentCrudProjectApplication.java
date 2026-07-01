package com.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.spring")
@MapperScan(basePackages = "com.spring.mapper")
public class StudentCrudProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentCrudProjectApplication.class, args);
	}

}
