package ru.xidv.drankov.fassist.beans;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class VertxProcessor implements BeanPostProcessor {

    @Bean
    public Vertx deployer() {
        return Vertx.vertx();
    }

    @Autowired
    private Vertx vertx;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    /**
     * Here we deploy each class as Verticle if it was extended by {@code AbstractVerticle.class}
     */
    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (AbstractVerticle.class.isAssignableFrom(o.getClass())) {
            vertx.deployVerticle((AbstractVerticle) o);
        }
        return o;
    }
}
