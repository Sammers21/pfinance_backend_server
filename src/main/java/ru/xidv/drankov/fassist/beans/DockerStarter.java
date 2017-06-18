package ru.xidv.drankov.fassist.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class DockerStarter implements BeanPostProcessor {

    private static void exec(String command) {
        Process p;
        try {
            p = new ProcessBuilder(command.split(" "))
                    .inheritIO()
                    .start();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runPostgresImage() {
        exec("/bin/bash scripts/runPostgres.sh");
    }

    public static void stopPostgresImage() {
        exec("/bin/bash scripts/stopPostgres.sh");
    }

    public static void start() {
        runPostgresImage();
    }

    boolean time = true;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if (time) {
            start();
            time = false;
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }


}
