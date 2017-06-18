package ru.xidv.drankov.fassist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.xidv.drankov.fassist.beans.DockerStarter;

import java.io.File;

@EnableJpaRepositories
@SpringBootApplication
public class FassistApplication {


    public static void main(String[] args) {
        DockerStarter.start();
        System.out.println("kek");
        SpringApplication.run(FassistApplication.class, args);
    }


}
