package ru.xidv.drankov.fassist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class FassistApplication {


    public static void main(String[] args) {
        SpringApplication.run(FassistApplication.class, args);
    }


}
