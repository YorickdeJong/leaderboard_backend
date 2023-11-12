package com.example.java_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class JavaTestApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.out.println("Database URL: " + dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("spring.datasource.url", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        SpringApplication.run(JavaTestApplication.class, args);
    }

}
