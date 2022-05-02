package com.dk.auth0examplespringbootbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class Auth0ExampleSpringBootBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Auth0ExampleSpringBootBackendApplication.class, args);
    }

}
