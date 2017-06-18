package ru.xidv.drankov.fassist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;

@EnableJpaRepositories
@SpringBootApplication
public class FassistApplication {


    public static void main(String[] args) {

        /*
         * WARNING
         *
         * THIS CODE IS UNSAFE
         * IF YOU NEED DEV PROFILE JUST CHANGE PATHNAME TO AN EXIST DIR
         */
        File file = new File("/home/sammers21/workspace/Telegram");

        if (file.exists() && file.isDirectory()) {
            System.setProperty("spring.profiles.active", "dev");
        } else {
            System.setProperty("spring.profiles.active", "default");
        }

        SpringApplication.run(FassistApplication.class, args);
    }
}
